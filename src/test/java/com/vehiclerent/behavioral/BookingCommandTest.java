package com.vehiclerent.behavioral;

import com.vehiclerent.rental.Booking;
import com.vehiclerent.repository.InMemoryBookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingCommandTest {

    private InMemoryBookingRepository repository;
    private VehicleStateContext stateContext;
    private Booking booking;

    @BeforeEach
    void setUp() {
        repository = new InMemoryBookingRepository();
        stateContext = new VehicleStateContext("CMD-VEH");
        booking = new Booking("BK-001", "CUST-1", "CMD-VEH", 300.0, 2);
    }

    @Test
    void rentCommandCreatesBookingAndBooksVehicle() {
        RentCommand command = new RentCommand(repository, stateContext, booking, 2);
        command.execute();
        assertEquals("Booked", stateContext.getStatusName());
        assertTrue(repository.findById("BK-001").isPresent());
    }

    @Test
    void cancelCommandCancelsBooking() {
        new RentCommand(repository, stateContext, booking, 2).execute();
        CancelCommand cancel = new CancelCommand(repository, stateContext, booking);
        cancel.execute();
        assertEquals(Booking.BookingStatus.CANCELLED, booking.getStatus());
        assertEquals("Available", stateContext.getStatusName());
    }

    @Test
    void returnCommandCompletesBooking() {
        new RentCommand(repository, stateContext, booking, 2).execute();
        ReturnCommand returnCmd = new ReturnCommand(repository, stateContext, booking);
        returnCmd.execute();
        assertEquals(Booking.BookingStatus.COMPLETED, booking.getStatus());
        assertEquals("Available", stateContext.getStatusName());
    }

    @Test
    void invokerTracksCommandHistory() {
        BookingInvoker invoker = new BookingInvoker();
        invoker.executeCommand(new RentCommand(repository, stateContext, booking, 2));
        assertEquals(1, invoker.getHistory().size());
    }
}
