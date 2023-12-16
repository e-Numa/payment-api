package com.enuma.paymentapi.entity.enums;

//Enum representing the payment modes available in the application (at this moment only card payment is available)
public enum PaymentMode {
    CARD,
    TRANSFER,
    PAYPAL,
    GOOGLE_PAY,
    CRYPTO
}
