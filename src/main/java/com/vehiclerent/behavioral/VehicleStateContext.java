package com.vehiclerent.behavioral;

import com.vehiclerent.util.ConsoleOutput;

/**
 * State Pattern — Context that holds current state and delegates behavior
 * to state objects.
 * Why: Transitions are explicit and maintainable; adding a new state does
 * not require changing existing states.
 */
public class VehicleStateContext {

    private VehicleState currentState;
    private final String vehicleId;

    public VehicleStateContext(String vehicleId) {
        this.vehicleId = vehicleId;
        this.currentState = new AvailableState();
    }

    public void rent() {
        currentState.rent(this);
    }

    public void returnVehicle() {
        currentState.returnVehicle(this);
    }

    public void sendToMaintenance() {
        currentState.sendToMaintenance(this);
    }

    public void setState(VehicleState state) {
        this.currentState = state;
        ConsoleOutput.printLine("Vehicle " + vehicleId + " status changed to: " + state.getStatusName());
    }

    public String getStatusName() {
        return currentState.getStatusName();
    }

    public String getVehicleId() {
        return vehicleId;
    }
}
