package com.vehiclerent.vehicle;

import com.vehiclerent.creational.VehicleBuilder;
import com.vehiclerent.structural.ChildSeatDecorator;
import com.vehiclerent.structural.GpsDecorator;
import com.vehiclerent.structural.InsuranceDecorator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleBuilderDecoratorTest {

    @Test
    void builderCreatesCarWithFeatures() {
        Vehicle car = new VehicleBuilder()
                .id("CAR-TEST")
                .type("Car")
                .model("Test Car")
                .basePrice(100.0)
                .withSunroof(true)
                .withBluetooth(true)
                .build();

        assertEquals("Car", car.getType());
        assertEquals("Test Car", car.getModel());
        assertTrue(car.getDescription().contains("Sunroof"));
        assertTrue(car.getDescription().contains("Bluetooth"));
    }

    @Test
    void builderCreatesBikeWithFeatures() {
        Vehicle bike = new VehicleBuilder()
                .id("BIKE-TEST")
                .type("Bike")
                .model("Test Bike")
                .basePrice(30.0)
                .withHelmet(true)
                .build();

        assertEquals("Bike", bike.getType());
        assertTrue(bike.getDescription().contains("Helmet"));
    }

    @Test
    void decoratorsAddPriceAndDescription() {
        Vehicle base = new VehicleBuilder()
                .id("V1")
                .type("Car")
                .model("Base")
                .basePrice(50.0)
                .build();

        Vehicle decorated = new GpsDecorator(new InsuranceDecorator(new ChildSeatDecorator(base)));
        assertEquals(100.0, decorated.getTotalPrice());
        assertTrue(decorated.getDescription().contains("GPS"));
        assertTrue(decorated.getDescription().contains("Insurance"));
        assertTrue(decorated.getDescription().contains("Child Seat"));
    }

    @Test
    void vehicleManagerAddsAndFindsVehicles() {
        VehicleManager manager = new VehicleManager();
        Vehicle v = new VehicleBuilder().id("V2").type("Car").model("M").basePrice(40).build();
        manager.addVehicle(v);
        assertTrue(manager.findById("V2").isPresent());
        assertEquals(1, manager.listAll().size());
    }
}
