package com.enuma.paymentapi.controller;

import com.enuma.paymentapi.entity.model.PaymentEntity;
import com.enuma.paymentapi.payload.request.PaymentRequest;
import com.enuma.paymentapi.payload.response.PaymentResponse;
import com.enuma.paymentapi.service.payment_service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/make-payment")
    public PaymentResponse makePayment(@RequestBody PaymentRequest paymentRequest) {
        String status = (String) paymentService.processPayment(paymentRequest);
        return new PaymentResponse(status);
    }

    @GetMapping("/get-payments")
    public List<PaymentEntity> getPayments(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String status) {
        return paymentService.getPayments(startDate, endDate, status);
    }
}
