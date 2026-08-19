package com.vehiclerent;

import com.vehiclerent.behavioral.CreditCardPayment;
import com.vehiclerent.behavioral.FlatLateFee;
import com.vehiclerent.behavioral.LateFeeStrategy;
import com.vehiclerent.behavioral.MobileWalletPayment;
import com.vehiclerent.behavioral.PayPalPayment;
import com.vehiclerent.behavioral.PaymentStrategy;
import com.vehiclerent.behavioral.StandardLateFee;
import com.vehiclerent.behavioral.CustomerNotifier;
import com.vehiclerent.behavioral.VehicleStatusSubject;
import com.vehiclerent.creational.SystemConfig;
import com.vehiclerent.database.SchemaInitializer;
import com.vehiclerent.rental.Booking;
import com.vehiclerent.repository.*;
import com.vehiclerent.security.SecurityContext;
import com.vehiclerent.structural.ChildSeatDecorator;
import com.vehiclerent.structural.GpsDecorator;
import com.vehiclerent.structural.InsuranceDecorator;
import com.vehiclerent.structural.PaymentGatewayAdapter;
import com.vehiclerent.structural.RentalServiceFacade;
import com.vehiclerent.structural.ReportService;
import com.vehiclerent.structural.ReportServiceImpl;
import com.vehiclerent.structural.ReportServiceProxy;
import com.vehiclerent.structural.ThirdPartyPaymentApi;
import com.vehiclerent.user.Customer;
import com.vehiclerent.user.User;
import com.vehiclerent.user.UserService;
import com.vehiclerent.util.ConsoleOutput;
import com.vehiclerent.vehicle.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Function;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static UserService userService;
    private static VehicleRepository vehicleRepository;
    private static BookingRepository bookingRepository;
    private static RentalServiceFacade rentalFacade;
    private static VehicleStatusSubject statusSubject;
    private static ReportService reportService;

    public static void main(String[] args) {
        ConsoleOutput.printHeading("VEHICLE RENTAL SYSTEM");
        try {
            initializeSystem();
        } catch (Exception e) {
            ConsoleOutput.printLine("FATAL: " + e.getMessage());
            ConsoleOutput.printLine("Make sure PostgreSQL is running and database.properties is configured.");
            System.exit(1);
        }

        while (true) {
            try {
                User current = SecurityContext.getCurrentUser();
                if (current == null) {
                    showMainMenu();
                } else if (current.isAdmin()) {
                    showAdminMenu();
                } else {
                    showCustomerMenu();
                }
            } catch (Exception e) {
                ConsoleOutput.printLine("Error: " + e.getMessage());
                pause();
            }
        }
    }

    private static void initializeSystem() {
        SystemConfig.getInstance().displayConfiguration();
        SchemaInitializer.initialize();

        UserRepository userRepo = new JdbcUserRepository();
        CustomerRepository customerRepo = new JdbcCustomerRepository(userRepo);
        vehicleRepository = new JdbcVehicleRepository();
        bookingRepository = new JdbcBookingRepository();

        userService = new UserService(userRepo, customerRepo);
        statusSubject = new VehicleStatusSubject();
        rentalFacade = new RentalServiceFacade(bookingRepository,
                new PaymentGatewayAdapter(new ThirdPartyPaymentApi()), statusSubject,
                new StandardLateFee());
        reportService = new ReportServiceProxy(new ReportServiceImpl(bookingRepository));

        ConsoleOutput.printLine("System initialized successfully.");
    }

    private static <T> T pickFromList(String prompt, List<T> items, Function<T, String> formatter) {
        if (items.isEmpty()) return null;
        for (int i = 0; i < items.size(); i++) {
            ConsoleOutput.printLine((i + 1) + ". " + formatter.apply(items.get(i)));
        }
        while (true) {
            try {
                System.out.print(prompt);
                int choice = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (choice >= 0 && choice < items.size()) return items.get(choice);
                ConsoleOutput.printLine("Enter a number between 1 and " + items.size() + ".");
            } catch (NumberFormatException e) {
                ConsoleOutput.printLine("Invalid input. Enter a number.");
            }
        }
    }

    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static void showMainMenu() {
        ConsoleOutput.printHeading("MAIN MENU");
        ConsoleOutput.printLine("1. Login");
        ConsoleOutput.printLine("2. Register");
        ConsoleOutput.printLine("3. Exit");
        ConsoleOutput.printSeparator();
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1" -> login();
            case "2" -> register();
            case "3" -> {
                ConsoleOutput.printLine("Goodbye!");
                System.exit(0);
            }
            default -> ConsoleOutput.printLine("Invalid choice.");
        }
    }

    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        if (userService.login(username, password)) {
            pause();
        } else {
            ConsoleOutput.printLine("Login failed. Check credentials.");
        }
    }

    private static void register() {
        ConsoleOutput.printHeading("CUSTOMER REGISTRATION");
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) { ConsoleOutput.printLine("Name is required."); return; }
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) { ConsoleOutput.printLine("Email is required."); return; }
            System.out.print("Phone: ");
            String phone = scanner.nextLine().trim();
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            if (username.isEmpty()) { ConsoleOutput.printLine("Username is required."); return; }
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();
            if (password.isEmpty()) { ConsoleOutput.printLine("Password is required."); return; }

            String id = UUID.randomUUID().toString();
            Customer customer = new Customer(id, name, email, phone);
            userService.registerCustomer(customer, username, password);
        } catch (IllegalArgumentException e) {
            ConsoleOutput.printLine("Registration failed: " + e.getMessage());
        }
    }

    private static String userLabel() {
        User u = SecurityContext.getCurrentUser();
        if (u == null) return "";
        return " (" + (u.isAdmin() ? "Admin: " : "") + u.getUsername() + ")";
    }

    private static void showAdminMenu() {
        ConsoleOutput.printHeading("ADMIN MENU" + userLabel());
        ConsoleOutput.printLine("1. List Vehicles");
        ConsoleOutput.printLine("2. Add Vehicle");
        ConsoleOutput.printLine("3. Update Vehicle Status");
        ConsoleOutput.printLine("4. List Customers");
        ConsoleOutput.printLine("5. View All Bookings");
        ConsoleOutput.printLine("6. Revenue Report");
        ConsoleOutput.printLine("7. Rental History Report");
        ConsoleOutput.printLine("8. Logout");
        ConsoleOutput.printSeparator();
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1" -> listVehicles();
            case "2" -> addVehicle();
            case "3" -> updateVehicleStatus();
            case "4" -> listCustomers();
            case "5" -> listAllBookings();
            case "6" -> { ConsoleOutput.printLine(reportService.generateRevenueReport()); pause(); }
            case "7" -> { ConsoleOutput.printLine(reportService.generateRentalHistoryReport()); pause(); }
            case "8" -> userService.logout();
            default -> ConsoleOutput.printLine("Invalid choice.");
        }
    }

    private static void showCustomerMenu() {
        ConsoleOutput.printHeading("CUSTOMER MENU" + userLabel());
        ConsoleOutput.printLine("1. List Available Vehicles");
        ConsoleOutput.printLine("2. Rent a Vehicle");
        ConsoleOutput.printLine("3. Return a Vehicle");
        ConsoleOutput.printLine("4. Cancel Booking");
        ConsoleOutput.printLine("5. My Bookings");
        ConsoleOutput.printLine("6. Logout");
        ConsoleOutput.printSeparator();
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1" -> listAvailableVehicles();
            case "2" -> rentVehicle();
            case "3" -> returnVehicle();
            case "4" -> cancelBooking();
            case "5" -> myBookings();
            case "6" -> userService.logout();
            default -> ConsoleOutput.printLine("Invalid choice.");
        }
    }

    private static void listVehicles() {
        ConsoleOutput.printHeading("ALL VEHICLES");
        List<VehicleRecord> records = vehicleRepository.findAll();
        if (records.isEmpty()) {
            ConsoleOutput.printLine("No vehicles found.");
            pause();
            return;
        }
        for (int i = 0; i < records.size(); i++) {
            VehicleRecord r = records.get(i);
            ConsoleOutput.printLine(String.format("%d. %s | %s | %s | $%.2f/day | %s",
                    i + 1, r.getId(), r.getType(), r.getModel(), r.getBasePrice(), r.getStatus()));
        }
        pause();
    }

    private static void addVehicle() {
        ConsoleOutput.printHeading("ADD VEHICLE");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) { ConsoleOutput.printLine("ID is required."); return; }

        if (vehicleRepository.findById(id).isPresent()) {
            ConsoleOutput.printLine("Vehicle ID already exists: " + id);
            return;
        }

        System.out.print("Type (Car/Bike): ");
        String type = scanner.nextLine().trim();
        if (!type.equalsIgnoreCase("Car") && !type.equalsIgnoreCase("Bike")) {
            ConsoleOutput.printLine("Type must be Car or Bike.");
            return;
        }
        System.out.print("Model: ");
        String model = scanner.nextLine().trim();
        if (model.isEmpty()) { ConsoleOutput.printLine("Model is required."); return; }

        double price;
        try {
            System.out.print("Base Price: ");
            price = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            ConsoleOutput.printLine("Invalid price.");
            return;
        }

        VehicleRecord record = new VehicleRecord(id, type, model, price);

        if ("Car".equalsIgnoreCase(type)) {
            System.out.print("Has Sunroof? (yes/no): ");
            record.setHasSunroof(scanner.nextLine().trim().equalsIgnoreCase("yes"));
            System.out.print("Has Bluetooth? (yes/no): ");
            record.setHasBluetooth(scanner.nextLine().trim().equalsIgnoreCase("yes"));
        } else {
            System.out.print("Has Helmet? (yes/no): ");
            record.setHasHelmet(scanner.nextLine().trim().equalsIgnoreCase("yes"));
            System.out.print("Has Carrier? (yes/no): ");
            record.setHasCarrier(scanner.nextLine().trim().equalsIgnoreCase("yes"));
        }

        vehicleRepository.save(record);
        ConsoleOutput.printLine("Vehicle added successfully.");
    }

    private static void updateVehicleStatus() {
        ConsoleOutput.printHeading("UPDATE VEHICLE STATUS");
        List<VehicleRecord> records = vehicleRepository.findAll();
        if (records.isEmpty()) {
            ConsoleOutput.printLine("No vehicles found.");
            return;
        }
        VehicleRecord record = pickFromList("Enter number: ", records,
                r -> String.format("%s | %s | $%.2f/day | %s", r.getId(), r.getModel(), r.getBasePrice(), r.getStatus()));
        if (record == null) return;

        ConsoleOutput.printLine("Current status: " + record.getStatus());
        System.out.print("New Status (Available/Booked/UnderMaintenance): ");
        String status = scanner.nextLine().trim();
        if (!status.equals("Available") && !status.equals("Booked") && !status.equals("UnderMaintenance")) {
            ConsoleOutput.printLine("Invalid status.");
            return;
        }
        vehicleRepository.updateStatus(record.getId(), status);
        ConsoleOutput.printLine("Status updated.");
    }

    private static void listCustomers() {
        ConsoleOutput.printHeading("CUSTOMERS");
        List<Customer> customers = userService.listCustomers();
        if (customers.isEmpty()) {
            ConsoleOutput.printLine("No customers registered.");
            pause();
            return;
        }
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            ConsoleOutput.printLine(String.format("%d. %s | %s | %s", i + 1, c.getName(), c.getEmail(), c.getPhone()));
        }
        pause();
    }

    private static void listAllBookings() {
        ConsoleOutput.printHeading("ALL BOOKINGS");
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) {
            ConsoleOutput.printLine("No bookings found.");
            pause();
            return;
        }
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            ConsoleOutput.printLine(String.format("%d. %s | Customer: %s | Vehicle: %s | $%.2f | %d days | %s%s",
                    i + 1, b.getBookingId(), b.getCustomerId(), b.getVehicleId(),
                    b.getTotalAmount(), b.getDays(), b.getStatus(), feeLabel(b)));
        }
        pause();
    }

    private static void listAvailableVehicles() {
        ConsoleOutput.printHeading("AVAILABLE VEHICLES");
        List<VehicleRecord> records = vehicleRepository.findByStatus("Available");
        if (records.isEmpty()) {
            ConsoleOutput.printLine("No vehicles available.");
            pause();
            return;
        }
        for (int i = 0; i < records.size(); i++) {
            VehicleRecord r = records.get(i);
            ConsoleOutput.printLine(String.format("%d. %s | %s | $%.2f/day",
                    i + 1, r.getModel(), r.getType(), r.getBasePrice()));
        }
        pause();
    }

    private static void rentVehicle() {
        User currentUser = SecurityContext.getCurrentUser();
        Optional<Customer> customerOpt = userService.findCustomerById(currentUser.getId());
        if (customerOpt.isEmpty()) {
            ConsoleOutput.printLine("Customer profile not found.");
            return;
        }
        Customer customer = customerOpt.get();

        List<VehicleRecord> available = vehicleRepository.findByStatus("Available");
        if (available.isEmpty()) {
            ConsoleOutput.printLine("No vehicles available.");
            pause();
            return;
        }

        ConsoleOutput.printHeading("SELECT VEHICLE");
        VehicleRecord record = pickFromList("Enter number: ", available,
                r -> String.format("%s | %s | $%.2f/day", r.getModel(), r.getType(), r.getBasePrice()));
        if (record == null) return;

        String vehicleId = record.getId();
        Vehicle vehicle = VehicleFactory.fromRecord(record);

        double addonCost = 0;
        System.out.print("Add GPS? ($15/day) (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            vehicle = new GpsDecorator(vehicle);
            record.setHasGps(true);
            addonCost += 15;
        }
        System.out.print("Add Insurance? ($25/day) (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            vehicle = new InsuranceDecorator(vehicle);
            record.setHasInsurance(true);
            addonCost += 25;
        }
        System.out.print("Add Child Seat? ($10/day) (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            vehicle = new ChildSeatDecorator(vehicle);
            record.setHasChildSeat(true);
            addonCost += 10;
        }

        int days;
        try {
            System.out.print("Number of days: ");
            days = Integer.parseInt(scanner.nextLine().trim());
            if (days < 1) { ConsoleOutput.printLine("Days must be at least 1."); return; }
        } catch (NumberFormatException e) {
            ConsoleOutput.printLine("Invalid number.");
            return;
        }

        double total = vehicle.getTotalPrice() * days;
        ConsoleOutput.printHeading("PRICE BREAKDOWN");
        ConsoleOutput.printLine(String.format("Base: $%.2f x %d days = $%.2f", record.getBasePrice(), days, record.getBasePrice() * days));
        if (addonCost > 0) ConsoleOutput.printLine(String.format("Add-ons: $%.2f x %d days = $%.2f", addonCost, days, addonCost * days));
        ConsoleOutput.printLine(String.format("Total: $%.2f", total));

        ConsoleOutput.printLine("Payment Method:");
        ConsoleOutput.printLine("1. Credit Card");
        ConsoleOutput.printLine("2. PayPal");
        ConsoleOutput.printLine("3. Mobile Wallet");
        System.out.print("Choice: ");
        String pmt = scanner.nextLine().trim();

        PaymentStrategy paymentStrategy;
        if ("2".equals(pmt)) {
            System.out.print("PayPal email: ");
            paymentStrategy = new PayPalPayment(scanner.nextLine().trim());
        } else if ("3".equals(pmt)) {
            System.out.print("Wallet ID: ");
            paymentStrategy = new MobileWalletPayment(scanner.nextLine().trim());
        } else {
            System.out.print("Card Number: ");
            paymentStrategy = new CreditCardPayment(scanner.nextLine().trim());
        }

        statusSubject.attach(new CustomerNotifier(customer.getName(), customer.getEmail()));
        rentalFacade.registerVehicleState(vehicleId);

        Booking booking = rentalFacade.rentVehicle(customer, vehicle, paymentStrategy, days);

        record.setStatus("Booked");
        vehicleRepository.update(record);

        ConsoleOutput.printLine("Rental successful! Booking ID: " + booking.getBookingId());
        pause();
    }

    private static void returnVehicle() {
        User currentUser = SecurityContext.getCurrentUser();
        List<Booking> bookings = bookingRepository.findByCustomerId(currentUser.getId());
        List<Booking> active = bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.CONFIRMED).toList();
        if (active.isEmpty()) {
            ConsoleOutput.printLine("No active rentals to return.");
            return;
        }
        ConsoleOutput.printHeading("SELECT RENTAL TO RETURN");
        Booking booking = pickFromList("Enter number: ", active,
                b -> String.format("%s | Vehicle: %s | %d days", b.getBookingId(), b.getVehicleId(), b.getDays()));
        if (booking == null) return;

        int daysLate;
        try {
            System.out.print("Days late (0 if on time): ");
            daysLate = Integer.parseInt(scanner.nextLine().trim());
            if (daysLate < 0) { ConsoleOutput.printLine("Days late cannot be negative."); return; }
        } catch (NumberFormatException e) {
            ConsoleOutput.printLine("Invalid number.");
            return;
        }

        LateFeeStrategy fineStrategy = pickFineStrategy();
        if (fineStrategy == null) return;

        double dailyRate = vehicleRepository.findById(booking.getVehicleId())
                .map(VehicleRecord::getBasePrice).orElse(0.0);
        double fine = fineStrategy.calculateFine(daysLate, dailyRate);

        ConsoleOutput.printHeading("LATE FINE");
        ConsoleOutput.printLine(String.format("Days late: %d | Daily rate: $%.2f", daysLate, dailyRate));
        ConsoleOutput.printLine("Strategy: " + fineStrategy.getDescription());
        ConsoleOutput.printLine(String.format("Fine: $%.2f", fine));
        if (fine > 0) {
            System.out.print("Apply fine? (yes/no): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                ConsoleOutput.printLine("Return cancelled.");
                return;
            }
        }

        rentalFacade.applyLateFee(booking, daysLate, dailyRate);
        rentalFacade.returnVehicle(booking);
        vehicleRepository.updateStatus(booking.getVehicleId(), "Available");
        ConsoleOutput.printLine("Vehicle returned successfully.");
        pause();
    }

    private static LateFeeStrategy pickFineStrategy() {
        ConsoleOutput.printLine("Fine Strategy:");
        ConsoleOutput.printLine("1. Standard (50% of daily rate per day late)");
        ConsoleOutput.printLine("2. Flat ($25 per day late)");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();
        return switch (choice) {
            case "1" -> new StandardLateFee();
            case "2" -> new FlatLateFee();
            default -> {
                ConsoleOutput.printLine("Invalid choice.");
                yield null;
            }
        };
    }

    private static void cancelBooking() {
        User currentUser = SecurityContext.getCurrentUser();
        List<Booking> bookings = bookingRepository.findByCustomerId(currentUser.getId());
        List<Booking> active = bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.CONFIRMED).toList();
        if (active.isEmpty()) {
            ConsoleOutput.printLine("No active bookings to cancel.");
            return;
        }
        ConsoleOutput.printHeading("SELECT BOOKING TO CANCEL");
        Booking booking = pickFromList("Enter number: ", active,
                b -> String.format("%s | Vehicle: %s | %d days", b.getBookingId(), b.getVehicleId(), b.getDays()));
        if (booking == null) return;

        rentalFacade.cancelBooking(booking);
        vehicleRepository.updateStatus(booking.getVehicleId(), "Available");
        ConsoleOutput.printLine("Booking cancelled successfully.");
        pause();
    }

    private static void myBookings() {
        User currentUser = SecurityContext.getCurrentUser();
        ConsoleOutput.printHeading("MY BOOKINGS");
        List<Booking> bookings = bookingRepository.findByCustomerId(currentUser.getId());
        if (bookings.isEmpty()) {
            ConsoleOutput.printLine("No bookings found.");
            pause();
            return;
        }
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            ConsoleOutput.printLine(String.format("%d. %s | Vehicle: %s | $%.2f | %d days | %s%s",
                    i + 1, b.getBookingId(), b.getVehicleId(), b.getTotalAmount(), b.getDays(), b.getStatus(), feeLabel(b)));
        }
        pause();
    }

    private static String feeLabel(Booking b) {
        return b.getLateFee() > 0 ? " | Late fee: $" + String.format("%.2f", b.getLateFee()) : "";
    }
}
