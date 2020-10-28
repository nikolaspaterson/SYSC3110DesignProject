import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartUpView extends JFrame {

    public StartUpView() {
        super("Player Setup!");
        setSize(new Dimension(1280,814));

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();

        // JLabel (
        JLabel jLabel = new JLabel("SELECT PLAYERS");
        jLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        jLabel.setForeground(Color.blue);
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
        //jButton1.setHorizontalAlignment(jButton1.LEFT);
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
        //jButton2.setHorizontalAlignment(jButton2.RIGHT);
        p2.add(jButton2);

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = (Integer.parseInt(jLabel1.getText()));
                if (x == 2) {
                    x = 6;
                } else {
                    x--;
                }
                jLabel1.setText("" + x);
            }
        });

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = (Integer.parseInt(jLabel1.getText()));
                if (x == 6) {
                    x = 2;
                } else {
                    x++;
                }
                jLabel1.setText("" + x);
            }
        });

        // JPanel (for characters)
        JPanel playerList = new JPanel();
        playerList.setLayout(new GridLayout(4, 2, 5, 10));


        // JPanel (for player)
        GridBagConstraints gbl = new GridBagConstraints();
        JPanel player = new JPanel(new GridBagLayout());
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        player.setBorder(blackLine);

        JLabel pn = new JLabel("Player Name:");
        pn.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        JTextField playerName = new JTextField(15);
        JButton submit = new JButton("SUBMIT");
        submit.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        JLabel empty = new JLabel("          ");
        JLabel empty1 = new JLabel("          ");

        // Creating another JLabel for pics of players
        JLabel personalPhoto = new JLabel();
        ImageIcon chizzy = new ImageIcon("C:\\Users\\adisa\\Desktop\\Chizzy.png");
        Image i = chizzy.getImage();
        Image newI = i.getScaledInstance( 75, 75,  java.awt.Image.SCALE_SMOOTH ) ;
        chizzy = new ImageIcon(newI);

        personalPhoto.setIcon(chizzy);
        gbl.gridx = 0;
        gbl.gridy = 0;
        player.add(personalPhoto, gbl);
        gbl.gridx = 10;
        gbl.gridy = 0;
        player.add(empty, gbl);
        gbl.gridx = 20;
        gbl.gridy = 0;
        player.add(pn, gbl);
        gbl.gridx = 30;
        gbl.gridy = 0;
        player.add(playerName, gbl);
        gbl.gridx = 40;
        gbl.gridy = 0;
        player.add(empty1, gbl);
        gbl.gridx = 50;
        gbl.gridy = 0;
        player.add(submit, gbl);

        playerList.add(player);


        // JPanel (for player)
        JPanel player2 = new JPanel(new GridBagLayout());
        player2.setBorder(blackLine);

        JLabel pn1 = new JLabel("Player Name:");
        pn1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        JTextField playerName1 = new JTextField(15);
        JButton submit1 = new JButton("SUBMIT");
        submit1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        JLabel e = new JLabel("          ");
        JLabel e1 = new JLabel("          ");

        // Creating another JLabel for pics of players
        JLabel personalPhoto1 = new JLabel();

        personalPhoto1.setIcon(chizzy);
        gbl.gridx = 0;
        gbl.gridy = 0;
        player2.add(personalPhoto1, gbl);
        gbl.gridx = 10;
        gbl.gridy = 0;
        player2.add(e, gbl);
        gbl.gridx = 20;
        gbl.gridy = 0;
        player2.add(pn1, gbl);
        gbl.gridx = 30;
        gbl.gridy = 0;
        player2.add(playerName1, gbl);
        gbl.gridx = 40;
        gbl.gridy = 0;
        player2.add(e1, gbl);
        gbl.gridx = 50;
        gbl.gridy = 0;
        player2.add(submit1, gbl);

        playerList.add(player2);

        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.CENTER);
        add(playerList, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        StartUpView s = new StartUpView();
    }
}
