import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackPopUpController implements ActionListener {

    private AttackPopUp attackPopUp;

    public AttackPopUpController(AttackPopUp attackPopUp) { this.attackPopUp = attackPopUp; }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
        if (e.getSource() == attackPopUp.getMinus()) {
            int x = Integer.parseInt(attackPopUp.getNumTroops().getText());
            if(x == 1) {
                x = 3;
            } else {
                x--;
            }
            attackPopUp.getNumTroops().setText(x + "");
        } else if (e.getSource() == attackPopUp.getPlus()) {
            int x = Integer.parseInt(attackPopUp.getNumTroops().getText());
            if(x == 3) {
                x = 1;
            } else {
                x++;
            }
            attackPopUp.getNumTroops().setText(x + "");
        } else {
            attackPopUp.refreshLabels();
        }
    }
}
