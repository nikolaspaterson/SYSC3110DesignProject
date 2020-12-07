package Model;

public enum Priority {
    DEPLOY_THRESHOLD(3),
    ATTACK_THRESHOLD(2),
    LOWEST_PRIORITY(-100),
    SIX(6),
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    ONE(1),
    ZERO(0),
    NEG_FIVE(-5),
    ROLL_THREE(4),
    ROLL_TWO(3),
    MOVE_HALF(2),
    THREE_DICE(3),
    TWO_DICE(2),
    ONE_DIE(1);

    private final int value;

    Priority(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

}
