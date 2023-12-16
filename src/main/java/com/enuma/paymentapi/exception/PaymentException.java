package com.enuma.paymentapi.exception;

// Custom exception class for handling payment-related exceptions in the application.
public class PaymentException extends RuntimeException {
   // Constructs a PaymentException with the specified error message.
    public PaymentException(String message) {
        super(message);
    }
}
