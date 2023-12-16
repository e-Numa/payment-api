package com.enuma.paymentapi.controller;

import com.enuma.paymentapi.entity.model.PaymentEntity;
import com.enuma.paymentapi.payload.request.PaymentRequest;
import com.enuma.paymentapi.payload.response.PaymentResponse;
import com.enuma.paymentapi.service.payment_service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
//Controller class responsible for handling payment-related HTTP requests.
public class PaymentController {

    //The service responsible for processing payment-related operations
    private final PaymentService paymentService;

    //API mapping, handles a POST request to initiate a payment
    @PostMapping("/make-payment")
    public ResponseEntity<PaymentResponse> makePayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        // Process the payment and obtain the result
        Object result = paymentService.processPaymentByMode(paymentRequest, new PaymentEntity());

        // Assuming PaymentResponse has a 'status' field
        String status = result.toString();
        PaymentResponse response = new PaymentResponse(status);

        // Determine HTTP status based on the payment result
        HttpStatus httpStatus = status.equals("Payment process SUCCESSFUL") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        // Return ResponseEntity with the PaymentResponse and HTTP status
        return new ResponseEntity<>(response, httpStatus);
    }

    //API mapping, handles a GET request to retrieve payments based on optional parameters
    @GetMapping("/get-payments")
    public List<PaymentEntity> getPayments(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String status) {
        return paymentService.getPayments(startDate, endDate, status);
    }
}