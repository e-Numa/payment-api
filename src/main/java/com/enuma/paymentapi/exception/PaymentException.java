package com.enuma.paymentapi.exception;

public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }
}
