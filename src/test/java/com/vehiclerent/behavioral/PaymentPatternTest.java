package com.vehiclerent.behavioral;

import com.vehiclerent.structural.PaymentGateway;
import com.vehiclerent.structural.PaymentGatewayAdapter;
import com.vehiclerent.structural.ThirdPartyPaymentApi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentPatternTest {

    @Test
    void creditCardPaymentSucceeds() {
        PaymentStrategy strategy = new CreditCardPayment("4111111111111111");
        assertTrue(strategy.pay(100.0));
    }

    @Test
    void payPalPaymentSucceeds() {
        PaymentStrategy strategy = new PayPalPayment("user@paypal.com");
        assertTrue(strategy.pay(200.0));
    }

    @Test
    void mobileWalletPaymentSucceeds() {
        PaymentStrategy strategy = new MobileWalletPayment("WALLET-001");
        assertTrue(strategy.pay(75.0));
    }

    @Test
    void paymentContextSwitchesStrategy() {
        PaymentContext context = new PaymentContext(new CreditCardPayment("1234567890123456"));
        assertTrue(context.executePayment(50.0));
        context.setStrategy(new PayPalPayment("test@paypal.com"));
        assertTrue(context.executePayment(50.0));
    }

    @Test
    void adapterProcessesGatewayTransaction() {
        PaymentGateway gateway = new PaymentGatewayAdapter(new ThirdPartyPaymentApi());
        assertTrue(gateway.processTransaction("CUST-1", 150.0));
    }
}
