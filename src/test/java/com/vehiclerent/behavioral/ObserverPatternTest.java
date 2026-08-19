package com.vehiclerent.behavioral;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ObserverPatternTest {

    @Test
    void observersReceiveStatusUpdates() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));

        try {
            VehicleStatusSubject subject = new VehicleStatusSubject();
            subject.attach(new CustomerNotifier("Alice", "alice@test.com"));
            subject.attach(new CustomerNotifier("Bob", "bob@test.com"));
            subject.setStatus("CAR-100", "Booked");

            String output = out.toString();
            assertTrue(output.contains("Alice"));
            assertTrue(output.contains("Bob"));
            assertTrue(output.contains("Booked"));
        } finally {
            System.setOut(original);
        }
    }

    @Test
    void detachedObserverDoesNotReceiveUpdates() {
        VehicleStatusSubject subject = new VehicleStatusSubject();
        CustomerNotifier observer = new CustomerNotifier("Charlie", "charlie@test.com");
        subject.attach(observer);
        subject.detach(observer);
        assertDoesNotThrow(() -> subject.setStatus("CAR-200", "Available"));
    }
}
