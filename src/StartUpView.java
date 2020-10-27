import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartUpView extends JFrame {

    //window size constants
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 814;

    //declaring buttons, labels, panels and layouts
    JLabel welcomeLabel = new JLabel("Welcome to RISK!");
    JLabel welcomeTextLabel = new JLabel("Are you ready to conquer the world?");
    JButton playButton = new JButton("Play");
    JButton howToPlayButton = new JButton("How to Play");
    JPanel panel = new JPanel();
    GroupLayout gLayout = new GroupLayout(panel);
    JPopupMenu popup = new JPopupMenu();
    JMenuItem temp = new JMenuItem("Temp");
    JMenuItem temp2 = new JMenuItem("Tempppppp");

    public StartUpView(){
        super("Welcome");//Sets title of window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close and stop the program when x is clicked

        panel.setLayout(gLayout); //Set panel layout to GroupLayout

        //Set font
        welcomeLabel.setFont(new Font("Impact", Font.PLAIN,50));
        welcomeTextLabel.setFont(new Font("Impact", Font.PLAIN,35));
        howToPlayButton.setFont(new Font("Impact", Font.PLAIN,60));
        playButton.setFont(new Font("Impact", Font.PLAIN,60));

        //Set button colors
        howToPlayButton.setBackground(new Color(206, 93, 93));
        playButton.setBackground(new Color(123, 220, 73));

        //set horizontal layout, align components by center point
        gLayout.setHorizontalGroup(gLayout.createParallelGroup(GroupLayout.Alignment.CENTER)

                .addGroup(gLayout.createSequentialGroup() //create sub group for buttons, drawing in sequence
                        .addGap(WIDTH / 3) //left most gap
                        .addComponent(playButton)
                        .addGap(WIDTH / 6) //gap between buttons
                        .addComponent(howToPlayButton)
                        .addGap(2 * WIDTH / 7) //adds space after buttons, shifts text to right
                )

                .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.CENTER) //sub group from text, aligned by center point
                        .addComponent(welcomeLabel)
                        .addComponent(welcomeTextLabel)
                )
        );

        //set vertical layout
        gLayout.setVerticalGroup(gLayout.createSequentialGroup()
                .addGap(HEIGHT / 6) //top gap
                .addComponent(welcomeLabel)
                .addGap(HEIGHT / 20) //gap between text
                .addComponent(welcomeTextLabel)
                .addGap(HEIGHT / 6)
                    .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.BASELINE) //subgroup, vertically aligns 2 buttons
                            .addComponent(playButton)
                            .addComponent(howToPlayButton))
        );


        popup.add(temp);
        popup.add(temp2);

        howToPlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component c = (Component) e.getSource();
                panel.add(popup);
                popup.show(c,panel.getX(),panel.getY());
            }
        });

        setContentPane(panel);
        setSize(WIDTH,HEIGHT); //set window size
        setVisible(true); //make window visible
    }

    public static void main(String[] args) {
        new StartUpView();
    }
}
