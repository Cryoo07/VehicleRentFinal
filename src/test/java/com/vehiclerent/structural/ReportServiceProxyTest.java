package com.vehiclerent.structural;

import com.vehiclerent.rental.Booking;
import com.vehiclerent.security.SecurityContext;
import com.vehiclerent.repository.InMemoryBookingRepository;
import com.vehiclerent.user.Customer;
import com.vehiclerent.user.User;
import com.vehiclerent.user.UserService;
import com.vehiclerent.repository.InMemoryUserRepository;
import com.vehiclerent.repository.InMemoryCustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceProxyTest {

    private ReportService proxy;
    private UserService userService;

    @BeforeEach
    void setUp() {
        InMemoryUserRepository userRepo = new InMemoryUserRepository();
        userRepo.save(new User("ADM001", "admin", "admin123", User.Role.ADMIN));
        userService = new UserService(userRepo, new InMemoryCustomerRepository());

        InMemoryBookingRepository repo = new InMemoryBookingRepository();
        repo.save(new Booking("BK-RPT", "C1", "V1", 500.0, 3), 3);
        proxy = new ReportServiceProxy(new ReportServiceImpl(repo));
    }

    @AfterEach
    void tearDown() {
        SecurityContext.clear();
    }

    @Test
    void adminCanAccessReports() {
        userService.login("admin", "admin123");
        String report = proxy.generateRevenueReport();
        assertTrue(report.contains("Total Revenue"));
        assertFalse(report.contains("Access Denied"));
    }

    @Test
    void customerDeniedReportAccess() {
        userService.registerCustomer(
                new Customer("C2", "Test User", "t@test.com", "123"),
                "testuser", "pass");
        userService.login("testuser", "pass");
        String report = proxy.generateRevenueReport();
        assertEquals("Access Denied", report);
    }

    @Test
    void unauthenticatedUserDenied() {
        SecurityContext.clear();
        assertEquals("Access Denied", proxy.generateRentalHistoryReport());
    }
}
