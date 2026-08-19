package com.vehiclerent.structural;

import com.vehiclerent.behavioral.StandardLateFee;
import com.vehiclerent.behavioral.VehicleStatusSubject;
import com.vehiclerent.behavioral.CreditCardPayment;
import com.vehiclerent.creational.VehicleBuilder;
import com.vehiclerent.rental.Booking;
import com.vehiclerent.repository.InMemoryBookingRepository;
import com.vehiclerent.repository.InMemoryCustomerRepository;
import com.vehiclerent.repository.InMemoryUserRepository;
import com.vehiclerent.repository.BookingRepository;
import com.vehiclerent.user.Customer;
import com.vehiclerent.user.UserService;
import com.vehiclerent.vehicle.Vehicle;
import com.vehiclerent.creational.VehicleBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RentalWorkflowTest {

    private RentalServiceFacade facade;
    private Customer customer;

    @BeforeEach
    void setUp() {
        BookingRepository repo = new InMemoryBookingRepository();
        VehicleStatusSubject subject = new VehicleStatusSubject();
        facade = new RentalServiceFacade(repo, new PaymentGatewayAdapter(new ThirdPartyPaymentApi()),
                subject, new StandardLateFee());

        UserService userService = new UserService(new InMemoryUserRepository(), new InMemoryCustomerRepository());
        customer = new Customer("WF-001", "Workflow User", "wf@test.com", "+111");
        userService.registerCustomer(customer, "wflower", "pass");
        userService.login("wflower", "pass");
    }

    @Test
    void fullRentalWorkflow() {
        Vehicle vehicle = new VehicleBuilder()
                .id("WF-CAR")
                .type("Car")
                .model("Workflow Car")
                .basePrice(60.0)
                .build();

        facade.registerVehicleState(vehicle.getId());
        Booking booking = facade.rentVehicle(customer, vehicle, new CreditCardPayment("4111111111111111"), 2);

        assertNotNull(booking);
        assertEquals(Booking.BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals(120.0, booking.getTotalAmount());
        assertEquals("Booked", facade.getVehicleState(vehicle.getId()).getStatusName());

        facade.returnVehicle(booking);
        assertEquals(Booking.BookingStatus.COMPLETED, booking.getStatus());
        assertEquals("Available", facade.getVehicleState(vehicle.getId()).getStatusName());
    }

    @Test
    void cancelBookingWorkflow() {
        Vehicle vehicle = new VehicleBuilder()
                .id("WF-CAR2")
                .type("Car")
                .model("Cancel Car")
                .basePrice(50.0)
                .build();

        facade.registerVehicleState(vehicle.getId());
        Booking booking = facade.rentVehicle(customer, vehicle, new CreditCardPayment("4111111111111111"), 1);
        facade.cancelBooking(booking);

        assertEquals(Booking.BookingStatus.CANCELLED, booking.getStatus());
        assertEquals("Available", facade.getVehicleState(vehicle.getId()).getStatusName());
    }

    @Test
    void lateReturnAppliesStandardFine() {
        Vehicle vehicle = new VehicleBuilder()
                .id("WF-LATE")
                .type("Car")
                .model("Late Car")
                .basePrice(100.0)
                .build();

        facade.registerVehicleState(vehicle.getId());
        Booking booking = facade.rentVehicle(customer, vehicle, new CreditCardPayment("4111111111111111"), 2);

        double fine = facade.applyLateFee(booking, 2, vehicle.getBasePrice());
        assertEquals(100.0, fine); // 50% of $100/day x 2 days late
        assertEquals(100.0, booking.getLateFee());

        facade.returnVehicle(booking);
        assertEquals(Booking.BookingStatus.COMPLETED, booking.getStatus());
        assertEquals("Available", facade.getVehicleState(vehicle.getId()).getStatusName());
    }

    @Test
    void onTimeReturnHasNoFine() {
        Vehicle vehicle = new VehicleBuilder()
                .id("WF-ONTIME")
                .type("Car")
                .model("On Time Car")
                .basePrice(80.0)
                .build();

        facade.registerVehicleState(vehicle.getId());
        Booking booking = facade.rentVehicle(customer, vehicle, new CreditCardPayment("4111111111111111"), 2);

        double fine = facade.applyLateFee(booking, 0, vehicle.getBasePrice());
        assertEquals(0.0, fine);
        assertEquals(0.0, booking.getLateFee());
    }
}
