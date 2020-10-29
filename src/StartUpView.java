import javax.swing.*;
import java.awt.*;

public class StartUpView extends JFrame {

    //window size constants
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 814;

    //declaring buttons, labels, panels and layouts
    JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>Welcome to RISK!</div></html>");
    JLabel welcomeTextLabel = new JLabel("<html><div style='text-align: center;'>Are you ready to conquer the world?</div></html>");
    JButton playButton = new JButton("Play");
    JButton howToPlayButton = new JButton("How to Play");
    JPanel panel = new JPanel(new BorderLayout(0, 30));

    public StartUpView(){
        super("Welcome");//Sets title of window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close and stop the program when x is clicked
        setLayout(new BorderLayout(0, 250));
        StartUpController controller = new StartUpController(this);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 0));

        //Set font
        welcomeLabel.setFont(new Font("Impact", Font.PLAIN,50));
        welcomeTextLabel.setFont(new Font("Impact", Font.PLAIN,40));
        howToPlayButton.setFont(new Font("Impact", Font.PLAIN,60));
        playButton.setFont(new Font("Impact", Font.PLAIN,60));

        welcomeLabel.setHorizontalAlignment(welcomeLabel.CENTER);
        welcomeLabel.setVerticalAlignment(welcomeLabel.CENTER);

        welcomeTextLabel.setHorizontalAlignment(welcomeTextLabel.CENTER);
        welcomeTextLabel.setVerticalAlignment(welcomeTextLabel.CENTER);

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(welcomeTextLabel, BorderLayout.SOUTH);

        //Set button colors
        howToPlayButton.setBackground(new Color(206, 93, 93));
        playButton.setBackground(new Color(123, 220, 73));

        buttonPanel.add(playButton);
        buttonPanel.add(howToPlayButton);

        add(panel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        setSize(WIDTH,HEIGHT); //set window size
        setVisible(true); //make window visible

        //Add controller to buttonListeners
        howToPlayButton.addActionListener(controller);
        playButton.addActionListener(controller);
    }

    public static void main(String[] args) {
        new StartUpView();
    }
}
