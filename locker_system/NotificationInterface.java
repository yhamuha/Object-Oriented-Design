package locker_system;

import locker_system.account.Account;

public interface NotificationInterface {
    void sendNotification(String message, Account user);
}
