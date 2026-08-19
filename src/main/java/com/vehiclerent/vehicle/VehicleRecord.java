package com.vehiclerent.vehicle;

/**
 * Data transfer object for persisting vehicle details in PostgreSQL.
 */
public class VehicleRecord {

    private String id;
    private String type;
    private String model;
    private double basePrice;
    private boolean hasSunroof;
    private boolean hasBluetooth;
    private boolean hasHelmet;
    private boolean hasCarrier;
    private boolean hasGps;
    private boolean hasInsurance;
    private boolean hasChildSeat;
    private String status;

    public VehicleRecord() {
        this.status = "Available";
    }

    public VehicleRecord(String id, String type, String model, double basePrice) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.basePrice = basePrice;
        this.status = "Available";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isHasSunroof() {
        return hasSunroof;
    }

    public void setHasSunroof(boolean hasSunroof) {
        this.hasSunroof = hasSunroof;
    }

    public boolean isHasBluetooth() {
        return hasBluetooth;
    }

    public void setHasBluetooth(boolean hasBluetooth) {
        this.hasBluetooth = hasBluetooth;
    }

    public boolean isHasHelmet() {
        return hasHelmet;
    }

    public void setHasHelmet(boolean hasHelmet) {
        this.hasHelmet = hasHelmet;
    }

    public boolean isHasCarrier() {
        return hasCarrier;
    }

    public void setHasCarrier(boolean hasCarrier) {
        this.hasCarrier = hasCarrier;
    }

    public boolean isHasGps() {
        return hasGps;
    }

    public void setHasGps(boolean hasGps) {
        this.hasGps = hasGps;
    }

    public boolean isHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public boolean isHasChildSeat() {
        return hasChildSeat;
    }

    public void setHasChildSeat(boolean hasChildSeat) {
        this.hasChildSeat = hasChildSeat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
