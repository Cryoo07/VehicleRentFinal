package com.vehiclerent.creational;

/**
 * Factory Method Pattern — Creator that delegates notification instantiation to subclasses.
 * Why: Encapsulates object creation so the client code (notifyCustomer) stays unchanged
 *      when new notification types are added — just add a new Creator subclass.
 * Analogy: A pizza shop's order counter — customers order the same way (notifyCustomer),
 *          but the kitchen (subclass) decides whether to make a cheese, pepperoni, or veggie pizza.
 */
public abstract class NotificationCreator {

    public final void notifyCustomer(String recipient, String message) {
        Notification notification = createNotification();
        notification.send(recipient, message);
    }

    protected abstract Notification createNotification();
}
