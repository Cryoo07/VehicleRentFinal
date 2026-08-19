package com.vehiclerent.structural;

import com.vehiclerent.util.ConsoleOutput;
import com.vehiclerent.vehicle.Vehicle;

/**
 * Decorator Pattern — Concrete decorator that adds Full Insurance add-on to a vehicle.
 * Wraps a {@link Vehicle} and enriches its description and total price with an insurance surcharge.
 */
public class InsuranceDecorator extends VehicleDecorator {

    private static final double INSURANCE_COST = 25.0;

    public InsuranceDecorator(Vehicle vehicle) {
        super(vehicle);
        ConsoleOutput.printLine("Decorator Applied: Insurance (+$" + INSURANCE_COST + "/day)");
    }

    @Override
    public String getDescription() {
        return wrappedVehicle.getDescription() + " + Full Insurance";
    }

    @Override
    public double getTotalPrice() {
        return wrappedVehicle.getTotalPrice() + INSURANCE_COST;
    }
}
