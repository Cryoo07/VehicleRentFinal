package com.vehiclerent.user;

import com.vehiclerent.repository.CustomerRepository;
import com.vehiclerent.repository.UserRepository;
import com.vehiclerent.security.SecurityContext;
import com.vehiclerent.util.ConsoleOutput;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public UserService(UserRepository userRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    public boolean login(String username, String password) {
        ConsoleOutput.printHeading("USER MANAGEMENT - LOGIN");
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            SecurityContext.setCurrentUser(userOpt.get());
            ConsoleOutput.printLine("Login Successful: " + username + " (" + userOpt.get().getRole() + ")");
            return true;
        }
        ConsoleOutput.printLine("Login Failed: Invalid credentials.");
        return false;
    }

    public void logout() {
        SecurityContext.clear();
        ConsoleOutput.printLine("User logged out.");
    }

    public void registerCustomer(Customer customer, String username, String password) {
        ConsoleOutput.printHeading("USER MANAGEMENT - CUSTOMER REGISTRATION");
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
        User user = new User(customer.getId(), username, password, User.Role.CUSTOMER);
        userRepository.save(user);
        customerRepository.save(customer, user);
        ConsoleOutput.printLine("Customer Registered: " + customer.getName());
        ConsoleOutput.printLine("Email: " + customer.getEmail());
        ConsoleOutput.printLine("Phone: " + customer.getPhone());
    }

    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerById(String id) {
        return customerRepository.findById(id);
    }
}
