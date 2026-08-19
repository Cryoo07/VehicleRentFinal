package com.vehiclerent.creational;

import com.vehiclerent.util.ConsoleOutput;
import com.vehiclerent.vehicle.Bike;
import com.vehiclerent.vehicle.Car;
import com.vehiclerent.vehicle.Vehicle;

/**
 * Builder Pattern — Director &amp; Builder in one: constructs complex Vehicle objects step-by-step.
 * Why: Separates object construction from its representation, allowing the same construction
 * process to create different Vehicle types (Car / Bike) with optional features.
 */
public class VehicleBuilder {

    private String id;
    private String type;
    private String model;
    private double basePrice;
    private boolean hasSunroof;
    private boolean hasBluetooth;
    private boolean hasHelmet;
    private boolean hasCarrier;

    public VehicleBuilder id(String id) {
        this.id = id;
        return this;
    }

    public VehicleBuilder type(String type) {
        this.type = type;
        return this;
    }

    public VehicleBuilder model(String model) {
        this.model = model;
        return this;
    }

    public VehicleBuilder basePrice(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public VehicleBuilder withSunroof(boolean hasSunroof) {
        this.hasSunroof = hasSunroof;
        return this;
    }

    public VehicleBuilder withBluetooth(boolean hasBluetooth) {
        this.hasBluetooth = hasBluetooth;
        return this;
    }

    public VehicleBuilder withHelmet(boolean hasHelmet) {
        this.hasHelmet = hasHelmet;
        return this;
    }

    public VehicleBuilder withCarrier(boolean hasCarrier) {
        this.hasCarrier = hasCarrier;
        return this;
    }

    public Vehicle build() {
        ConsoleOutput.printHeading("BUILDER PATTERN - VEHICLE CONSTRUCTION");
        Vehicle vehicle;
        if ("Bike".equalsIgnoreCase(type)) {
            vehicle = new Bike(id, model, basePrice, hasHelmet, hasCarrier);
        } else {
            vehicle = new Car(id, model, basePrice, hasSunroof, hasBluetooth);
        }
        ConsoleOutput.printLine("Vehicle Built: " + vehicle.getDescription());
        ConsoleOutput.printLine("Base Price: $" + vehicle.getBasePrice());
        return vehicle;
    }
}
