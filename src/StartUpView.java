import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StartUpView extends JFrame {

    public StartUpView() {
        super("Player Setup!");
        setSize(new Dimension(1280,814));
        setMinimumSize(new Dimension(1280,814));
        setMaximumSize(new Dimension(1280,814));
        setLayout(new BorderLayout());



        JPanel p1 = new JPanel(new GridLayout(2,1, 0, 0));
        p1.setMaximumSize(new Dimension(1280, 150));
        JPanel p2 = new JPanel();

        // JLabel (
        JLabel jLabel = new JLabel("SELECT PLAYERS");
        jLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        jLabel.setForeground(new Color(19, 0, 255));
        jLabel.setHorizontalAlignment(jLabel.CENTER);
        jLabel.setVerticalAlignment(jLabel.TOP);
        p1.add(jLabel);

        // JButton (left arrow)
        JButton jButton1 = new JButton();
        ImageIcon icon = new ImageIcon("C:\\Users\\adisa\\Desktop\\leftarrow.png");
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;
        icon = new ImageIcon(newImg);
        jButton1.setIcon(icon);
        p2.add(jButton1);

        // JLabel (for # of players)
        JLabel jLabel1 = new JLabel("2");
        jLabel1.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        jLabel1.setHorizontalAlignment(jLabel.CENTER);
        jLabel1.setVerticalAlignment(jLabel.CENTER);
        p2.add(jLabel1);

        // JButton (right arrow)
        JButton jButton2 = new JButton();
        ImageIcon icon2 = new ImageIcon("C:\\Users\\adisa\\Desktop\\rightarrow.png");
        Image img2 = icon2.getImage();
        Image scaledImage = img2.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;
        icon2 = new ImageIcon(scaledImage);
        jButton2.setIcon(icon2);
        p2.add(jButton2);

        JPanel p3 = new JPanel();
        JButton startButton = new JButton("PLAY GAME");
        startButton.setFont(new Font("Impact", Font.PLAIN, 40));
        startButton.setBackground(new Color(77, 220, 70));
        p3.add(startButton, BorderLayout.CENTER);

        // JPanel (for characters)
        JPanel playerList = new JPanel();
        playerList.setLayout(new FlowLayout(FlowLayout.CENTER));

        ArrayList<String> file = new ArrayList<>();
        file.add("C:\\Users\\adisa\\Desktop\\Chizzy.png");
        file.add("C:\\Users\\adisa\\Desktop\\TA.png");

        ArrayList<PlayerSelectPanel> players = new ArrayList<>();
        PlayerSelectPanel adi = new PlayerSelectPanel(file.get(0));
        PlayerSelectPanel TA = new PlayerSelectPanel(file.get(1));
        PlayerSelectPanel adi2 = new PlayerSelectPanel(file.get(0));
        PlayerSelectPanel TA2 = new PlayerSelectPanel(file.get(1));
        PlayerSelectPanel TA3 = new PlayerSelectPanel(file.get(1));
        PlayerSelectPanel adi3 = new PlayerSelectPanel(file.get(0));

        players.add(adi);
        players.add(TA);
        players.add(adi2);
        players.add(TA2);
        players.add(adi3);
        players.add(TA3);


        playerList.add(adi);
        playerList.add(TA);
        /*
        playerList.add(adi2);
        playerList.add(TA2);
        playerList.add(adi3);
        playerList.add(TA3);
        */


        // Left arrow
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = (Integer.parseInt(jLabel1.getText()));
                if (x == 2) {
                    x = 6;
                    for(int i = 0; i < 6; i++) {
                        playerList.add(players.get(i));
                        playerList.revalidate();
                        playerList.repaint();
                    }
                } else {
                    x--;
                    playerList.remove(players.get(x));
                    playerList.revalidate();
                    playerList.repaint();
                }
                jLabel1.setText("" + x);
            }
        });

        // Right Arrow
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = (Integer.parseInt(jLabel1.getText()));
                if (x == 6) {
                    x = 2;
                    for(int i = 5; i > 1; i--) {
                        playerList.remove(players.get(i));
                        playerList.revalidate();
                        playerList.repaint();
                    }
                } else {
                    x++;
                    playerList.add(players.get(x-1));
                    playerList.revalidate();
                    playerList.repaint();
                }
                jLabel1.setText("" + x);
            }
        });


        p1.add(p2);
        add(p1, BorderLayout.NORTH);
        add(playerList, BorderLayout.CENTER);
        add(p3, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        StartUpView s = new StartUpView();
    }
}
