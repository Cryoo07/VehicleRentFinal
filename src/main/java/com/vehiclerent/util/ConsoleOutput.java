package com.vehiclerent.util;

/**
 * Utility class for formatted console output with headings and separators.
 * Not a design pattern — simply a static helper that provides consistent,
 * reusable display formatting across the application.
 * Analogy: Like a printer with preset templates — you just feed it text and
 *          it returns a neatly formatted page (heading, body, separator).
 */
public final class ConsoleOutput {

    private ConsoleOutput() {
    }

    public static void printHeading(String title) {
        String line = "=".repeat(Math.max(title.length() + 4, 41));
        System.out.println();
        System.out.println(line);
        System.out.println("  " + title);
        System.out.println(line);
    }

    public static void printLine(String message) {
        System.out.println(message);
    }

    public static void printSeparator() {
        System.out.println("-".repeat(41));
    }
}
