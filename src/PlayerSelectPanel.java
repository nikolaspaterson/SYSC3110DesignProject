import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The PlayerSelectPanel class is used to store the information of each player in a JPanel.
 * A player has a Name, Icon and deciding if is an AI or Player.
 *
 * @author Ahmad El-Sammak
 */
public class PlayerSelectPanel extends JPanel {

    private ArrayList<String> file;
    private JTextField playerName;
    private JButton photo;

    /**
     * Class constructor for PlayerSelectPanel class.
     */
    public PlayerSelectPanel() {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        setBorder(blackLine);
        JLabel pn = new JLabel("Player Name:");
        pn.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        playerName = new JTextField(8);
        JButton option = new JButton("PLAYER");
        option.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        option.setBounds(new Rectangle(50,50));

        option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (option.getText().equals("PLAYER")) {
                    option.setText("AI");
                } else{
                    option.setText("PLAYER");
                }
            }
        });

        file = new ArrayList<>();
        file.add("/resources/Chizzy.png");
        file.add("/resources/TA.png");
        file.add("/resources/Captain.png");

        ImageIcon chizzy = scaleImage(file.get(0));
        ImageIcon TA = scaleImage(file.get(1));
        ImageIcon chip = scaleImage((file.get(2)));

        photo = new JButton();
        photo.setIcon(chizzy);


        photo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(photo.getIcon().equals(chizzy)) {
                    photo.setIcon(TA);
                } else if (photo.getIcon().equals(TA)) {
                    photo.setIcon(chip);
                } else{
                    photo.setIcon(chizzy);
                }
            }
        });

        add(photo);
        add(pn);
        add(playerName);
        add(option);

        setSize(600, 600);
        setVisible(true);
    }

    /**
     * Gets the name of the player.
     * @return String the player name.
     */
    public String getPlayerName(){
        return playerName.getText();
    }

    /**
     * Gets the Icon for the player.
     * @return Icon the player Icon
     */
    public Icon getImageIcon(){
        return photo.getIcon();
    }

    /**
     * Scales the Image to properly fit the JPanel.
     * @param filename the filepath
     * @return ImageIcon the scaled Icon
     */
    private ImageIcon scaleImage(String filename) {
        ImageIcon scaledImg = new ImageIcon(getClass().getResource(filename));
        Image img = scaledImg.getImage().getScaledInstance( 85, 85,  java.awt.Image.SCALE_SMOOTH );
        scaledImg = new ImageIcon(img);
        return scaledImg;
    }
}
