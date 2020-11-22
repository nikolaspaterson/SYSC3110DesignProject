package Event;

import Model.Player;
import java.util.EventObject;

/**
 * This class is used as the middle man between the PlayerModel and the PlayerView.
 * Whenever a change occurs to the model, a PlayerEvent is created and it notifies the PlayerView.
 */
public class PlayerEvent extends EventObject {

    private final int deployable_troops;
    private final int total_troops;
    private final boolean status;

    /**
     * Constructor for the PlayerEvent class.
     *
     * @param player the object on which the Event initially occurred
     * @param deployable_troops the amount of deployable troops
     * @param total_troops the total number of troops owned by the player
     * @param status the status
     */
    public PlayerEvent(Player player, int deployable_troops, int total_troops, boolean status){
        super(player);
        this.deployable_troops = deployable_troops;
        this.total_troops = total_troops;
        this.status = status;
    }

    /**
     * Getter for the deployable troop count
     * @return int deployable troop count
     */
    public int getDeployable_troops() {
        return deployable_troops;
    }

    /**
     * Getter for the total troop count
     * @return int total troop count
     */
    public int getTotal_troops() {
        return total_troops;
    }

    /**
     * Getter for the status.
     * @return boolean status
     */
    public boolean getStatus() {return status;}
}