package com.vehiclerent.repository;

import com.vehiclerent.creational.DatabaseConnection;
import com.vehiclerent.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * PostgreSQL JDBC implementation for user persistence.
 */
public class JdbcUserRepository implements UserRepository {

    private static final String INSERT = """
            INSERT INTO users (id, username, password, role) VALUES (?, ?, ?, ?)
            """;
    private static final String FIND_BY_USERNAME = """
            SELECT id, username, password, role FROM users WHERE username = ?
            """;
    private static final String EXISTS = """
            SELECT 1 FROM users WHERE username = ? LIMIT 1
            """;

    @Override
    public void save(User user) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save user: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapUser(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find user: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to check username: " + e.getMessage(), e);
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("id"),
                rs.getString("username"),
                rs.getString("password"),
                User.Role.valueOf(rs.getString("role")));
    }
}
