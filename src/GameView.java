import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GameView extends JFrame {
    private final ArrayList<Player> playerList;
    private final Stack<Color> color_list;

    private Player currentPlayer;
    private int currentPlayerIndex;

    private final StatusBar user_status;
    private HashMap<String,Territory> worldMap;
    private HashMap<String,Continent> continentMap;
    private GameController game_controller;

    private ArrayList<String> gameState;
    private int gameStateIndex;
    private String currentState;

    private int outOfGame;

    private ArrayList<Territory> commandTerritory;

    public GameView(ArrayList<PlayerSelectPanel> players) throws IOException {
        super("Risk!");
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        playerList = new ArrayList<>();
        color_list = new Stack<>();
        currentPlayer = null;
        worldMap = new HashMap<>();
        user_status = new StatusBar();
        game_controller = new GameController(this);
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

        //Player colours
        color_list.add(new Color(239, 150, 75));
        color_list.add(new Color(153, 221, 255));
        color_list.add(new Color(252, 115, 99));
        color_list.add(new Color(255, 218, 103));
        color_list.add(new Color(202, 128, 255));
        color_list.add(new Color(139, 224, 87));

        for( PlayerSelectPanel x : players){
            playerList.add(new Player(x.getPlayerName(),color_list.pop(), (ImageIcon) x.getImageIcon()));
        }

        JPanel players_overlay = new JPanel();
        players_overlay.setBackground(new Color(0,0,0, 0));
        players_overlay.setLayout(new FlowLayout());
        for (Player x: playerList){
            players_overlay.add(x);
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
        currentPlayer = playerList.get(currentPlayerIndex);
        playerBonus();
        user_status.setPlayer(currentPlayer);

        for(Territory x : gameSetup.returnWorldMap().values()){
            background.add(x);
            x.addActionListener(game_controller::territoryAction);
        }
        worldMap = gameSetup.returnWorldMap();
        add(user_status);
        background.add(players_overlay);
        setResizable(false);
        this.setVisible(true);
    }

    public void nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex + 1) % playerList.size();
        currentPlayer = playerList.get(currentPlayerIndex);
        if(currentPlayer.getTotal_troops() == 0){
            currentPlayer.xOutPlayer();
            outOfGame++;
            nextPlayer();
        }else if(outOfGame == playerList.size()-1){
            System.out.println("Winner!");
        }else{
            outOfGame = 0;
        }
        playerBonus();
    }

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

    public void nextState(){
        if(gameStateIndex + 1 == 3){
            nextPlayer();
            user_status.setPlayer(currentPlayer);
        }
        gameStateIndex = (gameStateIndex + 1) % gameState.size();
        currentState = gameState.get(gameStateIndex);
        user_status.updateDisplay(currentState);
    }

    public String getCurrentState(){
        return currentState;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void addCommandTerritory(Territory new_territory){
        Color new_color = new_territory.getBackground();
        new_territory.setBackground(new_color.darker());
        commandTerritory.add(new_territory);
    }

    public void clearCommandTerritory(){
        for(Territory x : commandTerritory){
            x.setBackground(x.getDefault_color());
        }
        commandTerritory.clear();
    }

    public ArrayList<Territory> getCommandTerritory(){
        return commandTerritory;

    }
    public int getCommandTerritorySize(){
        return commandTerritory.size();
    }

    public void updateStatusBar(){
        user_status.updateDisplay(currentState);
    }
}
