package Event;

import Model.Player;
import java.util.EventObject;

public class PlayerEvent extends EventObject {
    private final int deployable_troops;
    private final int total_troops;
    private final boolean status;

    /**
     * Constructs a prototypical Event.
     *
     * @param player the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */

    public PlayerEvent(Player player, int deployable_troops, int total_troops,boolean status){
        super(player);
        this.deployable_troops = deployable_troops;
        this.total_troops = total_troops;
        this.status = status;
    }

    public int getDeployable_troops() {
        return deployable_troops;
    }

    public int getTotal_troops() {
        return total_troops;
    }

    public boolean getStatus() {return status;}
}
