import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class GameView extends JFrame {
    private final ArrayList<Player> playerList;
    private final Stack<Color> color_list;
    private final Player currentPlayer;
    private final StatusBar user_status;

    public GameView() throws IOException {
        super("Risk!");
        playerList = new ArrayList<>();
        color_list = new Stack<>();
        currentPlayer = null;
        user_status = new StatusBar();

        color_list.add(new Color(239, 150, 75));
        color_list.add(new Color(153, 221, 255));
        color_list.add(new Color(252, 115, 99));
        color_list.add(new Color(255, 218, 103));
        color_list.add(new Color(202, 128, 255));
        color_list.add(new Color(142, 78, 52));

        playerList.add(new Player("Steve", color_list.pop() ,new ImageIcon(getClass().getResource("/resources/Chizzy.png"))));
        playerList.add(new Player("Bob", color_list.pop(),new ImageIcon(getClass().getResource("/resources/TA.png"))));
        playerList.add(new Player("Franklin", color_list.pop() ,new ImageIcon(getClass().getResource("/resources/Captain.png"))));
        playerList.add(new Player("Freddy", color_list.pop(),new ImageIcon(getClass().getResource("/resources/TA.png"))));
        playerList.add(new Player("Obama", color_list.pop() ,new ImageIcon(getClass().getResource("/resources/Chizzy.png"))));
        playerList.add(new Player("Chip", color_list.pop(),new ImageIcon(getClass().getResource("/resources/Captain.png"))));

        user_status.setPlayer(playerList.get(0));


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

        this.setMinimumSize(new Dimension(1280,814));
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
        GameSetup test_button_load = new GameSetup(playerList,background);
        for(Territory x : test_button_load.returnWorldMap().values()){
            background.add(x);
        }
        add(user_status);
        background.add(players_overlay);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        try{
            new GameView();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
