package View;

import Controller.GameController;
import Model.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class GameView extends JFrame {

    private final ArrayList<Player> playerList;
    private ArrayList<PlayerView> playerViews;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private final StatusBar user_status;
    private final HashMap<String, Continent> continentMap;
    private final ArrayList<String> gameState;
    private int gameStateIndex;
    private String currentState;
    private Clip clip;
    private int outOfGame;
    private final ArrayList<Territory> commandTerritory;
    private HashMap<String, Territory> worldMap;

    /**
     * Constructor of the Gameview, it is called in Controller.PlayerSelectController and the game begins after the construction of the class.
     * @param players ArrayList of View.PlayerSelectPanel object which contains the Icon and player name of all players
     * @throws IOException handle a possible IOException
     */
    public GameView(ArrayList<PlayerSelectPanel> players) throws IOException {
        super("Risk!");
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        playerList = new ArrayList<>();
        Stack<Color> color_list = new Stack<>();
        currentPlayer = null;
        user_status = new StatusBar();
        GameController game_controller = new GameController(this);
        user_status.setController(game_controller);
        outOfGame = 0;
        gameState = new ArrayList<>();
        gameState.add("Reinforce");
        gameState.add("Attack");
        gameState.add("Fortify");
        gameStateIndex = 0;

        currentState = "Reinforce";
        currentPlayerIndex = 0;
        commandTerritory = new ArrayList<>();

        //Model.Player colours
        color_list.add(new Color(239, 150, 75));
        color_list.add(new Color(153, 221, 255));
        color_list.add(new Color(252, 115, 99));
        color_list.add(new Color(255, 218, 103));
        color_list.add(new Color(202, 128, 255));
        color_list.add(new Color(139, 224, 87));

        JPanel players_overlay = new JPanel();
        players_overlay.setBackground(new Color(0,0,0, 0));
        players_overlay.setLayout(new FlowLayout());

        for(PlayerSelectPanel x : players){
            Player newPlayer;

            if(x.getPlayerType().equals("AI")) {
                newPlayer = new AIPlayer(x.getPlayerName(), this);
            } else {
                newPlayer = new Player(x.getPlayerName());
            }

            Color temp_color = color_list.pop();
            ImageIcon player_icon = (ImageIcon) x.getImageIcon();
            newPlayer.addGuiInfo(temp_color,player_icon);
            PlayerView new_view = new PlayerView(newPlayer,newPlayer.getName(), temp_color, player_icon,0);
            playerList.add(newPlayer);
            players_overlay.add(new_view);
        }

        players_overlay.setBounds(1160,0,100,814);

        this.setSize(1280,814);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        this.setMinimumSize(new Dimension(1280,840));
        JPanel background;
        BufferedImage image = ImageIO.read(getClass().getResource("/resources/Map.png"));
        background = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(image,0,0,this);
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(image.getWidth(), image.getHeight());
            }
        };
        add(background);
        setContentPane(background);
        background.setLayout(null);
        setLocationRelativeTo(null);

        GameSetup gameSetup = new GameSetup(playerList,background);

        continentMap = gameSetup.returnContinentMap();
        worldMap = gameSetup.returnWorldMap();
        ArrayList<TerritoryButton> worldMapView = gameSetup.returnWorldMapView();
        currentPlayer = playerList.get(currentPlayerIndex);
        playerBonus();
        user_status.setPlayer(currentPlayer);

        for(TerritoryButton x : worldMapView){
            background.add(x);
            x.addActionListener(game_controller::territoryAction);
        }

        add(user_status);
        background.add(players_overlay);
        setResizable(false);
        this.setVisible(true);
        playMusic("/resources/beat.wav");
        if(currentPlayer instanceof AIPlayer) {
            ((AIPlayer) currentPlayer).play();
        }
    }

    /**
     * This method is for switching the characters and occurs when the game reaches the end of the game state arrayList
     * A method there counts out of game to see if there is one person remaining to declare a winner.
     * The game will only end after the player ends their last turn.
     */
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerList.size();
        currentPlayer = playerList.get(currentPlayerIndex);
        if(currentPlayer.getTerritoriesOccupied().size() == 0){
            outOfGame++;
            nextPlayer();
        }else if(outOfGame == playerList.size()-1){
            winnerScreen();
            System.out.println("Winner!");
        }else{
            outOfGame = 0;
        }
        playerBonus();
        if(currentPlayer instanceof AIPlayer) {
            ((AIPlayer) currentPlayer).play();
        }
    }

    public void winnerScreen(){
        this.dispose();
        clip.stop();
        JFrame winnerFrame = new JFrame("WINNER!");
        winnerFrame.setSize(650, 450);
        winnerFrame.setLocation(400,400);
        winnerFrame.setLayout(new BorderLayout());

        JPanel playerPanel = new JPanel(new FlowLayout());

        JPanel playerIconName = new JPanel(new BorderLayout());

        JLabel name = new JLabel(currentPlayer.getName());
        name.setFont(new Font("Impact", Font.PLAIN, 30));
        name.setHorizontalAlignment(name.CENTER);
        name.setVerticalAlignment(name.CENTER);

        JLabel playerIcon = new JLabel();

        ImageIcon a = (ImageIcon) currentPlayer.getPlayer_icon();
        Image i = a.getImage().getScaledInstance( 150, 150,  java.awt.Image.SCALE_SMOOTH );
        a = new ImageIcon(i);
        playerIcon.setIcon(a);

        playerIconName.add(playerIcon, BorderLayout.NORTH);
        playerIconName.add(name, BorderLayout.SOUTH);

        JLabel confettiLeft = new JLabel();
        ImageIcon b = new ImageIcon(getClass().getResource("/resources/confetti.png"));
        Image m = b.getImage().getScaledInstance( 200, 200,  java.awt.Image.SCALE_SMOOTH );
        b = new ImageIcon(m);
        confettiLeft.setIcon(b);

        JLabel confettiRight = new JLabel();
        ImageIcon img1 = new ImageIcon(getClass().getResource("/resources/confettiflip.png"));
        Image m1 = img1.getImage().getScaledInstance( 200, 200,  java.awt.Image.SCALE_SMOOTH );
        img1 = new ImageIcon(m1);
        confettiRight.setIcon(img1);

        playerPanel.add(confettiLeft, FlowLayout.LEFT);
        playerPanel.add(playerIconName, FlowLayout.CENTER);
        playerPanel.add(confettiRight, FlowLayout.RIGHT);

        JLabel winnerText = new JLabel("Winner Winner Chicken Dinner!");
        winnerText.setFont(new Font("Comic SANS MS", Font.ITALIC, 35));
        winnerText.setHorizontalAlignment(winnerText.CENTER);
        winnerText.setVerticalAlignment(winnerText.CENTER);

        JLabel crown = new JLabel();
        ImageIcon c = new ImageIcon(getClass().getResource("/resources/crown1.gif"));
        crown.setIcon(c);
        crown.setHorizontalAlignment(crown.CENTER);
        crown.setVerticalAlignment(crown.CENTER);

        winnerFrame.add(winnerText, BorderLayout.NORTH);
        winnerFrame.add(crown, BorderLayout.CENTER);
        winnerFrame.add(playerPanel, BorderLayout.SOUTH);

        playMusic("/resources/allido.wav");

        winnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winnerFrame.setVisible(true);
    }

    /**
     * PlayerBonus calculates how many troops each player will get at the start of their turn by checking how many territories
     * they own and weather or not they occupy an entire continent
     */
    public void playerBonus(){

        int troops = 0;
        if (continentMap.get("Asia").checkContinentOccupant(currentPlayer)) troops += 7; // Asia Bonus
        if (continentMap.get("Australia").checkContinentOccupant(currentPlayer)) troops += 2; // Australia Bonus
        if (continentMap.get("Europe").checkContinentOccupant(currentPlayer)) troops += 5; // Europe Bonus
        if (continentMap.get("Africa").checkContinentOccupant(currentPlayer)) troops += 3; // Africa Bonus
        if (continentMap.get("SouthAmerica").checkContinentOccupant(currentPlayer)) troops += 2; // South America Bonus
        if (continentMap.get("NorthAmerica").checkContinentOccupant(currentPlayer)) troops += 5; // North America Bonus

        if ((currentPlayer.getTerritoriesOccupied().size()) <= 9) {
            troops += 3;
        } else {
            troops += ((currentPlayer.getTerritoriesOccupied().size()) / 3);
        }
        currentPlayer.addDeployableTroops(troops);
    }

    /**
     * This method is in charge of handling the switching of states and is called everytime the View.StatusBar nextButton
     * is pressed.
     * When reaching the end of state list it calls nextPlayer to load the next player and continue the game.
     */
    public void nextState(){
        clearCommandTerritory();
        currentState = gameState.get(gameStateIndex);
        user_status.updateDisplay(currentState);
        if(gameStateIndex + 1 == 3){
            gameStateIndex = (gameStateIndex + 1) % gameState.size();
            user_status.removePlayer(currentPlayer);
            user_status.setPlayer(playerList.get(currentPlayerIndex + 1));
            nextPlayer();
        }
    }

    /**
     * Method returns String, to check what the current state of the game
     * @return returns either Reinforce, Attack, or Fortify
     */
    public String getCurrentState(){
        return currentState;
    }

    /**
     * It returns the player object of the player who's turn it currently is
     * @return The player that is currently in use
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * This method is in charge of adding territories to the commandTerritory ArrayList and also setting the background
     * of that colour to be darker to indicate to the player that they have selected that territory
     * @param new_territory the new territory to add
     */
    public void addCommandTerritory(Territory new_territory){
        Color new_color = new_territory.getColor();
        new_territory.addColor(new_color.darker());
        commandTerritory.add(new_territory);
    }

    /**
     * This method is in charge of resetting the colour and timer of the Territories in commandTerritory ArrayList
     */
    public void clearCommandTerritory(){
        for(Territory x : commandTerritory){
            Color color_fix = x.getColor().brighter();
            x.addColor(color_fix);
        }
        commandTerritory.clear();
    }

    /**
     * Returns the ArrayList for currently selected territories which are then loaded into the PopUp methods for
     * Attack, Reinforce and Fortify.
     * @return list of the currently selected territories
     */
    public ArrayList<Territory> getCommandTerritory(){
        return commandTerritory;
    }

    /**
     * Returns the size of the commandTerritory ArrayList
     * @return commandTerritory
     */
    public int getCommandTerritorySize(){
        return commandTerritory.size();
    }

    public HashMap<String, Territory> getWorldMap() { return worldMap; }

    /**
     * This is to play the most jamming beat as you take over the world. No further explanation required.
     * @param filepath the filepath of the music to play
     */
    public void playMusic(String filepath) {
        {
            try
            {
                URL url = getClass().getResource(filepath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip( );
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            catch(Exception ex)
            {
                System.out.println("Error with playing sound.");
                ex.printStackTrace( );
            }
        }

    }
}
