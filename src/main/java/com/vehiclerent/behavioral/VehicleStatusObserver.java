package com.vehiclerent.behavioral;

/**
 * Observer Pattern — Observer interface for vehicle status change notifications.
 * Why: Defines a common contract so the subject can notify all interested
 * parties without knowing their concrete types.
 */
public interface VehicleStatusObserver {

    void update(String vehicleId, String newStatus);
}
