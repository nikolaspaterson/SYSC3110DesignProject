public class MockDice extends Dice {
    private int attackOutcome;

    public MockDice()
    {
        this.attackOutcome = 0;
    }

    public void setNextAttackOutcome(int outcome)
    {
        this.attackOutcome = outcome;
    }

    @Override
    public int attackResult(int[] attackerRoll, int[] defenderRoll) {
        // Always returns attack outcome
        return attackOutcome;
    }
}
