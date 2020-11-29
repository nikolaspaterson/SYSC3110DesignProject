package Listener;

import Event.SaveEvent;

/**
 * This interface is implemented by the GameMenuBar in order to handle events from the model and update it's view components respectively.
 */
public interface SaveListener {

    void updateSave(SaveEvent e);
}
