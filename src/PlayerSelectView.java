import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayerSelectView extends JFrame {

    private JButton leftArrow, rightArrow;
    private JLabel numPlayers;
    private JPanel playerList;
    private ArrayList<PlayerSelectPanel> players;

    public PlayerSelectView() {
        // Main Frame
        super("Player Setup!");
        setSize(new Dimension(1280,814));
        setMinimumSize(new Dimension(1280,814));
        setMaximumSize(new Dimension(1280,814));
        setLayout(new BorderLayout());

        // Panel 1 & 2
        JPanel p1 = new JPanel(new GridLayout(2,1, 0, 0));
        p1.setMaximumSize(new Dimension(1280, 150));
        JPanel p2 = new JPanel();

        // JLabel (SELECT PLAYERS)
        JLabel jLabel = new JLabel("SELECT PLAYERS");
        jLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        jLabel.setForeground(new Color(19, 0, 255));
        jLabel.setHorizontalAlignment(jLabel.CENTER);
        jLabel.setVerticalAlignment(jLabel.TOP);
        p1.add(jLabel);

        // JButton (left arrow)
        leftArrow = new JButton();
        ImageIcon icon = scaleImage("C:\\Users\\adisa\\Desktop\\leftarrow.png");
        leftArrow.setIcon(icon);
        p2.add(leftArrow);

        // JLabel (for # of players)
        numPlayers = new JLabel("2");
        numPlayers.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        numPlayers.setHorizontalAlignment(jLabel.CENTER);
        numPlayers.setVerticalAlignment(jLabel.CENTER);
        p2.add(numPlayers);

        // JButton (right arrow)
        rightArrow = new JButton();
        ImageIcon icon2 = scaleImage("C:\\Users\\adisa\\Desktop\\rightarrow.png");
        rightArrow.setIcon(icon2);
        p2.add(rightArrow);

        // Panel 3 (PLAY GAME Button)
        JPanel p3 = new JPanel();
        JButton startButton = new JButton("PLAY GAME");
        startButton.setFont(new Font("Impact", Font.PLAIN, 40));
        startButton.setBackground(new Color(77, 220, 70));
        p3.add(startButton, BorderLayout.CENTER);

        // Panel 4 (to hold player panels)
        playerList = new JPanel();
        playerList.setLayout(new FlowLayout(FlowLayout.CENTER));

        players = new ArrayList<>();
        PlayerSelectPanel player1 = new PlayerSelectPanel();
        PlayerSelectPanel player2 = new PlayerSelectPanel();
        PlayerSelectPanel player3 = new PlayerSelectPanel();
        PlayerSelectPanel player4 = new PlayerSelectPanel();
        PlayerSelectPanel player5 = new PlayerSelectPanel();
        PlayerSelectPanel player6 = new PlayerSelectPanel();

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        players.add(player6);

        playerList.add(player1);
        playerList.add(player2);

        PlayerSelectController playerSelectController = new PlayerSelectController(this);

        // If left arrow
        leftArrow.addActionListener(playerSelectController);

        // If right arrow
        rightArrow.addActionListener(playerSelectController);

        p1.add(p2);
        add(p1, BorderLayout.NORTH);
        add(playerList, BorderLayout.CENTER);
        add(p3, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private ImageIcon scaleImage(String filename) {
        ImageIcon scaledImg = new ImageIcon(filename);
        Image img = scaledImg.getImage().getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH );
        scaledImg = new ImageIcon(img);
        return scaledImg;
    }

    public JButton getLeftArrow() { return leftArrow; }

    public JLabel getNumPlayers () { return numPlayers; }

    public JPanel getPlayerList () { return playerList; }

    public ArrayList<PlayerSelectPanel> getPlayers() { return players; }

    public static void main(String[] args) {
        PlayerSelectView s = new PlayerSelectView();
    }
}
