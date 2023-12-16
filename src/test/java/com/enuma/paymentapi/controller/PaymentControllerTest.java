package com.enuma.paymentapi.controller;

import com.enuma.paymentapi.entity.enums.PaymentMode;
import com.enuma.paymentapi.payload.request.PaymentRequest;
import com.enuma.paymentapi.service.payment_service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ContextConfiguration(classes = {PaymentController.class})
@ExtendWith(SpringExtension.class)
class PaymentControllerTest {
    // Injecting the PaymentController for testing
    private PaymentController paymentController;

    // Mocking the PaymentService
    @MockBean
    private PaymentService paymentService;

    @Test
    void testGetPayments() throws Exception {
        when(paymentService.getPayments(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/payments/get-payments");
        MockMvcBuilders.standaloneSetup(paymentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testMakePayment() throws Exception {
        when(paymentService.processPaymentByMode(Mockito.any(), Mockito.any()))
                .thenReturn("Process Payment By Mode");
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(10.0d);
        paymentRequest.setCardNumber("42");
        paymentRequest.setCvv(1);
        paymentRequest.setDeviceType("Device Type");
        paymentRequest.setExpiryDate("2020-03-01");
        paymentRequest.setPaymentMode(PaymentMode.CARD);
        String content = (new ObjectMapper()).writeValueAsString(paymentRequest);

        // Performing the POST request and asserting the response
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/payments/make-payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(paymentController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"Process Payment By Mode\"}"));
    }

    @Test
    void testMakePayment2() throws Exception {
        // Setting up mock data and expectations
        when(paymentService.processPaymentByMode(Mockito.any(), Mockito.any()))
                .thenReturn("Payment process SUCCESSFUL");

        // Creating a PaymentRequest object and converting it to JSON
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(10.0d);
        paymentRequest.setCardNumber("42");
        paymentRequest.setCvv(1);
        paymentRequest.setDeviceType("Device Type");
        paymentRequest.setExpiryDate("2020-03-01");
        paymentRequest.setPaymentMode(PaymentMode.CARD);
        String content = (new ObjectMapper()).writeValueAsString(paymentRequest);

        // Performing the POST request and asserting the response
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/payments/make-payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(paymentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"Payment process SUCCESSFUL\"}"));
    }
}
