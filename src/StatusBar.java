import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class StatusBar extends JPanel {

    private final JLabel currentPlayerIcon;
    private final JPanel descriptionPanel;
    private final JButton nextStep;
    private final JLabel currentAction;
    private final JLabel currentName;
    private Player player;
    private GameController nextButtonController;
    private JLabel deployLabel;

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

        setBounds(306,697,539,86);
        add(currentPlayerIcon);
        add(descriptionPanel);
        setBorder(darkLine);
        add(nextStep);

    }
    public void setPlayer(Player player){
        this.player = player;
        deployLabel = player.getDeployLabel();
        currentName.setText(player.getName());
        currentPlayerIcon.setIcon(player.getplayer_icon());
        descriptionPanel.setBackground(player.getBackground().brighter());
        setBackground(player.getBackground());
        displayReinforce();
    }
    public void setController(GameController control){
        nextButtonController = control;
        nextStep.addActionListener(nextButtonController::nextState);
    }
    public void displayAttack(){
        descriptionPanel.remove(deployLabel);
        this.revalidate();
        this.repaint();

        currentAction.setText("<html>Time to FIGHT!<br>Attack enemy Territories</html>");

    }

    public void displayReinforce(){
        descriptionPanel.add(deployLabel);
        this.revalidate();
        this.repaint();
        nextStep.setBackground(new Color(113, 220, 70));
        nextStep.setText("Next"); //add to first if after implementation
        currentAction.setText("<html>Troops to deploy!");

    }
    public void displayFortify(){
        currentAction.setText("<html>Fortify!<br>Move troops from<br>Your territories</html>");
        nextStep.setBackground(new Color(0xF16262));
        nextStep.setText("End Turn");
    }
    public void updateDisplay(String state){
        if(state == "Reinforce"){

            displayReinforce();
        } else if(state == "Attack"){
            displayAttack();
        } else if(state == "Fortify"){
            displayFortify();
        }

    }
}