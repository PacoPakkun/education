package srv;

import org.springframework.jms.core.JmsOperations;
import utils.Notification;

public class NotificationService {
    private JmsOperations jmsOperations;

    public NotificationService(JmsOperations operations) {
        jmsOperations = operations;
    }

    public void update() {
        try {
            System.out.println("Update notification");
            Notification notification = new Notification();
            notification.setMessage("notification");
            jmsOperations.convertAndSend(notification);
            System.out.println("Sent message to ActiveMQ... ");
        } catch (Throwable t) {
            System.out.println("error sending " + t.getMessage());
        }
    }
}

