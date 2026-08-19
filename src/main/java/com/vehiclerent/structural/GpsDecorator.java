package com.vehiclerent.structural;

import com.vehiclerent.util.ConsoleOutput;
import com.vehiclerent.vehicle.Vehicle;

/**
 * Decorator Pattern — Concrete decorator that adds a GPS Navigation add-on to a vehicle.
 * Wraps a {@link Vehicle} and enriches its description and total price with a GPS surcharge.
 */
public class GpsDecorator extends VehicleDecorator {

    private static final double GPS_COST = 15.0;

    public GpsDecorator(Vehicle vehicle) {
        super(vehicle);
        ConsoleOutput.printLine("Decorator Applied: GPS (+$" + GPS_COST + "/day)");
    }

    @Override
    public String getDescription() {
        return wrappedVehicle.getDescription() + " + GPS Navigation";
    }

    @Override
    public double getTotalPrice() {
        return wrappedVehicle.getTotalPrice() + GPS_COST;
    }
}
