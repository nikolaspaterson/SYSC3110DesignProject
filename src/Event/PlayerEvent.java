package Event;

import Model.Player;

import java.util.EventObject;

public class PlayerEvent extends EventObject {
    private int deployable_troops;
    private int total_troops;

    /**
     * Constructs a prototypical Event.
     *
     * @param player the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */

    public PlayerEvent(Player player, int deployable_troops, int total_troops){
        super(player);
        this.deployable_troops = deployable_troops;
        this.total_troops = total_troops;
    }

    public int getDeployable_troops() {
        return deployable_troops;
    }

    public int getTotal_troops() {
        return total_troops;
    }
}
