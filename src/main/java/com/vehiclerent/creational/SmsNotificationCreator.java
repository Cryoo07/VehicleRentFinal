package com.vehiclerent.creational;

/**
 * Factory Method Pattern — Concrete creator for SMS notifications.
 * Why: The Factory Method hook that instantiates SmsNotification, letting
 *      the superclass (NotificationCreator) handle the common notify flow.
 * Analogy: The kitchen station that only crafts text-message envelopes — it stamps
 *          "SMS" on every order and hands it to the generic dispatch process.
 */
public class SmsNotificationCreator extends NotificationCreator {

    @Override
    protected Notification createNotification() {
        return new SmsNotification();
    }
}
