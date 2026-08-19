package com.vehiclerent.creational;

import com.vehiclerent.util.ConsoleOutput;

/**
 * Factory Method Pattern — Concrete product for SMS notifications.
 * Why: Implements the Notification interface to handle SMS-specific delivery.
 * Analogy: A specific type of envelope — the "text message" variant that travels via cellular networks.
 */
public class SmsNotification implements Notification {

    @Override
    public void send(String recipient, String message) {
        ConsoleOutput.printHeading("FACTORY METHOD PATTERN - NOTIFICATION");
        ConsoleOutput.printLine("Sending SMS Notification...");
        ConsoleOutput.printLine("To: " + recipient);
        ConsoleOutput.printLine("Message: " + message);
    }
}
