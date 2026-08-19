package com.vehiclerent.user;

import com.vehiclerent.repository.InMemoryCustomerRepository;
import com.vehiclerent.repository.InMemoryUserRepository;
import com.vehiclerent.security.SecurityContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private InMemoryUserRepository userRepo;
    private UserService service;

    @BeforeEach
    void setUp() {
        userRepo = new InMemoryUserRepository();
        userRepo.save(new User("ADM001", "admin", "admin123", User.Role.ADMIN));
        service = new UserService(userRepo, new InMemoryCustomerRepository());
    }

    @AfterEach
    void tearDown() {
        SecurityContext.clear();
    }

    @Test
    void adminLoginSucceeds() {
        assertTrue(service.login("admin", "admin123"));
        assertNotNull(SecurityContext.getCurrentUser());
        assertTrue(SecurityContext.getCurrentUser().isAdmin());
    }

    @Test
    void invalidLoginFails() {
        assertFalse(service.login("admin", "wrong"));
    }

    @Test
    void customerRegistrationAndLogin() {
        Customer customer = new Customer("C-100", "Jane", "jane@test.com", "+999");
        service.registerCustomer(customer, "jane", "secret");
        assertTrue(service.login("jane", "secret"));
        assertEquals(User.Role.CUSTOMER, SecurityContext.getCurrentUser().getRole());
        assertTrue(service.findCustomerById("C-100").isPresent());
    }

    @Test
    void duplicateUsernameThrows() {
        Customer c1 = new Customer("C1", "Alice", "a@test.com", "111");
        service.registerCustomer(c1, "alice", "pass");
        Customer c2 = new Customer("C2", "Bob", "b@test.com", "222");
        assertThrows(IllegalArgumentException.class,
                () -> service.registerCustomer(c2, "alice", "pass2"));
    }
}
