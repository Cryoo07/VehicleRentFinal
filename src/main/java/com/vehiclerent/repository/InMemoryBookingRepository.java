package com.vehiclerent.repository;

import com.vehiclerent.rental.Booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory booking storage for unit tests and offline mode.
 */
public class InMemoryBookingRepository implements BookingRepository {

    private final Map<String, Booking> bookings = new HashMap<>();
    private final Map<String, Integer> bookingDays = new HashMap<>();

    @Override
    public void save(Booking booking, int days) {
        bookings.put(booking.getBookingId(), booking);
        bookingDays.put(booking.getBookingId(), days);
    }

    @Override
    public void update(Booking booking) {
        if (!bookings.containsKey(booking.getBookingId())) {
            throw new IllegalArgumentException("Booking not found: " + booking.getBookingId());
        }
        bookings.put(booking.getBookingId(), booking);
    }

    @Override
    public Optional<Booking> findById(String bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }

    @Override
    public List<Booking> findAll() {
        return List.copyOf(bookings.values());
    }

    @Override
    public List<Booking> findByCustomerId(String customerId) {
        return bookings.values().stream()
                .filter(b -> b.getCustomerId().equals(customerId))
                .toList();
    }

    public int getDays(String bookingId) {
        return bookingDays.getOrDefault(bookingId, 1);
    }
}
