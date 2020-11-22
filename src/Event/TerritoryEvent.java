package Event;

import Model.Player;
import Model.Territory;
import java.awt.*;
import java.util.EventObject;

/**
 * This class is used as the middle man between the TerritoryModel and the TerritoryButton.
 * Whenever a change occurs to the model, a TerritoryEvent is created and it notifies the TerritoryButton.
 */
public class TerritoryEvent extends EventObject {

    private final Player occupant;
    private final int troops;
    private final Color color;

    /**
     * Constructor for the TerritoryEvent class.
     *
     * @param territory the object on which the Event initially occurred
     * @param occupant the player who occupies the Territory
     * @param troops the amount of troops the territory has
     * @param color the color of the player
     */
    public TerritoryEvent(Territory territory, Player occupant, int troops, Color color) {
        super(territory);
        this.occupant = occupant;
        this.troops = troops;
        this.color = color;
    }

    /**
     * Getter for the number of troops.
     * @return int troops
     */
    public int getTroops() { return troops; }

    /**
     * Getter for the Player that is occupying the Territory.
     * @return Player the occupant
     */
    public Player getOccupant() { return occupant; }

    /**
     * Getter for the Color of the occupying player.
     * @return Color the color
     */
    public Color getColor() { return color; }
}
