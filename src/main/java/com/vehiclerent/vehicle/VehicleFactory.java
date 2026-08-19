package com.vehiclerent.vehicle;

import com.vehiclerent.creational.VehicleBuilder;

/**
 * Rebuilds Vehicle objects from database records using Builder and Decorator patterns.
 */
public final class VehicleFactory {

    private VehicleFactory() {
    }

    public static Vehicle fromRecord(VehicleRecord record) {
        VehicleBuilder builder = new VehicleBuilder()
                .id(record.getId())
                .type(record.getType())
                .model(record.getModel())
                .basePrice(record.getBasePrice());

        if ("Bike".equalsIgnoreCase(record.getType())) {
            builder.withHelmet(record.isHasHelmet()).withCarrier(record.isHasCarrier());
        } else {
            builder.withSunroof(record.isHasSunroof()).withBluetooth(record.isHasBluetooth());
        }

        return builder.build();
    }

    public static VehicleRecord toRecord(Vehicle vehicle, VehicleRecord extras) {
        VehicleRecord record = extras != null ? extras : new VehicleRecord();
        record.setId(vehicle.getId());
        record.setType(vehicle.getType());
        record.setModel(vehicle.getModel());
        record.setBasePrice(vehicle.getBasePrice());
        if (record.getStatus() == null) {
            record.setStatus("Available");
        }
        return record;
    }
}
