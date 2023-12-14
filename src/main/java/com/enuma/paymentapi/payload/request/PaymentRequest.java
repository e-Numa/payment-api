package com.enuma.paymentapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PaymentRequest {
    private double amount;
    private String cardNumber;
    private String expiryDate;
    private int cvv;
//    private String deviceType;
}
