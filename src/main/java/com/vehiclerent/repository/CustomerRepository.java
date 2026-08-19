package com.vehiclerent.repository;

import com.vehiclerent.user.Customer;
import com.vehiclerent.user.User;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    void save(Customer customer, User user);

    List<Customer> findAll();

    Optional<Customer> findById(String id);
}
