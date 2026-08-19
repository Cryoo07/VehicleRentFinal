package com.vehiclerent.creational;

/**
 * Factory Method Pattern — Concrete creator for Email notifications.
 * Why: The Factory Method hook that instantiates EmailNotification, letting
 *      the superclass (NotificationCreator) handle the common notify flow.
 * Analogy: The kitchen station that only makes airmail envelopes — it stamps
 *          "EMAIL" on every order and hands it to the generic dispatch process.
 */
public class EmailNotificationCreator extends NotificationCreator {

    @Override
    protected Notification createNotification() {
        return new EmailNotification();
    }
}
