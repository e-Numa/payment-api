package com.enuma.paymentapi.payload.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PaymentRequest {
    @Positive(message = "Amount must be a positive value")
    private double amount;

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "\\d{16}", message = "Card number must be a 16-digit numeric value")
    private String cardNumber;

    @NotBlank(message = "Expiry date is required")
    @Pattern(regexp = "(0[1-9]|1[0-2])/(\\d{2})", message = "Expiry date must be in the format MM/YY")
    private String expiryDate;

    @NotNull(message = "CVV is required")
    @Digits(integer = 3, fraction = 0, message = "CVV must be a 3-digit numeric value")
    private Integer cvv;

    @NotBlank(message = "Device type is required")
    private String deviceType;

}
