import javax.swing.*;
import java.awt.*;

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

    public StartUpView(){
        super("Welcome");//Sets title of window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close and stop the program when x is clicked

        panel.setLayout(gLayout); //Set panel layout to GroupLayout


        welcomeLabel.setFont(new Font("Impact", Font.BOLD,50));
        welcomeTextLabel.setFont(new Font("Comic Sans MS", Font.BOLD,35));

        playButton.setFont(new Font("Impact", Font.BOLD,20));
        playButton.setPreferredSize(new Dimension(150, 50));
        howToPlayButton.setFont(new Font("Comic Sans MS", Font.BOLD,20));


        gLayout.setHorizontalGroup(gLayout.createSequentialGroup() //Horizontal alignment, giving in sequence
                .addGap(WIDTH / 9) //leftmost gap
                .addComponent(playButton)
                .addGap(WIDTH / 7) //gap between playButton and text above
                .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.CENTER) //Subgroup that will be centered with eachother
                        .addComponent(welcomeLabel)
                        .addComponent(welcomeTextLabel))
                .addGap(WIDTH / 7) //gap between text and howToPlayButton
                .addComponent(howToPlayButton)
        );

        gLayout.setVerticalGroup(gLayout.createSequentialGroup()
                .addGap(HEIGHT / 6)
                .addComponent(welcomeLabel)
                .addGap(HEIGHT / 20)
                .addComponent(welcomeTextLabel)
                .addGap(HEIGHT / 6)
                    .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(playButton)
                            .addComponent(howToPlayButton))
        );



        setContentPane(panel);
        setSize(WIDTH,HEIGHT); //set window size
        setVisible(true); //make window visible
    }

    public static void main(String[] args) {
        new StartUpView();
    }
}
