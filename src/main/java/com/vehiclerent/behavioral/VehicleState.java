package com.vehiclerent.behavioral;

import com.vehiclerent.util.ConsoleOutput;

/**
 * State Pattern — Interface for vehicle availability states (Available, Booked,
 * UnderMaintenance).
 * Why: Each state encapsulates its own transition rules, eliminating
 * if-else chains and making state-specific behavior easy to extend.
 */
public interface VehicleState {

    void rent(VehicleStateContext context);

    void returnVehicle(VehicleStateContext context);

    void sendToMaintenance(VehicleStateContext context);

    String getStatusName();
}
