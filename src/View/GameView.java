package View;

import Controller.GameController;
import Listener.UserStatusListener;
import Model.*;
import Event.UserStatusEvent;

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
    private final BackgroundPanel background;

    /**
     * Constructor for the GameView class.
     * @param players the list of players that were passed in from the playerselect phase
     * @throws IOException exception
     */
    public GameView(ArrayList<PlayerSelectPanel> players) throws IOException {
        super("Risk!");
        color_list = new Stack<>();
        color_list.add(new Color(239, 150, 75));
        color_list.add(new Color(153, 221, 255));
        color_list.add(new Color(252, 115, 99));
        color_list.add(new Color(255, 218, 103));
        color_list.add(new Color(202, 128, 255));
        color_list.add(new Color(139, 224, 87));

        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        GameModel gameModel = new GameModel();
        gameModel.addPlayers(addPlayersFromPanel(players, gameModel));
        gameModel.addView(this);

        user_status = new StatusBar();
        game_controller = new GameController(gameModel,this);
        user_status.setController(game_controller);
        BufferedImage image = ImageIO.read(getClass().getResource("/resources/Map.png"));
        background = new BackgroundPanel(image);


        setSize(1280,814);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setMinimumSize(new Dimension(1280,840));
        add(background);
        setContentPane(background);
        background.setLayout(null);
        setLocationRelativeTo(null);


        GameSetup setupGame = new GameSetup(gameModel.getPlayers(),background);
        gameModel.getGameSetup(setupGame);
        players_overlay = new JPanel();
        players_overlay.setBackground(new Color(0,0,0, 0));
        players_overlay.setLayout(new FlowLayout());

        players_overlay.setBounds(1160,0,100,814);
        addPlayerOverlay(gameModel);
        addTerritoryOverlay(setupGame);

        add(user_status);
        background.add(players_overlay);
        setResizable(false);
        setVisible(true);
        playMusic("/resources/beat.wav");
    }

    /**
     * Used to add players from the model to the View
     * @param gameModel the game model
     */
    private void addPlayerOverlay(GameModel gameModel){
        for(Player temp_player : gameModel.getPlayers()){
            PlayerView new_view = new PlayerView(temp_player, temp_player.getName(), temp_player.getPlayer_color(), (ImageIcon) temp_player.getPlayer_icon(),0);
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
        for(PlayerSelectPanel x : players){
            Player newPlayer;
            if(x.getPlayerType().equals("AI")) {
                newPlayer = new AIPlayer(x.getPlayerName(), gameModel);
            } else {
                newPlayer = new Player(x.getPlayerName());
            }
            Color temp_color = color_list.pop();
            ImageIcon player_icon = (ImageIcon) x.getImageIcon();
            newPlayer.addGuiInfo(temp_color, player_icon);
            playerList.add(newPlayer);
        }
        return playerList;
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

    /**
     * This method is used to handle updates from the GameModel and update the view respectively.
     * @param event the event
     */
    @Override
    public void updateUserStatus(UserStatusEvent event) {
        switch (event.getGameState()){
            case REINFORCE -> {
                user_status.setPlayer(event.getCurrentPlayer());
                event.getCurrentPlayer().addPlayerListener(user_status);
                user_status.displayReinforce();
            }
            case ATTACK -> {
                event.getCurrentPlayer().removePlayerListener(user_status);
                user_status.displayAttack();
            }
            case FORTIFY -> user_status.displayFortify();
            case WINNING -> new WinningScreenFrame(event.getCurrentPlayer(),this);
        }
    }
}
