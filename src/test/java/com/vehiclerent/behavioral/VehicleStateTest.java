package com.vehiclerent.behavioral;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleStateTest {

    @Test
    void initialStateIsAvailable() {
        VehicleStateContext context = new VehicleStateContext("V-STATE");
        assertEquals("Available", context.getStatusName());
    }

    @Test
    void rentTransitionsToBooked() {
        VehicleStateContext context = new VehicleStateContext("V-STATE");
        context.rent();
        assertEquals("Booked", context.getStatusName());
    }

    @Test
    void returnTransitionsToAvailable() {
        VehicleStateContext context = new VehicleStateContext("V-STATE");
        context.rent();
        context.returnVehicle();
        assertEquals("Available", context.getStatusName());
    }

    @Test
    void cannotRentWhenAlreadyBooked() {
        VehicleStateContext context = new VehicleStateContext("V-STATE");
        context.rent();
        context.rent();
        assertEquals("Booked", context.getStatusName());
    }

    @Test
    void maintenanceTransition() {
        VehicleStateContext context = new VehicleStateContext("V-STATE");
        context.sendToMaintenance();
        assertEquals("UnderMaintenance", context.getStatusName());
    }
}
