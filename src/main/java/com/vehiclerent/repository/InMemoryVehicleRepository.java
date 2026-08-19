package com.vehiclerent.repository;

import com.vehiclerent.vehicle.VehicleRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory vehicle storage for unit tests and offline mode.
 */
public class InMemoryVehicleRepository implements VehicleRepository {

    private final Map<String, VehicleRecord> vehicles = new HashMap<>();

    @Override
    public void save(VehicleRecord record) {
        vehicles.put(record.getId(), record);
    }

    @Override
    public void update(VehicleRecord record) {
        if (!vehicles.containsKey(record.getId())) {
            throw new IllegalArgumentException("Vehicle not found: " + record.getId());
        }
        vehicles.put(record.getId(), record);
    }

    @Override
    public Optional<VehicleRecord> findById(String id) {
        return Optional.ofNullable(vehicles.get(id));
    }

    @Override
    public List<VehicleRecord> findAll() {
        return new ArrayList<>(vehicles.values());
    }

    @Override
    public List<VehicleRecord> findByStatus(String status) {
        return vehicles.values().stream()
                .filter(v -> status.equals(v.getStatus()))
                .toList();
    }

    @Override
    public void updateStatus(String vehicleId, String status) {
        VehicleRecord record = vehicles.get(vehicleId);
        if (record == null) {
            throw new IllegalArgumentException("Vehicle not found: " + vehicleId);
        }
        record.setStatus(status);
    }
}
