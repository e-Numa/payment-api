package com.enuma.paymentapi.service.payment_service;

import com.enuma.paymentapi.entity.model.PaymentEntity;
import com.enuma.paymentapi.payload.request.PaymentRequest;

import java.util.List;

public interface PaymentService {
    Object processPayment(PaymentRequest paymentRequest);

    List<PaymentEntity> getPayments(String startDate, String endDate, String status);
}
