package com.vehiclerent.vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Manages vehicle inventory (add, update, list cars/bikes).
 */
public class VehicleManager {

    private final List<Vehicle> vehicles = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void updateVehicle(String id, Vehicle updatedVehicle) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId().equals(id)) {
                vehicles.set(i, updatedVehicle);
                return;
            }
        }
        throw new IllegalArgumentException("Vehicle not found: " + id);
    }

    public List<Vehicle> listAll() {
        return Collections.unmodifiableList(vehicles);
    }

    public Optional<Vehicle> findById(String id) {
        return vehicles.stream().filter(v -> v.getId().equals(id)).findFirst();
    }
}
