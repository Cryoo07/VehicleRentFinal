package com.vehiclerent.vehicle;

/**
 * Builder Pattern — Concrete Product for the Car vehicle type.
 * Constructed by {@link VehicleBuilder} and optionally wrapped by decorators at runtime.
 */
public class Car implements Vehicle {

    private final String id;
    private final String model;
    private final double basePrice;
    private final boolean hasSunroof;
    private final boolean hasBluetooth;

    public Car(String id, String model, double basePrice, boolean hasSunroof, boolean hasBluetooth) {
        this.id = id;
        this.model = model;
        this.basePrice = basePrice;
        this.hasSunroof = hasSunroof;
        this.hasBluetooth = hasBluetooth;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return "Car";
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder(model + " Car");
        if (hasSunroof) {
            sb.append(" [Sunroof]");
        }
        if (hasBluetooth) {
            sb.append(" [Bluetooth]");
        }
        return sb.toString();
    }

    @Override
    public double getTotalPrice() {
        return basePrice;
    }

    public boolean hasSunroof() {
        return hasSunroof;
    }

    public boolean hasBluetooth() {
        return hasBluetooth;
    }
}
