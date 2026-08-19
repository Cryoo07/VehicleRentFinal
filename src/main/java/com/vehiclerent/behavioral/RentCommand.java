package com.vehiclerent.behavioral;

import com.vehiclerent.rental.Booking;
import com.vehiclerent.repository.BookingRepository;
import com.vehiclerent.util.ConsoleOutput;

/**
 * Command Pattern: Encapsulates the Rent operation as a command object.
 */
public class RentCommand implements BookingCommand {

    private final BookingRepository bookingRepository;
    private final VehicleStateContext vehicleState;
    private final Booking booking;
    private final int days;

    public RentCommand(BookingRepository bookingRepository, VehicleStateContext vehicleState, Booking booking, int days) {
        this.bookingRepository = bookingRepository;
        this.vehicleState = vehicleState;
        this.booking = booking;
        this.days = days;
    }

    @Override
    public void execute() {
        ConsoleOutput.printHeading("COMMAND PATTERN - RENT BOOKING");
        ConsoleOutput.printLine("Booking ID: " + booking.getBookingId());
        ConsoleOutput.printLine("Customer: " + booking.getCustomerId());
        ConsoleOutput.printLine("Vehicle: " + booking.getVehicleId());
        vehicleState.rent();
        bookingRepository.save(booking, days);
        ConsoleOutput.printLine("Rent command executed successfully.");
    }

    @Override
    public void undo() {
        ConsoleOutput.printLine("Undoing Rent Command for " + booking.getBookingId());
        vehicleState.returnVehicle();
        booking.setStatus(Booking.BookingStatus.CANCELLED);
    }

    @Override
    public String getDescription() { return "Rent " + booking.getVehicleId(); }
}
