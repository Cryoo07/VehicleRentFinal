package com.vehiclerent.repository;

import com.vehiclerent.user.Customer;
import com.vehiclerent.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory customer storage for unit tests and offline mode.
 */
public class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<String, Customer> customers = new HashMap<>();

    @Override
    public void save(Customer customer, User user) {
        customers.put(customer.getId(), customer);
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(customers.get(id));
    }
}
