package Listener;

import Event.TerritoryEvent;

/**
 * This interface is implemented by the PlayerView in order to handle events from the model and update it's view components respectively.
 */
public interface TerritoryView {
    void handleTerritoryUpdate(TerritoryEvent e);
}
