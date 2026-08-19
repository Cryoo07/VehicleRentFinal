package com.vehiclerent.behavioral;

import com.vehiclerent.util.ConsoleOutput;

/**
 * Strategy Pattern — Concrete Strategy.
 * Implements PaymentStrategy for PayPal payments.
 * Encapsulates the PayPal-specific algorithm so it can be used
 * interchangeably with other payment methods via PaymentContext.
 */
public class PayPalPayment implements PaymentStrategy {

    private final String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public boolean pay(double amount) {
        ConsoleOutput.printHeading("STRATEGY PATTERN - PAYMENT PROCESSING");
        ConsoleOutput.printLine("Payment Method: PayPal");
        ConsoleOutput.printLine("Account: " + email);
        ConsoleOutput.printLine("Amount: $" + amount);
        ConsoleOutput.printLine("Payment Successful");
        return true;
    }
}
