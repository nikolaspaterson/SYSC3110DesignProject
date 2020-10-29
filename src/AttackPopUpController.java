import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackPopUpController implements ActionListener {

    private AttackPopUp attackPopUp;
    private GameEvent gameEvent;

    public AttackPopUpController(AttackPopUp attackPopUp) {
        this.attackPopUp = attackPopUp;
        this.gameEvent = new GameEvent(attackPopUp.getAttackingTerritory().getOccupant());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int attackingTroops = attackPopUp.getAttackingTerritory().getTroops();
        int max;

        if ((attackingTroops - 1) >= 3) {
            max = 3;
        } else if ((attackingTroops - 1) == 2) {
            max = 2;
        } else{
            max = 1;
        }

        if (e.getSource() == attackPopUp.getMinus()) {
            int x = Integer.parseInt(attackPopUp.getNumDice().getText());
            if(x == 1) {
                x = max;
            } else {
                x--;
            }
            attackPopUp.getNumDice().setText(x + "");
        } else if (e.getSource() == attackPopUp.getPlus()) {
            int x = Integer.parseInt(attackPopUp.getNumDice().getText());
            if(x == max) {
                x = 1;
            } else {
                x++;
            }
            attackPopUp.getNumDice().setText(x + "");
        } else {
            gameEvent.attack(attackPopUp.getAttackingTerritory(), attackPopUp.getDefendingTerritory(), Integer.parseInt(attackPopUp.getNumDice().getText()));
            attackPopUp.refreshLabels(gameEvent.getAttackerRolls(), gameEvent.getDefendingRolls(), gameEvent.getResult());
        }
    }
}
