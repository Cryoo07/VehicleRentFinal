package com.vehiclerent.behavioral;

import com.vehiclerent.util.ConsoleOutput;

/**
 * State Pattern — Concrete state: vehicle is available for rent.
 * Why: Handles rent/return/maintenance actions according to the rules
 * that apply only when the vehicle is available.
 */
public class AvailableState implements VehicleState {

    @Override
    public void rent(VehicleStateContext context) {
        ConsoleOutput.printHeading("STATE PATTERN - VEHICLE STATUS");
        ConsoleOutput.printLine("Action: Rent vehicle " + context.getVehicleId());
        context.setState(new BookedState());
    }

    @Override
    public void returnVehicle(VehicleStateContext context) {
        ConsoleOutput.printLine("Vehicle is not currently rented.");
    }

    @Override
    public void sendToMaintenance(VehicleStateContext context) {
        ConsoleOutput.printLine("Sending vehicle to maintenance...");
        context.setState(new UnderMaintenanceState());
    }

    @Override
    public String getStatusName() {
        return "Available";
    }
}
