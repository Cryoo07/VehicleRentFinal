package com.vehiclerent.repository;

import com.vehiclerent.rental.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {

    void save(Booking booking, int days);

    void update(Booking booking);

    Optional<Booking> findById(String bookingId);

    List<Booking> findAll();

    List<Booking> findByCustomerId(String customerId);
}
