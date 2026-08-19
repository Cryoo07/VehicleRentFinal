package com.vehiclerent.behavioral;

/**
 * Strategy Pattern — Context.
 * Holds a reference to a PaymentStrategy and delegates payment execution to it.
 * Clients can switch strategies at runtime via setStrategy() without changing
 * their own code. This decouples the payment flow from specific algorithms.
 */
public class PaymentContext {

    private PaymentStrategy strategy;

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean executePayment(double amount) {
        return strategy.pay(amount);
    }
}
