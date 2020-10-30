import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that responds to actions performed in ReinforcePopUp.
 */
public class ReinforcePopUpController implements ActionListener {

    private final ReinforcePopUp popup;
    private final GameEvent ge;

    /**
     * Controller constructor.
     * @param reinforcepopup - instance of ReinforcePopUp
     */
    public ReinforcePopUpController(ReinforcePopUp reinforcepopup){
        this.popup = reinforcepopup;
        ge = new GameEvent(popup.getPlayer());
    }

    /**
     * Performs an action based on which button triggered the event
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Maximum number of troops that can be moved
        int x = Integer.parseInt(popup.getTroops().getText());
        //number of troops the player want to move
        int deployableTroops = popup.getPlayer().getDeployableTroops();

        if(e.getSource().equals(popup.getPlus())){//if plus button is pressed
            if(x < deployableTroops){
                x++;
            }
            popup.setTroops(x);
        }else if(e.getSource().equals(popup.getMinus())){//if minus is pressed
            if(x > 0){
                x--;
            }
            popup.setTroops(x);
        }else if(e.getSource().equals(popup.getDeployButton())){ //if deploy button is pressed
            if(x > 0){
                ge.reinforce(popup.getTerritory(), x);
                popup.setVisible(false);
            }
        }
    }
}
