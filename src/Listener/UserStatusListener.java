package Listener;

import Event.UserStatusEvent;

public interface UserStatusListener {
    void updateUserStatus(UserStatusEvent event);
}
