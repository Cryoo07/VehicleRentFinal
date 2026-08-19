package com.vehiclerent.repository;

import com.vehiclerent.creational.DatabaseConnection;
import com.vehiclerent.user.Customer;
import com.vehiclerent.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PostgreSQL JDBC implementation for customer persistence.
 */
public class JdbcCustomerRepository implements CustomerRepository {

    private final UserRepository userRepository;

    public JdbcCustomerRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final String INSERT_CUSTOMER = """
            INSERT INTO customers (id, name, email, phone) VALUES (?, ?, ?, ?)
            """;
    private static final String FIND_ALL = """
            SELECT id, name, email, phone FROM customers ORDER BY name
            """;
    private static final String FIND_BY_ID = """
            SELECT id, name, email, phone FROM customers WHERE id = ?
            """;

    @Override
    public void save(Customer customer, User user) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CUSTOMER)) {
            statement.setString(1, customer.getId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPhone());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save customer: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                customers.add(mapCustomer(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to list customers: " + e.getMessage(), e);
        }
        return customers;
    }

    @Override
    public Optional<Customer> findById(String id) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapCustomer(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find customer: " + e.getMessage(), e);
        }
    }

    private Customer mapCustomer(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"));
    }
}
