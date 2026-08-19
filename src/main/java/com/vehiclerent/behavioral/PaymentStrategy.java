package com.vehiclerent.behavioral;

/**
 * Strategy Pattern — Interface.
 * Defines the contract for interchangeable payment algorithms.
 * Used so that the rental system can support multiple payment methods
 * (Credit Card, PayPal, Mobile Wallet) without coupling to any concrete
 * implementation. New payment types can be added without modifying existing code.
 */
public interface PaymentStrategy {

    boolean pay(double amount);
}
