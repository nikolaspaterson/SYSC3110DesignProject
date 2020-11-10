package Controller;

import Model.GameEvent;
import Model.Player;
import Model.Territory;
import View.AttackPopUp;
import View.FortifyPopUp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Controller.AttackPopUpController class is used to change and update the View.AttackPopUp based which buttons are pressed.
 *
 * @author Ahmad El-Sammak
 */
public class AttackPopUpController implements ActionListener {

    private final AttackPopUp attackPopUp;
    private final GameEvent gameEvent;

    /**
     * Class constructor for Controller.AttackPopUpController class.
     * @param attackPopUp the view that needs to be changed.
     */
    public AttackPopUpController(AttackPopUp attackPopUp) {
        this.attackPopUp = attackPopUp;
        this.gameEvent = new GameEvent(attackPopUp.getAttackingTerritory().getOccupant());
    }

    /**
     * Checks to see which button is responsible for the ActionEvent and performs the respected action based on which button was pressed.
     * @param e the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Integer.parseInt(attackPopUp.getNumDice().getText()) != 0) {
            int attackingTroops = attackPopUp.getAttackingTerritory().getTroops();
            int max;

            if ((attackingTroops - 1) >= 3) {
                max = 3;
            } else if ((attackingTroops - 1) == 2) {
                max = 2;
            } else {
                max = 1;
            }

            if (e.getSource() == attackPopUp.getMinus()) {
                int x = Integer.parseInt(attackPopUp.getNumDice().getText());
                if (x == 1) {
                    x = max;
                } else {
                    x--;
                }
                attackPopUp.getNumDice().setText(x + "");
            } else if (e.getSource() == attackPopUp.getPlus()) {
                int x = Integer.parseInt(attackPopUp.getNumDice().getText());
                if (x == max) {
                    x = 1;
                } else {
                    x++;
                }
                attackPopUp.getNumDice().setText(x + "");
            } else {
                Territory tempDef = attackPopUp.getDefendingTerritory();
                Player def_owner = tempDef.getOccupant();
                gameEvent.attack(attackPopUp.getAttackingTerritory(), attackPopUp.getDefendingTerritory(), Integer.parseInt(attackPopUp.getNumDice().getText()));
                attackPopUp.refreshLabels(gameEvent.getAttackerRolls(), gameEvent.getDefendingRolls(), gameEvent.getResult());
                Territory tempAttk = attackPopUp.getAttackingTerritory();
                tempAttk.updateView();
                tempDef.updateView();
                if(gameEvent.getAttackerWon()) {
                    FortifyPopUp fortifyPopUp = new FortifyPopUp(attackPopUp.getAttackingTerritory(), attackPopUp.getDefendingTerritory());
                    fortifyPopUp.show(attackPopUp.getGameViewRef(), 300, 350);
                }
            }
        }
    }
}
