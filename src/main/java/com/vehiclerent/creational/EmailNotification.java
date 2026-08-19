package com.vehiclerent.creational;

import com.vehiclerent.util.ConsoleOutput;

/**
 * Factory Method Pattern — Concrete product for Email notifications.
 * Why: Implements the Notification interface to handle email-specific delivery.
 * Analogy: A specific type of envelope — the "airmail" variant that knows how to fly.
 */
public class EmailNotification implements Notification {

    @Override
    public void send(String recipient, String message) {
        ConsoleOutput.printHeading("FACTORY METHOD PATTERN - NOTIFICATION");
        ConsoleOutput.printLine("Sending Email Notification...");
        ConsoleOutput.printLine("To: " + recipient);
        ConsoleOutput.printLine("Message: " + message);
    }
}
