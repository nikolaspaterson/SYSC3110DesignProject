package Listener;

import Event.PlayerEvent;

/**
 * This interface is implemented by the PlayerView in order to handle events from the model and update it's view components respectively.
 */
public interface PlayerListener {
    void handlePlayerUpdate(PlayerEvent e);
}
