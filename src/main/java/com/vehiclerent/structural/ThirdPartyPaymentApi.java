package com.vehiclerent.structural;

import com.vehiclerent.util.ConsoleOutput;

/**
 * Adapter Pattern — Adaptee.
 * A third-party payment API with an incompatible interface (chargeCustomer
 * with different parameter types and return format). Cannot be used directly
 * by the rental system. The PaymentGatewayAdapter wraps this class to make
 * it conform to PaymentGateway.
 */
public class ThirdPartyPaymentApi {

    public String chargeCustomer(String userRef, String currency, String amountStr) {
        return "TXN-" + userRef + "-" + System.currentTimeMillis();
    }
}
