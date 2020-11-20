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
import java.util.Timer;


public class GameView extends JFrame {

    private final ArrayList<Player> playerList;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private final StatusBar user_status;
    private HashMap<String, Continent> continentMap;
    private final ArrayList<GameState> gameState;
    private int gameStateIndex;
    private GameState currentState;
    private Clip clip;
    private int outOfGame;
    private final ArrayList<Territory> commandTerritory;
    private HashMap<String, Territory> worldMap;
    private JPanel players_overlay;
    private Stack<Color> color_list;
    private final int AISpeed = 100;
    private Timer aiTimer;

    /**
     * Constructor of the Gameview, it is called in Controller.PlayerSelectController and the game begins after the construction of the class.
     * @param players ArrayList of View.PlayerSelectPanel object which contains the Icon and player name of all players
     * @throws IOException handle a possible IOException
     */
    public GameView(ArrayList<PlayerSelectPanel> players) throws IOException {
        super("Risk!");
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        playerList = new ArrayList<>();
        color_list = new Stack<>();
        currentPlayer = null;
        user_status = new StatusBar();
        GameController game_controller = new GameController(this);
        user_status.setController(game_controller);
        outOfGame = 0;
        gameState = new ArrayList<>();
        gameState.add(GameState.REINFORCE);
        gameState.add(GameState.ATTACK);
        gameState.add(GameState.FORTIFY);
        gameStateIndex = 0;
        aiTimer = new Timer("AI");

        currentState = GameState.REINFORCE;
        currentPlayerIndex = 0;
        commandTerritory = new ArrayList<>();

        //Model.Player colours
        color_list.add(new Color(239, 150, 75));
        color_list.add(new Color(153, 221, 255));
        color_list.add(new Color(252, 115, 99));
        color_list.add(new Color(255, 218, 103));
        color_list.add(new Color(202, 128, 255));
        color_list.add(new Color(139, 224, 87));

        players_overlay = new JPanel();
        players_overlay.setBackground(new Color(0,0,0, 0));
        players_overlay.setLayout(new FlowLayout());
        addPlayersToOverlay(players);
        players_overlay.setBounds(1160,0,100,814);

        BufferedImage image = ImageIO.read(getClass().getResource("/resources/Map.png"));
        BackgroundPanel background = new BackgroundPanel(image);

        setSize(1280,814);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setMinimumSize(new Dimension(1280,840));
        add(background);
        setContentPane(background);
        background.setLayout(null);
        setLocationRelativeTo(null);

        gameSetup(playerList, background, game_controller);

        add(user_status);
        background.add(players_overlay);
        setResizable(false);
        setVisible(true);
        playMusic("/resources/beat.wav");
        initializeAITimer();
    }

    public void initializeAITimer() {
        if(currentPlayer instanceof AIPlayer) {
            aiTimer.scheduleAtFixedRate(new AITimer((AIPlayer) currentPlayer),AISpeed,AISpeed);
        }
    }

    public void gameSetup(ArrayList<Player> playerList, JPanel background, GameController game_controller) {
        GameSetup gameSetup = new GameSetup(playerList, background);
        continentMap = gameSetup.returnContinentMap();
        worldMap = gameSetup.returnWorldMap();
        currentPlayer = playerList.get(currentPlayerIndex);
        currentPlayer.playerBonus(continentMap);
        user_status.setPlayer(currentPlayer);
        for(TerritoryButton x : gameSetup.returnWorldMapView()){
            background.add(x);
            x.addActionListener(game_controller::territoryAction);
        }
    }

    public void addPlayersToOverlay(ArrayList<PlayerSelectPanel> players) {
        for(PlayerSelectPanel x : players){
            Player newPlayer;
            if(x.getPlayerType().equals("AI")) {
                newPlayer = new AIPlayer(x.getPlayerName(), this);
            } else {
                newPlayer = new Player(x.getPlayerName());
            }
            Color temp_color = color_list.pop();
            ImageIcon player_icon = (ImageIcon) x.getImageIcon();
            newPlayer.addGuiInfo(temp_color, player_icon);
            PlayerView new_view = new PlayerView(newPlayer, newPlayer.getName(), temp_color, player_icon,0);
            playerList.add(newPlayer);
            players_overlay.add(new_view);
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
        user_status.setPlayer(currentPlayer);
        aiTimer.cancel();
        aiTimer = new Timer();
        if(currentPlayer.getTerritoriesOccupied().size() == 0){
            outOfGame++;
            nextPlayer();
        }else if(outOfGame == playerList.size()-1){
            new WinningScreenFrame(currentPlayer, this);
        }else{
            outOfGame = 0;
            currentPlayer.playerBonus(continentMap);
            initializeAITimer();
        }
    }

    /**
     * This method is in charge of handling the switching of states and is called everytime the View.StatusBar nextButton
     * is pressed.
     * When reaching the end of state list it calls nextPlayer to load the next player and continue the game.
     */
    public void nextState(){
        clearCommandTerritory();
        if(gameStateIndex + 1 == 3){
            gameStateIndex = (gameStateIndex + 1) % gameState.size();
            currentState = gameState.get(gameStateIndex);
            user_status.removePlayer(currentPlayer);
            nextPlayer();
        }else{
            gameStateIndex = (gameStateIndex + 1) % gameState.size();
            currentState = gameState.get(gameStateIndex);
            user_status.updateDisplay(currentState);
        }
    }

    /**
     * Method returns String, to check what the current state of the game
     * @return returns either Reinforce, Attack, or Fortify
     */
    public GameState getCurrentState(){
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

    public Territory getFirstCommandTerritory(){
        return commandTerritory.get(0);
    }

    /**
     * Returns the size of the commandTerritory ArrayList
     * @return commandTerritory
     */
    public int getCommandTerritorySize(){
        return commandTerritory.size();
    }

    public Continent getContinent(Territory territory){
        return continentMap.get(territory.getContinentName());
    }

    public HashMap<String, Territory> getWorldMap() { return worldMap; }

    public void stopMusic() {
        clip.stop();
    }

    /**
     * This is to play the most jamming beat as you take over the world. No further explanation required.
     * @param filepath the filepath of the music to play
     */
    public void playMusic(String filepath) {
            try {
                URL url = getClass().getResource(filepath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip( );
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch(Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace( );
            }
    }
}
