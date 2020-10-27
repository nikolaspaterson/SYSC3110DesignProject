import javax.swing.*;
import java.awt.*;

public class StartUpView extends JFrame {

    //window size constants
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 814;

    //declaring buttons, labels, panels and layouts
    JLabel welcomeLabel = new JLabel("Welcome");
    JButton playButton = new JButton("Play");
    JButton howToPlayButton = new JButton("How to Play");
    JPanel panel = new JPanel();
    GroupLayout gLayout = new GroupLayout(panel);

    public StartUpView(){
        super("Welcome to RISK");//Sets title of window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close and stop the program when x is clicked
        setMinimumSize(new Dimension(WIDTH,HEIGHT)); //set minimum window size
        setLayout(null); //no layout for window
        add(panel); //add panel to window
        panel.setLayout(gLayout); //set panel layout to grouplayout

        gLayout.setAutoCreateGaps(true); //let layout create its own gaps between components
        gLayout.setAutoCreateContainerGaps(true);

        //grouplayout has 2 dimensions that you declare separately, vertical and horizontal
        gLayout.setHorizontalGroup( //create horizontal group
                gLayout.createSequentialGroup() //create a group of components in sequence
                    .addComponent(welcomeLabel)
                    .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.LEADING) //creating a sub group
                        .addComponent(playButton)
                        .addComponent(howToPlayButton))
        );

        gLayout.setVerticalGroup(
                gLayout.createSequentialGroup()
                    .addComponent(welcomeLabel)
                    .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(playButton)
                            .addComponent(howToPlayButton))
        );

/*
        welcomeLabel.setBounds(WIDTH * (1/3),HEIGHT * (1/3),WIDTH * (2/3),HEIGHT * (1/10));

        panel.add(welcomeLabel);

        add(welcomeLabel);
        add(playButton);
        add(howToPlayButton);*/

        setSize(WIDTH,HEIGHT); //set window size
        setVisible(true); //make window visible
    }

    public static void main(String[] args) {
        new StartUpView();
    }
}
