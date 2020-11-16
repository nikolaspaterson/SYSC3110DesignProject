package View;

import Controller.StartUpController;
import javax.swing.*;
import java.awt.*;

/**
 * Main menu for our game.
 * @author nikolaspaterson
 */
public class StartUpView extends JFrame {

    //window size constants
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 814;

    /**
     * Creates a JFrame for our main menu.
     */
    public StartUpView() {
        super("Welcome");//Sets title of window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close and stop the program when x is clicked
        setLayout(new BorderLayout(0, 250));
        StartUpController controller = new StartUpController(this);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 0));

        // Welcome Label (Welcome to RISK!)
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>Welcome to RISK!</div></html>");
        welcomeLabel.setFont(new Font("Impact", Font.PLAIN, 50));

        // Welcome Text Label (Description of the game)
        JLabel welcomeTextLabel = new JLabel("<html><div style='text-align: center;'>Are you ready to conquer the world?</div></html>");
        welcomeTextLabel.setFont(new Font("Impact", Font.PLAIN, 40));

        // How to Play Button
        JButton howToPlayButton = new JButton("How to Play");
        howToPlayButton.setFont(new Font("Impact", Font.PLAIN, 60));

        // Play Button
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Impact", Font.PLAIN, 60));

        welcomeLabel.setHorizontalAlignment(welcomeLabel.CENTER);
        welcomeLabel.setVerticalAlignment(welcomeLabel.CENTER);

        welcomeTextLabel.setHorizontalAlignment(welcomeTextLabel.CENTER);
        welcomeTextLabel.setVerticalAlignment(welcomeTextLabel.CENTER);

        JPanel panel = new JPanel(new BorderLayout(0, 30));
        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(welcomeTextLabel, BorderLayout.SOUTH);

        // Setting button colors
        howToPlayButton.setBackground(new Color(206, 93, 93));
        playButton.setBackground(new Color(123, 220, 73));

        buttonPanel.add(playButton);
        buttonPanel.add(howToPlayButton);

        add(panel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        setSize(WIDTH, HEIGHT); //set window size
        setVisible(true); //make window visible

        //Add controller to buttonListeners
        howToPlayButton.addActionListener(controller);
        playButton.addActionListener(controller);
    }

    /**
     * main method that starts the game
     * @param args default
     */
    public static void main(String[] args) {
        new StartUpView();
    }
}
