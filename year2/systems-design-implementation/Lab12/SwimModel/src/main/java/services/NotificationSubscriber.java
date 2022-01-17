package services;

import utils.Notification;

/**
 * Created by grigo on 5/3/17.
 */
public interface NotificationSubscriber {
    void notificationReceived(Notification notif);
}
