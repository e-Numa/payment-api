package com.enuma.paymentapi.controller;

import com.enuma.paymentapi.entity.model.PaymentEntity;
import com.enuma.paymentapi.service.payment_service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PaymentControllerTest {

    private final PaymentService paymentService = mock(PaymentService.class);
    private final PaymentController paymentController = new PaymentController(paymentService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

    @Test
    void makePayment_SuccessfulPayment() throws Exception {
        when(paymentService.processPayment(any())).thenReturn("SUCCESS");

        mockMvc.perform(post("/api/payments/make-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"amount\": 100.0, \"cardNumber\": \"1234567890123456\", \"expiryDate\": \"12/25\", \"cvv\": 123, \"deviceType\": \"Mobile\" }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{ \"status\": \"SUCCESS\" }"));
    }

    @Test
    void makePayment_ValidationFailure() throws Exception {
        when(paymentService.processPayment(any())).thenReturn("Validation error: Card number must be a 16-digit numeric value");


        mockMvc.perform(post("/api/payments/make-payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"amount\": -50.0, \"cardNumber\": \"invalid-card\", \"expiryDate\": \"12/25\", \"cvv\": 123, \"deviceType\": \"Mobile\" }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{ \"status\": \"Validation error: Card number must be a 16-digit numeric value\" }"));
    }

    @Test
    void getPayments() throws Exception {

        List<PaymentEntity> paymentEntities = List.of();
        when(paymentService.getPayments(anyString(), anyString(), anyString())).thenReturn(paymentEntities);

        // Perform the GET request
        mockMvc.perform(get("/api/payments/get-payments")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-12-31")
                        .param("status", "SUCCESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
