package com.vehiclerent.structural;

/**
 * Adapter Pattern — Target.
 * The interface expected by the rental system for payment processing.
 * Defines the domain-specific contract (processTransaction) that client
 * code depends on, keeping it decoupled from any external API.
 */
public interface PaymentGateway {

    boolean processTransaction(String customerId, double amount);
}
