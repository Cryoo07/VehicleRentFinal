package com.vehiclerent.structural;

import com.vehiclerent.util.ConsoleOutput;
import com.vehiclerent.vehicle.Vehicle;

/**
 * Decorator Pattern — Concrete decorator that adds a Child Seat add-on to a vehicle.
 * Wraps a {@link Vehicle} and enriches its description and total price with a child-seat surcharge.
 */
public class ChildSeatDecorator extends VehicleDecorator {

    private static final double CHILD_SEAT_COST = 10.0;

    public ChildSeatDecorator(Vehicle vehicle) {
        super(vehicle);
        ConsoleOutput.printLine("Decorator Applied: Child Seat (+$" + CHILD_SEAT_COST + "/day)");
    }

    @Override
    public String getDescription() {
        return wrappedVehicle.getDescription() + " + Child Seat";
    }

    @Override
    public double getTotalPrice() {
        return wrappedVehicle.getTotalPrice() + CHILD_SEAT_COST;
    }
}
