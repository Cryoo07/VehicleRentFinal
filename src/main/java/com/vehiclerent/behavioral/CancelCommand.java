package com.vehiclerent.behavioral;

import com.vehiclerent.rental.Booking;
import com.vehiclerent.repository.BookingRepository;
import com.vehiclerent.util.ConsoleOutput;

/**
 * Command Pattern: Encapsulates the Cancel operation as a command object.
 * Why: Allows the booking workflow to treat cancel as a reversible, parameterized action.
 */
public class CancelCommand implements BookingCommand {

    private final BookingRepository bookingRepository;
    private final VehicleStateContext vehicleState;
    private final Booking booking;

    public CancelCommand(BookingRepository bookingRepository, VehicleStateContext vehicleState, Booking booking) {
        this.bookingRepository = bookingRepository;
        this.vehicleState = vehicleState;
        this.booking = booking;
    }

    @Override
    public void execute() {
        ConsoleOutput.printHeading("COMMAND PATTERN - CANCEL BOOKING");
        ConsoleOutput.printLine("Executing Cancel Command...");
        ConsoleOutput.printLine("Booking ID: " + booking.getBookingId());
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        vehicleState.returnVehicle();
        bookingRepository.update(booking);
        ConsoleOutput.printLine("Cancel command executed successfully.");
    }

    @Override
    public void undo() {
        ConsoleOutput.printLine("Undoing Cancel Command for " + booking.getBookingId());
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        vehicleState.rent();
    }

    @Override
    public String getDescription() {
        return "Cancel " + booking.getBookingId();
    }
}
