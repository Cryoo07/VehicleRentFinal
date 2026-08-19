package com.vehiclerent.creational;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    @Test
    void emailCreatorProducesEmailNotification() {
        NotificationCreator creator = new EmailNotificationCreator();
        assertDoesNotThrow(() -> creator.notifyCustomer("test@email.com", "Hello"));
    }

    @Test
    void smsCreatorProducesSmsNotification() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            new SmsNotificationCreator().notifyCustomer("+1234567890", "Your booking is confirmed!");
            String output = out.toString();
            assertTrue(output.contains("SMS"));
            assertTrue(output.contains("Your booking is confirmed!"));
        } finally {
            System.setOut(original);
        }
    }

    @Test
    void pushCreatorProducesPushNotification() {
        NotificationCreator creator = new PushNotificationCreator();
        assertDoesNotThrow(() -> creator.notifyCustomer("token-123", "Push message"));
    }
}
