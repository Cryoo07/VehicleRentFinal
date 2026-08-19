package com.vehiclerent.structural;

import com.vehiclerent.rental.Booking;
import com.vehiclerent.repository.BookingRepository;

/**
 * Real report service implementation (used behind Proxy).
 */
public class ReportServiceImpl implements ReportService {

    private final BookingRepository bookingRepository;

    public ReportServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public String generateRentalHistoryReport() {
        StringBuilder sb = new StringBuilder("=== RENTAL HISTORY REPORT ===\n");
        for (Booking booking : bookingRepository.findAll()) {
            sb.append(String.format("Booking: %s | Customer: %s | Vehicle: %s | Status: %s | Amount: $%.2f%n",
                    booking.getBookingId(), booking.getCustomerId(), booking.getVehicleId(),
                    booking.getStatus(), booking.getTotalAmount()));
        }
        if (bookingRepository.findAll().isEmpty()) {
            sb.append("No rental records found.\n");
        }
        return sb.toString();
    }

    @Override
    public String generateRevenueReport() {
        double totalRevenue = bookingRepository.findAll().stream()
                .filter(b -> b.getStatus() != Booking.BookingStatus.CANCELLED)
                .mapToDouble(Booking::getTotalAmount)
                .sum();
        return String.format("=== REVENUE REPORT ===%nTotal Revenue: $%.2f%nBookings: %d%n",
                totalRevenue, bookingRepository.findAll().size());
    }
}
