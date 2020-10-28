import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayerSelectPanel extends JPanel {

    ArrayList<String> file;

    public PlayerSelectPanel() {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        setBorder(blackLine);
        JLabel pn = new JLabel("Player Name:");
        pn.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        JTextField playerName = new JTextField(8);
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
        file.add("C:\\Users\\adisa\\Desktop\\Chizzy.png");
        file.add("C:\\Users\\adisa\\Desktop\\TA.png");
        file.add("C:\\Users\\adisa\\Desktop\\Chip.png");

        ImageIcon chizzy = scaleImage(file.get(0));
        ImageIcon TA = scaleImage(file.get(1));
        ImageIcon chip = scaleImage((file.get(2)));

        JButton photo = new JButton();
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

    private ImageIcon scaleImage(String filename) {
        ImageIcon scaledImg = new ImageIcon(filename);
        Image img = scaledImg.getImage().getScaledInstance( 75, 75,  java.awt.Image.SCALE_SMOOTH );
        scaledImg = new ImageIcon(img);
        return scaledImg;
    }
}
