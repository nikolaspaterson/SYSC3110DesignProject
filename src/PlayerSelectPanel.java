import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayerSelectPanel extends JPanel {

    ArrayList<String> file;

    public PlayerSelectPanel(String filename) {
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

        ImageIcon chizzy = new ImageIcon(file.get(0));
        Image img1 = chizzy.getImage().getScaledInstance( 75, 75,  java.awt.Image.SCALE_SMOOTH );
        chizzy = new ImageIcon(img1);

        ImageIcon TA = new ImageIcon(file.get(1));
        Image img2 = TA.getImage().getScaledInstance( 75, 75,  java.awt.Image.SCALE_SMOOTH );
        TA = new ImageIcon(img2);

        JButton photo = new JButton();
        photo.setIcon(chizzy);

        ImageIcon finalChizzy = chizzy;
        ImageIcon finalTA = TA;
        photo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(photo.getIcon().equals(finalChizzy)) {
                    photo.setIcon(finalTA);
                } else {
                    photo.setIcon(finalChizzy);
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


}
