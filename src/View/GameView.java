package View;

import Controller.GameController;
import Controller.SaveController;
import Event.UserStatusEvent;
import Listener.UserStatusListener;
import Model.AIPlayer;
import Model.GameModel;
import Model.Player;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This class is used to handle all the view components of the Game.
 */
public class GameView extends JFrame implements UserStatusListener {

    private final StatusBar user_status;
    private final JPanel players_overlay;
    private Clip clip;
    private final Stack<Color> color_list;
    private final GameController game_controller;
    private GameModel gameModel;
    private final BackgroundPanel background;

    /**
     * Constructor for the GameView class.
     * @param players the list of players that were passed in from the player select phase
     * @throws IOException exception
     */
    public GameView(ArrayList<PlayerSelectPanel> players,String path) throws IOException {
        super("Risk!");
        color_list = new Stack<>();
        color_list.add(new Color(239, 150, 75));
        color_list.add(new Color(153, 221, 255));
        color_list.add(new Color(252, 115, 99));
        color_list.add(new Color(255, 218, 103));
        color_list.add(new Color(202, 128, 255));
        color_list.add(new Color(139, 224, 87));

        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        gameModel = new GameModel();
        gameModel.addPlayers(addPlayersFromPanel(players, gameModel));
        gameModel.addView(this);

        user_status = new StatusBar();
        game_controller = new GameController(gameModel,this);
        user_status.setController(game_controller);

        GameSetup setupGame = new GameSetup(gameModel.getPlayers(),path);

        background = setupGame.getBackground();
        gameModel.setGameName(setupGame.getGameName());

        setSize(background.imageSize());
        add(background);
        setContentPane(background);
        setMinimumSize(new Dimension(background.getWidth() , (int) (background.imageSize().getHeight() + 40)));
        gameModel.getWorld(setupGame.returnContinentMap(),setupGame.returnWorldMap());
        players_overlay = new JPanel();
        players_overlay.setBackground(new Color(0,0,0, 0));
        players_overlay.setLayout(new FlowLayout());

        SaveController saveController = new SaveController(this, setupGame.getOutput_subdirectory());
        GameMenuBar menuBar = new GameMenuBar(saveController, setupGame.getOutput_subdirectory());
        saveController.addView(menuBar);
        setJMenuBar(menuBar);

        players_overlay.setBounds(this.getWidth()-120,0,100, this.getHeight());
        addPlayerOverlay(gameModel);
        addTerritoryOverlay(setupGame);
        user_status.setBounds(this.getWidth()/3,this.getHeight()-175,500, 100);
        add(user_status);
        background.add(players_overlay);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        playMusic("/resources/beat.wav");
    }

    public void newGameModel(GameModel newModel){
        gameModel = newModel;
        game_controller.updateModel(gameModel);
        buildNewPlayerView();
    }

    /**
     * Used to add players from the model to the View
     * @param gameModel the game model
     */
    private void addPlayerOverlay(GameModel gameModel){
        for(Player temp_player : gameModel.getPlayers()){
            PlayerView new_view = new PlayerView(temp_player.getName(), temp_player.getPlayer_color(), (ImageIcon) temp_player.getPlayer_icon(),0);
            temp_player.addPlayerListener(new_view);
            players_overlay.add(new_view);
        }
    }

    /**
     * Used to add the TerritoryButtons onto the Game Frame.
     * @param setup the game setup
     */
    private void addTerritoryOverlay(GameSetup setup){
        for(TerritoryButton temp_territory : setup.returnWorldMapView()){
            background.add(temp_territory);
            temp_territory.addActionListener(game_controller::territoryAction);
        }
    }

    /**
     * Used to stop the music.
     */
    public void stopMusic() {
        clip.stop();
    }

    /**
     * This method is used to create all players from the playerselect phase and add them to the GameModel.
     * @param players the players from PlayerSelectPanel
     * @param gameModel the game model
     * @return ArrayList<Player> the list of players
     */
    public ArrayList<Player> addPlayersFromPanel(ArrayList<PlayerSelectPanel> players, GameModel gameModel) {
        ArrayList<Player> playerList = new ArrayList<>();
        int player_index = 0;
        for(PlayerSelectPanel x : players){
            Player newPlayer;
            if(x.getPlayerType().equals("AI")) {
                newPlayer = new AIPlayer(x.getPlayerName(), gameModel);
            } else {
                newPlayer = new Player(x.getPlayerName());
            }
            newPlayer.setPlayerNumber(player_index);
            Color temp_color = color_list.pop();
            ImageIcon player_icon = (ImageIcon) x.getImageIcon();
            newPlayer.addGuiInfo(temp_color, player_icon, x.getFilePath());
            playerList.add(newPlayer);
            player_index++;
        }
        return playerList;
    }

    public void buildNewPlayerView(){
        players_overlay.removeAll();
        addPlayerOverlay(gameModel);
        revalidate();
        repaint();
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

    public GameModel getModel(){
        return gameModel;
    }

    private void load_file(){
        buildNewPlayerView();
    }

    /**
     * This method is used to handle updates from the GameModel and update the view respectively.
     * @param event the event
     */
    @Override
    public void updateUserStatus(UserStatusEvent event) {
        user_status.setPlayer(event.getCurrentPlayer());
        switch (event.getGameState()){
            case REINFORCE -> {
                event.getCurrentPlayer().addPlayerListener(user_status);
                user_status.displayReinforce();
            }
            case ATTACK -> {
                event.getCurrentPlayer().removePlayerListener(user_status);
                user_status.displayAttack();
            }
            case FORTIFY -> {
                event.getCurrentPlayer().removePlayerListener(user_status);
                user_status.displayFortify();
            }
            case WINNING -> new WinningScreenFrame(event.getCurrentPlayer(),this);
        }
    }
}
