package Event;

import Model.GameState;
import Model.Player;
import Model.GameModel;

import java.util.EventObject;

/**
 * This class is used as the middle man between the GameModel and the GameView.
 * Whenever a change occurs to the model, a UserStatusEvent is created and it notifies the GameView.
 */
public class UserStatusEvent extends EventObject {

    private final Player currentPlayer;
    private final GameState gameState;

    /**
     * Constructor for the UserStatusEvent class.
     *
     * @param model the object on which the Event initially occurred
     * @param currentPlayer the current player's turn
     * @param gameState the state of the game
     */
    public UserStatusEvent(GameModel model, Player currentPlayer, GameState gameState) {
        super(model);
        this.currentPlayer = currentPlayer;
        this.gameState = gameState;
    }

    /**
     * Getter for the current player's turn.
     * @return Player the player in control
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Getter for the GameState.
     * @return GameState the state of the Game
     */
    public GameState getGameState() {
        return gameState;
    }
}
