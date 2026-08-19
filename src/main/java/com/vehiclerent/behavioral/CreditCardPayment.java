package com.vehiclerent.behavioral;

import com.vehiclerent.util.ConsoleOutput;

/**
 * Strategy Pattern — Concrete Strategy.
 * Implements PaymentStrategy for Credit Card payments.
 * Encapsulates the credit-card-specific algorithm so it can be used
 * interchangeably with other payment methods via PaymentContext.
 */
public class CreditCardPayment implements PaymentStrategy {

    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        ConsoleOutput.printHeading("STRATEGY PATTERN - PAYMENT PROCESSING");
        ConsoleOutput.printLine("Payment Method: Credit Card");
        ConsoleOutput.printLine("Card: ****" + cardNumber.substring(Math.max(0, cardNumber.length() - 4)));
        ConsoleOutput.printLine("Amount: $" + amount);
        ConsoleOutput.printLine("Payment Successful");
        return true;
    }
}
