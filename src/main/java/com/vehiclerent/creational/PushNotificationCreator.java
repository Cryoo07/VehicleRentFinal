package com.vehiclerent.creational;

/**
 * Factory Method Pattern — Concrete creator for Push notifications.
 * Why: The Factory Method hook that instantiates PushNotification, letting
 *      the superclass (NotificationCreator) handle the common notify flow.
 * Analogy: The kitchen station that only makes push-alert envelopes — it stamps
 *          "PUSH" on every order and hands it to the generic dispatch process.
 */
public class PushNotificationCreator extends NotificationCreator {

    @Override
    protected Notification createNotification() {
        return new PushNotification();
    }
}
