package Model;

import Model.AttackResult;
import Model.Dice;

public class MockDice extends Dice {

    private AttackResult attackOutcome;

    public MockDice()
    {
        this.attackOutcome = AttackResult.A1D1;
    }

    public void setNextAttackOutcome(AttackResult outcome)
    {
        this.attackOutcome = outcome;
    }

    @Override
    public AttackResult attackResult(int[] attackerRoll, int[] defenderRoll) {
        // Always returns attack outcome
        return attackOutcome;
    }
}
