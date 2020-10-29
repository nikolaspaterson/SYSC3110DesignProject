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

    public StatusBar(){
        this.setLayout(new FlowLayout());

        descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel,BoxLayout.Y_AXIS));
        Border darkLine = BorderFactory.createLineBorder(Color.black,3);
        currentAction = new JLabel();
        currentName = new JLabel();
        currentName.setFont(new Font("Impact",Font.PLAIN,15));
        descriptionPanel.add(currentName);
        descriptionPanel.add(currentAction);

        currentPlayerIcon = new JLabel();
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
        currentAction.setText("Time to FIGHT! Attack enemy Territories");

    }

    public void displayReinforce(){

        if(player.getDeployableTroops() > 0){
            nextStep.setBackground(new Color(0x6C6C6C));
        }else{
            nextStep.setBackground(new Color(178, 236, 83));
        }
        nextStep.setText("Next"); //add to first if after implementation
        currentAction.setText("Reinforce your land! \n You can deploy " + player.getDeployableTroops() + " troops!");

    }
    public void displayFortify(){
        currentAction.setText("Fortify! <br> Move troops from your territories");
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
