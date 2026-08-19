package com.vehiclerent.database;

import com.vehiclerent.creational.DatabaseConnection;
import com.vehiclerent.util.ConsoleOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Initializes PostgreSQL schema on application startup.
 */
public final class SchemaInitializer {

    private SchemaInitializer() {
    }

    public static void initialize() {
        ConsoleOutput.printHeading("DATABASE - SCHEMA INITIALIZATION");
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            String sql = readSchemaSql();
            for (String command : sql.split(";")) {
                String trimmed = command.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
            ConsoleOutput.printLine("PostgreSQL schema initialized successfully.");
        } catch (SQLException | IOException e) {
            throw new IllegalStateException("Failed to initialize database schema: " + e.getMessage(), e);
        }
    }

    private static String readSchemaSql() throws IOException {
        try (InputStream stream = SchemaInitializer.class.getClassLoader().getResourceAsStream("schema.sql")) {
            if (stream == null) {
                throw new IOException("schema.sql not found in resources");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        }
    }
}
