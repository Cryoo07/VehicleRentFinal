package com.vehiclerent.behavioral;

import com.vehiclerent.util.ConsoleOutput;

/**
 * Strategy Pattern — Concrete Strategy.
 * Implements PaymentStrategy for Mobile Wallet payments.
 * Encapsulates the mobile-wallet-specific algorithm so it can be used
 * interchangeably with other payment methods via PaymentContext.
 */
public class MobileWalletPayment implements PaymentStrategy {

    private final String walletId;

    public MobileWalletPayment(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public boolean pay(double amount) {
        ConsoleOutput.printHeading("STRATEGY PATTERN - PAYMENT PROCESSING");
        ConsoleOutput.printLine("Payment Method: Mobile Wallet");
        ConsoleOutput.printLine("Wallet ID: " + walletId);
        ConsoleOutput.printLine("Amount: $" + amount);
        ConsoleOutput.printLine("Payment Successful");
        return true;
    }
}
