package com.vehiclerent.structural;

import com.vehiclerent.util.ConsoleOutput;

/**
 * Adapter Pattern — Adapter.
 * Adapts ThirdPartyPaymentApi (Adaptee) to the PaymentGateway (Target) interface.
 * Translates processTransaction calls into chargeCustomer calls, converting
 * parameters and return types. This integrates an external system without
 * modifying any existing rental code.
 */
public class PaymentGatewayAdapter implements PaymentGateway {

    private final ThirdPartyPaymentApi thirdPartyApi;

    public PaymentGatewayAdapter(ThirdPartyPaymentApi thirdPartyApi) {
        this.thirdPartyApi = thirdPartyApi;
    }

    @Override
    public boolean processTransaction(String customerId, double amount) {
        ConsoleOutput.printHeading("ADAPTER PATTERN - PAYMENT GATEWAY");
        ConsoleOutput.printLine("Adapting third-party API to internal gateway...");
        String transactionId = thirdPartyApi.chargeCustomer(customerId, "USD", String.valueOf(amount));
        ConsoleOutput.printLine("Customer: " + customerId);
        ConsoleOutput.printLine("Amount: $" + amount);
        ConsoleOutput.printLine("Transaction ID: " + transactionId);
        ConsoleOutput.printLine("Gateway Payment Successful");
        return true;
    }
}
