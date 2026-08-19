package com.vehiclerent.creational;

import com.vehiclerent.util.ConsoleOutput;

/**
 * Factory Method Pattern — Concrete product for Push notifications.
 * Why: Implements the Notification interface to handle push-specific delivery.
 * Analogy: A specific type of envelope — the "push notification" variant that pings a mobile device directly.
 */
public class PushNotification implements Notification {

    @Override
    public void send(String recipient, String message) {
        ConsoleOutput.printHeading("FACTORY METHOD PATTERN - NOTIFICATION");
        ConsoleOutput.printLine("Sending Push Notification...");
        ConsoleOutput.printLine("To: " + recipient);
        ConsoleOutput.printLine("Message: " + message);
    }
}
