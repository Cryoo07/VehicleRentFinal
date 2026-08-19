package com.vehiclerent.vehicle;

/**
 * Builder Pattern — Product interface for all rentable vehicles.
 * Defines the contract that concrete products (Car, Bike) and decorators implement.
 */
public interface Vehicle {

    String getId();

    String getType();

    String getModel();

    double getBasePrice();

    String getDescription();

    double getTotalPrice();
}
