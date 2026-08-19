package com.vehiclerent.repository;

import com.vehiclerent.creational.DatabaseConnection;
import com.vehiclerent.rental.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PostgreSQL JDBC implementation for booking persistence.
 */
public class JdbcBookingRepository implements BookingRepository {

    private static final String INSERT = """
            INSERT INTO bookings (booking_id, customer_id, vehicle_id, total_amount, days, late_fee, status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String UPDATE = """
            UPDATE bookings SET status = ?, late_fee = ? WHERE booking_id = ?
            """;
    private static final String FIND_BY_ID = """
            SELECT booking_id, customer_id, vehicle_id, total_amount, days, late_fee, status FROM bookings WHERE booking_id = ?
            """;
    private static final String FIND_ALL = """
            SELECT booking_id, customer_id, vehicle_id, total_amount, days, late_fee, status FROM bookings ORDER BY created_at DESC
            """;
    private static final String FIND_BY_CUSTOMER = """
            SELECT booking_id, customer_id, vehicle_id, total_amount, days, late_fee, status FROM bookings WHERE customer_id = ?
            ORDER BY created_at DESC
            """;

    @Override
    public void save(Booking booking, int days) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, booking.getBookingId());
            statement.setString(2, booking.getCustomerId());
            statement.setString(3, booking.getVehicleId());
            statement.setDouble(4, booking.getTotalAmount());
            statement.setInt(5, days);
            statement.setDouble(6, booking.getLateFee());
            statement.setString(7, booking.getStatus().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save booking: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Booking booking) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, booking.getStatus().name());
            statement.setDouble(2, booking.getLateFee());
            statement.setString(3, booking.getBookingId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update booking: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Booking> findById(String bookingId) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setString(1, bookingId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapBooking(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find booking: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Booking> findAll() {
        return queryList(FIND_ALL, null);
    }

    @Override
    public List<Booking> findByCustomerId(String customerId) {
        return queryList(FIND_BY_CUSTOMER, customerId);
    }

    private List<Booking> queryList(String sql, String param) {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (param != null) {
                statement.setString(1, param);
            }
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapBooking(rs));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to query bookings: " + e.getMessage(), e);
        }
        return bookings;
    }

    private Booking mapBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking(
                rs.getString("booking_id"),
                rs.getString("customer_id"),
                rs.getString("vehicle_id"),
                rs.getDouble("total_amount"),
                rs.getInt("days"));
        booking.setStatus(Booking.BookingStatus.valueOf(rs.getString("status")));
        booking.setLateFee(rs.getDouble("late_fee"));
        return booking;
    }
}
