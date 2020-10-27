import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerSelectPanel extends JPanel {

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

        JLabel personalPhoto = new JLabel();

        ImageIcon pic = new ImageIcon(filename);
        Image img123 = pic.getImage().getScaledInstance( 75, 75,  java.awt.Image.SCALE_SMOOTH );
        pic = new ImageIcon(img123);

        personalPhoto.setIcon(pic);

        add(personalPhoto);
        add(pn);
        add(playerName);
        add(option);

        setSize(600, 600);
        setVisible(true);
    }


}
