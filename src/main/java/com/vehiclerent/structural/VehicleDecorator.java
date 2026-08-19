package com.vehiclerent.structural;

import com.vehiclerent.vehicle.Vehicle;

/**
 * Decorator Pattern — Base abstract decorator that wraps a {@link Vehicle}.
 * Why: Allows optional features (GPS, Insurance, Child Seat) to be added dynamically at
 * runtime without modifying core vehicle classes, adhering to the Open/Closed Principle.
 */
public abstract class VehicleDecorator implements Vehicle {

    protected final Vehicle wrappedVehicle;

    protected VehicleDecorator(Vehicle wrappedVehicle) {
        this.wrappedVehicle = wrappedVehicle;
    }

    @Override
    public String getId() {
        return wrappedVehicle.getId();
    }

    @Override
    public String getType() {
        return wrappedVehicle.getType();
    }

    @Override
    public String getModel() {
        return wrappedVehicle.getModel();
    }

    @Override
    public double getBasePrice() {
        return wrappedVehicle.getBasePrice();
    }
}
