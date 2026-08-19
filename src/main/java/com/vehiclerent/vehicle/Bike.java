package com.vehiclerent.vehicle;

/**
 * Builder Pattern — Concrete Product for the Bike vehicle type.
 * Constructed by {@link VehicleBuilder} and optionally wrapped by decorators at runtime.
 */
public class Bike implements Vehicle {

    private final String id;
    private final String model;
    private final double basePrice;
    private final boolean hasHelmet;
    private final boolean hasCarrier;

    public Bike(String id, String model, double basePrice, boolean hasHelmet, boolean hasCarrier) {
        this.id = id;
        this.model = model;
        this.basePrice = basePrice;
        this.hasHelmet = hasHelmet;
        this.hasCarrier = hasCarrier;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return "Bike";
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
        StringBuilder sb = new StringBuilder(model + " Bike");
        if (hasHelmet) {
            sb.append(" [Helmet]");
        }
        if (hasCarrier) {
            sb.append(" [Carrier]");
        }
        return sb.toString();
    }

    @Override
    public double getTotalPrice() {
        return basePrice;
    }

    public boolean hasHelmet() {
        return hasHelmet;
    }

    public boolean hasCarrier() {
        return hasCarrier;
    }
}
