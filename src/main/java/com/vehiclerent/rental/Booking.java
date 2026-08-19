package com.vehiclerent.rental;

/**
 * Represents a rental booking/reservation record.
 */
public class Booking {

    public enum BookingStatus {
        CONFIRMED, CANCELLED, COMPLETED
    }

    private final String bookingId;
    private final String customerId;
    private final String vehicleId;
    private final double totalAmount;
    private final int days;
    private BookingStatus status;
    private double lateFee;

    public Booking(String bookingId, String customerId, String vehicleId, double totalAmount, int days) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.totalAmount = totalAmount;
        this.days = days;
        this.status = BookingStatus.CONFIRMED;
        this.lateFee = 0;
    }

    public String getBookingId() { return bookingId; }
    public String getCustomerId() { return customerId; }
    public String getVehicleId() { return vehicleId; }
    public double getTotalAmount() { return totalAmount; }
    public int getDays() { return days; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public double getLateFee() { return lateFee; }
    public void setLateFee(double lateFee) { this.lateFee = lateFee; }
}
