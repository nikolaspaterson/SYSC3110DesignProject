import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FortifyPopUpController implements ActionListener {

    private FortifyPopUp popup;
    private GameEvent ge;

    public FortifyPopUpController(FortifyPopUp fortifyPopUp){
        this.popup = fortifyPopUp;
        //ge = new GameEvent(popup.getPlayer());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int x = Integer.valueOf(popup.getTroops().getText());
        //int movableTroops =

        if(e.getSource().equals(popup.getPlusButton())){

        }else if(e.getSource().equals(popup.getMinusButton())){
            if(x > 0){
                x--;
            }
            popup.setTroops(x);
        }else if(e.getSource().equals(popup.getFortifyButton())){
            //new game event
        }

    }
}
