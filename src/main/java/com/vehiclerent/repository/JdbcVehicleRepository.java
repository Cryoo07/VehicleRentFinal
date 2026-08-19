package com.vehiclerent.repository;

import com.vehiclerent.creational.DatabaseConnection;
import com.vehiclerent.rental.Booking;
import com.vehiclerent.vehicle.VehicleRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PostgreSQL JDBC implementation for vehicle persistence.
 */
public class JdbcVehicleRepository implements VehicleRepository {

    private static final String INSERT = """
            INSERT INTO vehicles (id, type, model, base_price, has_sunroof, has_bluetooth,
            has_helmet, has_carrier, has_gps, has_insurance, has_child_seat, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String UPDATE = """
            UPDATE vehicles SET type=?, model=?, base_price=?, has_sunroof=?, has_bluetooth=?,
            has_helmet=?, has_carrier=?, has_gps=?, has_insurance=?, has_child_seat=?, status=?
            WHERE id=?
            """;
    private static final String FIND_BY_ID = "SELECT * FROM vehicles WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM vehicles ORDER BY id";
    private static final String FIND_BY_STATUS = "SELECT * FROM vehicles WHERE status = ? ORDER BY id";
    private static final String UPDATE_STATUS = "UPDATE vehicles SET status = ? WHERE id = ?";

    @Override
    public void save(VehicleRecord record) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            bindVehicle(statement, record);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save vehicle: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(VehicleRecord record) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, record.getType());
            statement.setString(2, record.getModel());
            statement.setDouble(3, record.getBasePrice());
            statement.setBoolean(4, record.isHasSunroof());
            statement.setBoolean(5, record.isHasBluetooth());
            statement.setBoolean(6, record.isHasHelmet());
            statement.setBoolean(7, record.isHasCarrier());
            statement.setBoolean(8, record.isHasGps());
            statement.setBoolean(9, record.isHasInsurance());
            statement.setBoolean(10, record.isHasChildSeat());
            statement.setString(11, record.getStatus());
            statement.setString(12, record.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update vehicle: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<VehicleRecord> findById(String id) {
        return querySingle(FIND_BY_ID, id);
    }

    @Override
    public List<VehicleRecord> findAll() {
        return queryList(FIND_ALL, null);
    }

    @Override
    public List<VehicleRecord> findByStatus(String status) {
        return queryList(FIND_BY_STATUS, status);
    }

    @Override
    public void updateStatus(String vehicleId, String status) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS)) {
            statement.setString(1, status);
            statement.setString(2, vehicleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update vehicle status: " + e.getMessage(), e);
        }
    }

    private void bindVehicle(PreparedStatement statement, VehicleRecord record) throws SQLException {
        statement.setString(1, record.getId());
        statement.setString(2, record.getType());
        statement.setString(3, record.getModel());
        statement.setDouble(4, record.getBasePrice());
        statement.setBoolean(5, record.isHasSunroof());
        statement.setBoolean(6, record.isHasBluetooth());
        statement.setBoolean(7, record.isHasHelmet());
        statement.setBoolean(8, record.isHasCarrier());
        statement.setBoolean(9, record.isHasGps());
        statement.setBoolean(10, record.isHasInsurance());
        statement.setBoolean(11, record.isHasChildSeat());
        statement.setString(12, record.getStatus());
    }

    private Optional<VehicleRecord> querySingle(String sql, String param) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, param);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapVehicle(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to query vehicle: " + e.getMessage(), e);
        }
    }

    private List<VehicleRecord> queryList(String sql, String param) {
        List<VehicleRecord> records = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (param != null) {
                statement.setString(1, param);
            }
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    records.add(mapVehicle(rs));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to query vehicles: " + e.getMessage(), e);
        }
        return records;
    }

    private VehicleRecord mapVehicle(ResultSet rs) throws SQLException {
        VehicleRecord record = new VehicleRecord();
        record.setId(rs.getString("id"));
        record.setType(rs.getString("type"));
        record.setModel(rs.getString("model"));
        record.setBasePrice(rs.getDouble("base_price"));
        record.setHasSunroof(rs.getBoolean("has_sunroof"));
        record.setHasBluetooth(rs.getBoolean("has_bluetooth"));
        record.setHasHelmet(rs.getBoolean("has_helmet"));
        record.setHasCarrier(rs.getBoolean("has_carrier"));
        record.setHasGps(rs.getBoolean("has_gps"));
        record.setHasInsurance(rs.getBoolean("has_insurance"));
        record.setHasChildSeat(rs.getBoolean("has_child_seat"));
        record.setStatus(rs.getString("status"));
        return record;
    }
}
