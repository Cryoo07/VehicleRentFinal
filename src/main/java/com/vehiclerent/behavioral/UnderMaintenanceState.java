package com.vehiclerent.behavioral;

import com.vehiclerent.util.ConsoleOutput;

/**
 * State Pattern — Concrete state: vehicle is under maintenance and cannot
 * be rented.
 * Why: Centralizes maintenance-specific rules such as the
 * {@link #completeMaintenance} transition back to AvailableState.
 */
public class UnderMaintenanceState implements VehicleState {

    @Override
    public void rent(VehicleStateContext context) {
        ConsoleOutput.printLine("Vehicle is under maintenance and cannot be rented.");
    }

    @Override
    public void returnVehicle(VehicleStateContext context) {
        ConsoleOutput.printLine("Vehicle is not rented.");
    }

    @Override
    public void sendToMaintenance(VehicleStateContext context) {
        ConsoleOutput.printLine("Vehicle is already under maintenance.");
    }

    public void completeMaintenance(VehicleStateContext context) {
        ConsoleOutput.printHeading("STATE PATTERN - VEHICLE STATUS");
        ConsoleOutput.printLine("Maintenance completed for " + context.getVehicleId());
        context.setState(new AvailableState());
    }

    @Override
    public String getStatusName() {
        return "UnderMaintenance";
    }
}
