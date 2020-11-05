package Model;

import Model.Dice;
import Model.GameEvent;
import Model.Player;

public class MockGameEvent extends GameEvent {

    private MockDice mockDice;

    /**
     * Super Constructor for GameEvent class where a GameEvent should be initiated by a Player.
     *
     * @param player Player that initiates the GameEvent
     */
    public MockGameEvent(Player player) {
        super(player);
        this.mockDice = new MockDice();
    }

    @Override
    protected Dice createTempDice() {
        // Use the mock dice in the game
        return mockDice;
    }

    public MockDice getMockDice() {
        return this.mockDice;
    }
}
