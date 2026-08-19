package com.vehiclerent.repository;

import com.vehiclerent.vehicle.VehicleRecord;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    void save(VehicleRecord record);

    void update(VehicleRecord record);

    Optional<VehicleRecord> findById(String id);

    List<VehicleRecord> findAll();

    List<VehicleRecord> findByStatus(String status);

    void updateStatus(String vehicleId, String status);
}
