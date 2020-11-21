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

public class GameView extends JFrame implements UserStatusListener {
    private GameModel gameModel;
    private final StatusBar user_status;
    private JPanel players_overlay;
    private Clip clip;
    private Stack<Color> color_list;
    private GameController game_controller;
    private BackgroundPanel background;

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
        gameModel = new GameModel();
        gameModel.addPlayers(addPlayers(players));
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
        addPlayerOverlay();
        addTerritoryOverlay(setupGame);

        add(user_status);
        background.add(players_overlay);
        setResizable(false);
        setVisible(true);
        playMusic("/resources/beat.wav");
    }

    private void addPlayerOverlay(){
        for(Player temp_player : gameModel.getPlayers()){
            PlayerView new_view = new PlayerView(temp_player, temp_player.getName(), temp_player.getPlayer_color(), (ImageIcon) temp_player.getPlayer_icon(),0);
            players_overlay.add(new_view);
        }
    }
    private void addTerritoryOverlay(GameSetup setup){
        for(TerritoryButton temp_territory : setup.returnWorldMapView()){
            background.add(temp_territory);
            temp_territory.addActionListener(game_controller::territoryAction);
        }
    }

    public void stopMusic() {
        clip.stop();
    }
    public ArrayList<Player> addPlayers(ArrayList<PlayerSelectPanel> players) {
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
            //PlayerView new_view = new PlayerView(newPlayer, newPlayer.getName(), temp_color, player_icon,0);
            playerList.add(newPlayer);
            //players_overlay.add(new_view);
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

    @Override
    public void updateUserStatus(UserStatusEvent event) {
        switch (event.getGameState()){
            case REINFORCE -> {
                user_status.setPlayer(gameModel.getCurrentPlayer());
                gameModel.getCurrentPlayer().addPlayerListener(user_status);
                user_status.displayReinforce();
            }
            case ATTACK -> {
                gameModel.getCurrentPlayer().removePlayerListener(user_status);
                user_status.displayAttack();
            }
            case FORTIFY -> {
                user_status.displayFortify();
            }
            case WINNING -> {
                new WinningScreenFrame(gameModel.getCurrentPlayer(),this);

            }

        }
    }
}
