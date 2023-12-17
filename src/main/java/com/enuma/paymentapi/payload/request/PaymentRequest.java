package com.enuma.paymentapi.payload.request;

import com.enuma.paymentapi.entity.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

//Request payload class representing the details for initiating a user payment
public class PaymentRequest {
    //The amount to be processed for the user payment
    private Double amount;

    //The card name on the face of the card used for payment
    private String cardHolderName;

    //The card number associated with the user payment
    private String cardNumber;

    //The expiry date of the card used for the user payment
    private String expiryDate;

    //The Card Verification Value (CVV) associated with the user payment card
    private Integer cvv;

    //The type of device used for the payment (mobile, desktop, browser, POS)
    private String deviceType;

    //The payment mode preferred by user (CARD, TRANSFER, PAYPAL)
    private PaymentMode paymentMode;

}
