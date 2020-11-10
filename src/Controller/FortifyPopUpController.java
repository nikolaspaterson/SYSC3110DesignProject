package Controller;

import Model.GameEvent;
import View.FortifyPopUp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that responds to actions performed in View.FortifyPopUp.
 */
public class FortifyPopUpController implements ActionListener {

    private final FortifyPopUp popup;
    private final GameEvent ge;

    /**
     * Controller constructor.
     * @param fortifyPopUp - instance of View.FortifyPopUp
     */
    public FortifyPopUpController(FortifyPopUp fortifyPopUp){
        this.popup = fortifyPopUp;
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

        if(e.getSource().equals(popup.getPlusButton())){//if plus button is pressed
            if(x == (popup.getTerritoryToLoseTroops().getTroops()-1)) {
                x = 0;
            } else {
                x++;
            }
            popup.setTroops(x);
        }else if(e.getSource().equals(popup.getMinusButton())){//if minus button is pressed
            if(x == 0) {
                x = (popup.getTerritoryToLoseTroops().getTroops()-1);
            } else {
                x--;
            }
            popup.setTroops(x);
        }else if(e.getSource().equals(popup.getFortifyButton())){//if fortify button is pressed
            ge.fortify(popup.getTerritoryToLoseTroops(), popup.getTerritoryToGainTroops(), x);
            popup.setVisible(false);
        }
    }
}
