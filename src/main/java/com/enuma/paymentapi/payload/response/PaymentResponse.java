package com.enuma.paymentapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PaymentResponse {
    //Returns to the request, the status of the payment operation
    private String status;

    // Getters and setters are auto generated using lombok, but does not necessarily have to be within the face of the
    // code in the IDE, this is to reduce boilerplate code.
}