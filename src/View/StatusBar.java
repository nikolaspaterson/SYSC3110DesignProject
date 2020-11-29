package View;

import Controller.GameController;
import Event.PlayerEvent;
import Listener.PlayerListener;
import Model.Player;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Object constructor for View.StatusBar JPanel which stays at the bottom of the screen and displays
 */
public class StatusBar extends JPanel implements PlayerListener {

    private final JLabel currentPlayerIcon;
    private final JPanel descriptionPanel;
    private final JButton nextStep;
    private final JLabel currentAction;
    private final JLabel currentName;
    private final JLabel deployLabel;

    /**
     * Constructor for the StatusBar class.
     */
    public StatusBar(){
        this.setLayout(new GridLayout(1, 3, 3, 0));

        descriptionPanel = new JPanel();
        descriptionPanel.setMinimumSize(new Dimension(200, 40));
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel,BoxLayout.Y_AXIS));
        Border darkLine = BorderFactory.createLineBorder(Color.black,3);

        currentAction = new JLabel();

        currentName = new JLabel();
        currentName.setFont(new Font("Impact",Font.PLAIN,20));

        descriptionPanel.add(currentName);
        descriptionPanel.add(currentAction);

        currentPlayerIcon = new JLabel();
        currentPlayerIcon.setHorizontalAlignment(currentPlayerIcon.CENTER);
        currentPlayerIcon.setVerticalAlignment(currentPlayerIcon.CENTER);

        nextStep = new JButton("Next");
        nextStep.setFont(new Font("Impact",Font.PLAIN,30));
        nextStep.setBackground(new Color(178, 236, 83));

        deployLabel = new JLabel();

        setBounds(306,697,539,86);
        add(currentPlayerIcon);
        add(descriptionPanel);
        setBorder(darkLine);
        add(nextStep);
    }

    /**
     * Set current player in the object Status bar may reference variables from the player such as deployLabel which
     * updates when the deployable troops value goes down
     * @param player Model.Player Object
     */
    public void setPlayer(Player player){
        deployLabel.setText(String.valueOf(player.getDeployableTroops()));
        currentName.setText(player.getName());
        currentPlayerIcon.setIcon(player.getPlayer_icon());
        descriptionPanel.setBackground(player.getPlayer_color().brighter());
        setBackground(player.getPlayer_color());
        displayReinforce();
    }

    /**
     * Set the action for nextStep button from nextButtonController
     * @param control Game controller which contains action listener nextState
     */
    public void setController(GameController control){
        nextStep.addActionListener(control::nextState);
    }

    /**
     * Displays to the user that it is currently the attacking phase, and removes the deployLabel and repaints the canvas
     * The method is called by updateDisplay for when the player interacts with nextStep
     */
    public void displayAttack(){
        deployLabel.setText("");
        currentAction.setText("<html>Time to FIGHT!<br>Attack enemy Territories</html>");
    }

    /**
     * Displays to the user that it is currently the Reinforce phase, and adds the deployLabel and repaints the canvas
     * The method is called by updateDisplay for when the player interacts with nextStep
     */
    public void displayReinforce(){
        descriptionPanel.add(deployLabel);
        this.revalidate();
        this.repaint();
        nextStep.setBackground(new Color(113, 220, 70));
        nextStep.setText("Next"); //add to first if after implementation
        currentAction.setText("<html>Troops to deploy!");
    }

    /**
     * Displays to the user that it is currently the Fortify Phase
     * The method is called by updateDisplay for when the player interacts with nextStep
     */
    public void displayFortify(){
        deployLabel.setText("");
        currentAction.setText("<html>Fortify!<br>Move troops from<br>Your territories</html>");
        nextStep.setBackground(new Color(0xF16262));
        nextStep.setText("End Turn");
    }

    /**
     * This method is used to handle updates from the PlayerModel and update the view components respectively.
     * @param e player event
     */
    @Override
    public void handlePlayerUpdate(PlayerEvent e) {
        deployLabel.setText(String.valueOf(e.getDeployable_troops()));
    }
}