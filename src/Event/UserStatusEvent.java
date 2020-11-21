package Event;

import Model.GameState;
import Model.Player;
import Model.GameModel;

import java.util.EventObject;

public class UserStatusEvent extends EventObject {
    private Player currentPlayer;
    private GameState gameState;


    /**
     * Constructs a prototypical Event.
     *
     * @throws IllegalArgumentException if source is null
     */
    public UserStatusEvent(GameModel model, Player currentPlayer, GameState gameState) {
        super(model);
        this.currentPlayer = currentPlayer;
        this.gameState = gameState;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }
}
