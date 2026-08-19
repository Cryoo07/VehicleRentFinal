package com.vehiclerent.behavioral;

import com.vehiclerent.util.ConsoleOutput;

/**
 * Observer Pattern — Subject that maintains observers and broadcasts status changes.
 * Why: Decouples status tracking from notification logic; new observers can be
 * added without modifying existing code.
 */
public class VehicleStatusSubject {

    private final java.util.List<VehicleStatusObserver> observers = new java.util.ArrayList<>();

    public void attach(VehicleStatusObserver observer) {
        observers.add(observer);
    }

    public void detach(VehicleStatusObserver observer) {
        observers.remove(observer);
    }

    public void setStatus(String vehicleId, String newStatus) {
        notifyObservers(vehicleId, newStatus);
    }

    private void notifyObservers(String vehicleId, String newStatus) {
        ConsoleOutput.printLine("Subject broadcasting status change...");
        for (VehicleStatusObserver observer : observers) {
            observer.update(vehicleId, newStatus);
        }
    }
}
