package com.enuma.paymentapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


 //PaymentExceptionHandler class to handle custom PaymentException globally in the application.
@RestControllerAdvice
public class PaymentExceptionHandler {
    //Handles PaymentException, a custom exception specific to payment processing.
    @ExceptionHandler(PaymentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlePaymentException(PaymentException ex) {
        // Returns a BAD_REQUEST response with the error message from the PaymentException
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
