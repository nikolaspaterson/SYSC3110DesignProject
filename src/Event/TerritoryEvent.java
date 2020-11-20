package Event;

import Model.Player;
import Model.Territory;
import java.awt.*;
import java.util.EventObject;

public class TerritoryEvent extends EventObject {

    private final Player occupant;
    private final int troops;
    private final Color color;

    public TerritoryEvent(Territory territory, Player occupant, int troops, Color color) {
        super(territory);
        this.occupant = occupant;
        this.troops = troops;
        this.color = color;
    }

    public int getTroops() { return troops; }

    public Player getOccupant() { return occupant; }

    public Color getColor() { return color; }
}
