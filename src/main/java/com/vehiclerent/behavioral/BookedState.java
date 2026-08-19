package com.vehiclerent.behavioral;

import com.vehiclerent.util.ConsoleOutput;

/**
 * State Pattern — Concrete state: vehicle is booked/rented out.
 * Why: Prevents invalid actions (e.g. renting again, sending to
 * maintenance) and defines valid transitions from this state.
 */
public class BookedState implements VehicleState {

    @Override
    public void rent(VehicleStateContext context) {
        ConsoleOutput.printLine("Vehicle is already booked.");
    }

    @Override
    public void returnVehicle(VehicleStateContext context) {
        ConsoleOutput.printHeading("STATE PATTERN - VEHICLE STATUS");
        ConsoleOutput.printLine("Action: Return vehicle " + context.getVehicleId());
        context.setState(new AvailableState());
    }

    @Override
    public void sendToMaintenance(VehicleStateContext context) {
        ConsoleOutput.printLine("Cannot send rented vehicle to maintenance. Return first.");
    }

    @Override
    public String getStatusName() {
        return "Booked";
    }
}
