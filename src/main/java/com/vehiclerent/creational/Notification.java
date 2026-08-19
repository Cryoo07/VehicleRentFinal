package com.vehiclerent.creational;

/**
 * Factory Method Pattern — Product interface for all notification types.
 * Why: Defines a common contract so the creator can work with any notification
 *      without knowing the concrete class at compile time.
 * Analogy: A generic "envelope" contract — whether it carries a letter, a postcard,
 *          or a formal document, the postal worker delivers it the same way.
 */
public interface Notification {

    void send(String recipient, String message);
}
