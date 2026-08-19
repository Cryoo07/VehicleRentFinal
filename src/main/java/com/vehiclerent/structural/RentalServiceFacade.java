package com.vehiclerent.structural;

import com.vehiclerent.behavioral.BookingInvoker;
import com.vehiclerent.behavioral.CancelCommand;
import com.vehiclerent.behavioral.LateFeeStrategy;
import com.vehiclerent.behavioral.PaymentContext;
import com.vehiclerent.behavioral.PaymentStrategy;
import com.vehiclerent.behavioral.RentCommand;
import com.vehiclerent.behavioral.ReturnCommand;
import com.vehiclerent.behavioral.VehicleStateContext;
import com.vehiclerent.behavioral.VehicleStatusSubject;
import com.vehiclerent.creational.NotificationCreator;
import com.vehiclerent.creational.SmsNotificationCreator;
import com.vehiclerent.rental.Booking;
import com.vehiclerent.repository.BookingRepository;
import com.vehiclerent.user.Customer;
import com.vehiclerent.util.ConsoleOutput;
import com.vehiclerent.vehicle.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Facade Pattern: Provides simplified interface to the complex rental subsystem (payment, booking, notifications, state, late fees).
 */
public class RentalServiceFacade {

    private final BookingRepository bookingRepository;
    private final BookingInvoker bookingInvoker;
    private final PaymentGateway paymentGateway;
    private final VehicleStatusSubject statusSubject;
    private final LateFeeStrategy lateFeeStrategy;
    private final Map<String, VehicleStateContext> vehicleStates = new HashMap<>();

    public RentalServiceFacade(BookingRepository bookingRepository,
                               PaymentGateway paymentGateway,
                               VehicleStatusSubject statusSubject,
                               LateFeeStrategy lateFeeStrategy) {
        this.bookingRepository = bookingRepository;
        this.bookingInvoker = new BookingInvoker();
        this.paymentGateway = paymentGateway;
        this.statusSubject = statusSubject;
        this.lateFeeStrategy = lateFeeStrategy;
    }

    /**
     * Strategy Pattern: Computes and persists the late-return fine using the
     * configured LateFeeStrategy, then returns the calculated fine amount.
     *
     * @param booking  the booking being returned late
     * @param daysLate number of days the return was delayed (0 or negative means on time)
     * @param dailyRate base daily price of the vehicle, used by some fee strategies
     * @return the fine applied (0 if not late)
     */
    public double applyLateFee(Booking booking, int daysLate, double dailyRate) {
        double fine = lateFeeStrategy.calculateFine(daysLate, dailyRate);
        if (fine > 0) {
            booking.setLateFee(fine);
            bookingRepository.update(booking);
            ConsoleOutput.printLine("Late fee applied: $" + String.format("%.2f", fine)
                    + " (" + lateFeeStrategy.getDescription() + ")");
        } else {
            ConsoleOutput.printLine("Return on time. No late fee.");
        }
        return fine;
    }

    public void registerVehicleState(String vehicleId) {
        vehicleStates.putIfAbsent(vehicleId, new VehicleStateContext(vehicleId));
    }

    public VehicleStateContext getVehicleState(String vehicleId) {
        return vehicleStates.computeIfAbsent(vehicleId, VehicleStateContext::new);
    }

    public Booking rentVehicle(Customer customer, Vehicle vehicle, PaymentStrategy paymentStrategy, int days) {
        ConsoleOutput.printHeading("FACADE PATTERN - RENTAL SERVICE");
        ConsoleOutput.printLine("Processing rental request for " + customer.getName() + "...");

        double totalAmount = vehicle.getTotalPrice() * days;
        PaymentContext paymentContext = new PaymentContext(paymentStrategy);
        paymentContext.executePayment(totalAmount);

        paymentGateway.processTransaction(customer.getId(), totalAmount);

        String bookingId = "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Booking booking = new Booking(bookingId, customer.getId(), vehicle.getId(), totalAmount, days);

        VehicleStateContext stateContext = getVehicleState(vehicle.getId());
        RentCommand rentCommand = new RentCommand(bookingRepository, stateContext, booking, days);
        bookingInvoker.executeCommand(rentCommand);

        statusSubject.setStatus(vehicle.getId(), stateContext.getStatusName());

        NotificationCreator notifier = new SmsNotificationCreator();
        notifier.notifyCustomer(customer.getPhone(), "Your booking " + bookingId + " is confirmed!");

        ConsoleOutput.printLine("Rental completed via Facade. Total: $" + totalAmount);
        return booking;
    }

    public void cancelBooking(Booking booking) {
        ConsoleOutput.printHeading("FACADE PATTERN - CANCEL RENTAL");
        VehicleStateContext stateContext = getVehicleState(booking.getVehicleId());
        CancelCommand cancelCommand = new CancelCommand(bookingRepository, stateContext, booking);
        bookingInvoker.executeCommand(cancelCommand);
        statusSubject.setStatus(booking.getVehicleId(), stateContext.getStatusName());
    }

    public void returnVehicle(Booking booking) {
        ConsoleOutput.printHeading("FACADE PATTERN - RETURN RENTAL");
        VehicleStateContext stateContext = getVehicleState(booking.getVehicleId());
        ReturnCommand returnCommand = new ReturnCommand(bookingRepository, stateContext, booking);
        bookingInvoker.executeCommand(returnCommand);
        statusSubject.setStatus(booking.getVehicleId(), stateContext.getStatusName());
    }
}
