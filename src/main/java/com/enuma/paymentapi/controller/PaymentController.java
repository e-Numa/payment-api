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
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/make-payment")
    public ResponseEntity<PaymentResponse> makePayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        String status = (String) paymentService.processPayment(paymentRequest);
        PaymentResponse response = new PaymentResponse(status);
        HttpStatus httpStatus = status.equals("Payment process SUCCESSFUL") ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, httpStatus);
    }


    @GetMapping("/get-payments")
    public List<PaymentEntity> getPayments(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String status) {
        return paymentService.getPayments(startDate, endDate, status);
    }
}
