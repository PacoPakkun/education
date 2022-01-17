package ams;

import org.springframework.jms.core.JmsOperations;
import services.NotificationSubscriber;
import utils.Notification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NotificationReceiver {
    private JmsOperations jmsOperations;
    private boolean running;

    public NotificationReceiver(JmsOperations operations) {
        jmsOperations = operations;
    }

    ExecutorService service;
    NotificationSubscriber subscriber;

    public void start(NotificationSubscriber subscriber) {
        System.out.println("Starting notification receiver ...");
        running = true;
        this.subscriber = subscriber;
        service = Executors.newSingleThreadExecutor();
        service.submit(() -> {
            run();
        });
    }

    private void run() {
        while (running) {
            try {
                Notification notif = (Notification) jmsOperations.receiveAndConvert();
                System.out.println("Received Notification... ");
                subscriber.notificationReceived(notif);
            } catch (Throwable t) {
                System.out.println("error client " + t.getMessage());
            }
        }
    }

    public void stop() {
        running = false;
        try {
            service.awaitTermination(100, TimeUnit.MILLISECONDS);
            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopped notification receiver");
    }
}