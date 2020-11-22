package Listener;

import Event.UserStatusEvent;

/**
 * This interface is implemented by the PlayerView in order to handle events from the model and update it's view components respectively.
 */
public interface UserStatusListener {
    void updateUserStatus(UserStatusEvent event);
}

