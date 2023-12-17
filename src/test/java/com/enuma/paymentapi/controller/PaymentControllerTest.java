package com.enuma.paymentapi.controller;

import static org.mockito.Mockito.when;

import com.enuma.paymentapi.entity.enums.PaymentMode;
import com.enuma.paymentapi.entity.model.PaymentEntity;
import com.enuma.paymentapi.payload.request.PaymentRequest;
import com.enuma.paymentapi.service.payment_service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PaymentController.class})
@ExtendWith(SpringExtension.class)
class PaymentControllerTest {
  @Autowired
  private PaymentController paymentController;

  @MockBean
  private PaymentService paymentService;

  @Test
  void testGetPayments() throws Exception {
    when(paymentService.getPayments(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
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
    when(paymentService.processPaymentByMode(Mockito.<PaymentRequest>any(), Mockito.<PaymentEntity>any()))
            .thenReturn("Process Payment By Mode");

    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setAmount(10.0d);
    paymentRequest.setCardHolderName("Card Holder Name");
    paymentRequest.setCardNumber("42");
    paymentRequest.setCvv(1);
    paymentRequest.setDeviceType("Device Type");
    paymentRequest.setExpiryDate("2020-03-01");
    paymentRequest.setPaymentMode(PaymentMode.CARD);
    String content = (new ObjectMapper()).writeValueAsString(paymentRequest);
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
    when(paymentService.processPaymentByMode(Mockito.<PaymentRequest>any(), Mockito.<PaymentEntity>any()))
            .thenReturn("Payment process SUCCESSFUL");

    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setAmount(10.0d);
    paymentRequest.setCardHolderName("Card Holder Name");
    paymentRequest.setCardNumber("42");
    paymentRequest.setCvv(1);
    paymentRequest.setDeviceType("Device Type");
    paymentRequest.setExpiryDate("2020-03-01");
    paymentRequest.setPaymentMode(PaymentMode.CARD);
    String content = (new ObjectMapper()).writeValueAsString(paymentRequest);
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
