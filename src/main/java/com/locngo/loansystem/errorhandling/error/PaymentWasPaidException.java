package com.locngo.loansystem.errorhandling.error;

public class PaymentWasPaidException extends RuntimeException {

    private static final long serialVersionUID = -4069045576660553653L;

    public PaymentWasPaidException() {
        super("Payment was paid!");
    }
}
