package com.vehiclerent.behavioral;

import com.vehiclerent.rental.Booking;
import com.vehiclerent.repository.BookingRepository;
import com.vehiclerent.util.ConsoleOutput;

/**
 * Command Pattern: Encapsulates the Return operation as a command object.
 * Why: Enables uniform handling of return alongside other booking commands via the invoker.
 */
public class ReturnCommand implements BookingCommand {

    private final BookingRepository bookingRepository;
    private final VehicleStateContext vehicleState;
    private final Booking booking;

    public ReturnCommand(BookingRepository bookingRepository, VehicleStateContext vehicleState, Booking booking) {
        this.bookingRepository = bookingRepository;
        this.vehicleState = vehicleState;
        this.booking = booking;
    }

    @Override
    public void execute() {
        ConsoleOutput.printHeading("COMMAND PATTERN - RETURN VEHICLE");
        ConsoleOutput.printLine("Executing Return Command...");
        ConsoleOutput.printLine("Booking ID: " + booking.getBookingId());
        booking.setStatus(Booking.BookingStatus.COMPLETED);
        vehicleState.returnVehicle();
        bookingRepository.update(booking);
        ConsoleOutput.printLine("Return command executed successfully.");
    }

    @Override
    public void undo() {
        ConsoleOutput.printLine("Undoing Return Command for " + booking.getBookingId());
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        vehicleState.rent();
    }

    @Override
    public String getDescription() {
        return "Return " + booking.getVehicleId();
    }
}
