package com.vehiclerent.creational;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton Pattern — Single database connection manager for PostgreSQL.
 * Why: Reuses the shared SystemConfig, provides a single point of JDBC access,
 *      and prevents multiple redundant connection factories from being created.
 * Analogy: Like a single reception desk in an office — every department (client) goes
 *          through the same desk to get connected to the right line (database).
 */
public final class DatabaseConnection {

    private static volatile DatabaseConnection instance;
    private final SystemConfig config;

    private DatabaseConnection() {
        this.config = SystemConfig.getInstance();
        loadDriver();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    private void loadDriver() {
        try {
            Class.forName(config.getDatabaseDriver());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("PostgreSQL JDBC driver not found", e);
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                config.getDatabaseUrl(),
                config.getDatabaseUsername(),
                config.getDatabasePassword());
        config.setDatabaseConnected(true);
        return connection;
    }

    public boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection.isValid(3);
        } catch (SQLException e) {
            config.setDatabaseConnected(false);
            return false;
        }
    }
}
