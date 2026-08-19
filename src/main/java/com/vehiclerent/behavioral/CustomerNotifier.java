package com.vehiclerent.behavioral;

import com.vehiclerent.util.ConsoleOutput;

/**
 * Observer Pattern — Concrete observer that notifies customers when vehicle
 * status changes.
 * Why: Isolates notification logic from the subject; multiple observer types
 * can react to the same event independently.
 */
public class CustomerNotifier implements VehicleStatusObserver {

    private final String customerName;
    private final String contact;

    public CustomerNotifier(String customerName, String contact) {
        this.customerName = customerName;
        this.contact = contact;
    }

    @Override
    public void update(String vehicleId, String newStatus) {
        ConsoleOutput.printHeading("OBSERVER PATTERN - STATUS NOTIFICATION");
        ConsoleOutput.printLine("Notifying Customer: " + customerName);
        ConsoleOutput.printLine("Contact: " + contact);
        ConsoleOutput.printLine("Vehicle " + vehicleId + " is now: " + newStatus);
    }
}
