package com.vehiclerent.creational;

import com.vehiclerent.util.ConsoleOutput;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton Pattern — Ensures only one configuration instance exists across the application.
 * Why: Centralizes DB connection settings and app configuration to avoid inconsistent state
 *       when multiple components read or modify properties concurrently.
 * Analogy: Like a building's master control room — one central panel manages all utility settings;
 *          everyone goes to the same room to read or update the config.
 */
public final class SystemConfig {

    private static volatile SystemConfig instance;

    private final String databaseUrl;
    private final String databaseUsername;
    private final String databasePassword;
    private final String databaseDriver;
    private final String databaseName;
    private final String systemVersion;
    private boolean databaseConnected;

    private SystemConfig() {
        Properties props = loadProperties();
        this.databaseUrl = firstNonEmpty(System.getenv("DB_URL"), props.getProperty("db.url"));
        this.databaseUsername = firstNonEmpty(System.getenv("DB_USER"), props.getProperty("db.username"));
        this.databasePassword = firstNonEmpty(System.getenv("DB_PASSWORD"), props.getProperty("db.password"));
        this.databaseDriver = props.getProperty("db.driver", "org.postgresql.Driver");
        this.databaseName = props.getProperty("system.name", "VehicleRentDB");
        this.systemVersion = props.getProperty("system.version", "1.0");
        this.databaseConnected = false;
    }

    public static SystemConfig getInstance() {
        if (instance == null) {
            synchronized (SystemConfig.class) {
                if (instance == null) {
                    instance = new SystemConfig();
                }
            }
        }
        return instance;
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream stream = SystemConfig.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (stream != null) {
                props.load(stream);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load database.properties", e);
        }
        return props;
    }

    private static String firstNonEmpty(String primary, String fallback) {
        if (primary != null && !primary.isBlank()) {
            return primary;
        }
        return fallback;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseDriver() {
        return databaseDriver;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public boolean isDatabaseConnected() {
        return databaseConnected;
    }

    public void setDatabaseConnected(boolean databaseConnected) {
        this.databaseConnected = databaseConnected;
    }

    public void displayConfiguration() {
        ConsoleOutput.printHeading("SINGLETON PATTERN - SYSTEM CONFIGURATION");
        ConsoleOutput.printLine("Database: PostgreSQL");
        ConsoleOutput.printLine("Database Name: " + databaseName);
        ConsoleOutput.printLine("Connection URL: " + maskPasswordInUrl(databaseUrl));
        ConsoleOutput.printLine("Connected: " + (databaseConnected ? "Yes" : "No"));
        ConsoleOutput.printLine("System Version: " + systemVersion);
    }

    private String maskPasswordInUrl(String url) {
        return url;
    }
}
