package com.enuma.paymentapi.service.payment_service.serviceImplementation;

import com.enuma.paymentapi.entity.model.PaymentEntity;
import com.enuma.paymentapi.payload.request.PaymentRequest;
import com.enuma.paymentapi.repository.PaymentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void testProcessPayment_SuccessfulPayment() {
        PaymentRequest paymentRequest = new PaymentRequest(100.0, "1234567812345678", "01/23", 123, "Mobile");
        when(validator.validate(paymentRequest)).thenReturn(Collections.emptySet());
        when(paymentRepository.existsByCardNumber(anyString())).thenReturn(false);
        when(paymentRepository.save(any())).thenReturn(new PaymentEntity());

        String result = paymentService.processPayment(paymentRequest);

        assertEquals("Payment process SUCCESSFUL", result);
    }

    @Test
    void testProcessPayment_ValidationFailure() {
        PaymentRequest paymentRequest = new PaymentRequest(100.0, "1234567812345678", "01/23", 123, "Mobile");

        Set<ConstraintViolation<PaymentRequest>> violations = new HashSet<>();
        ConstraintViolation<PaymentRequest> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Your validation error message");
        violations.add(violation);

        when(validator.validate(paymentRequest)).thenReturn(violations);

        String result = paymentService.processPayment(paymentRequest);

        assertTrue(result.contains("Validation error"));
    }

    @Test
    void testProcessPayment_DuplicateCardNumber() {
        PaymentRequest paymentRequest = new PaymentRequest(100.0, "1234567812345678", "01/23", 123, "Mobile");

        when(validator.validate(paymentRequest)).thenReturn(Collections.emptySet());
        when(paymentRepository.existsByCardNumber(anyString())).thenReturn(true);

        String result = paymentService.processPayment(paymentRequest);

        assertTrue(result.contains("Card already exists. Payment not processed."));

        verify(paymentRepository, never()).save(any());
    }


    @Test
    void testProcessPayment_InvalidPaymentRequest() {
        PaymentRequest paymentRequest = new PaymentRequest(-15000.00, "1234567812345678", "01/23", 123, "Mobile");

        when(validator.validate(paymentRequest)).thenReturn(Collections.emptySet());
        String result = paymentService.processPayment(paymentRequest);

        assertTrue(result.contains("Validation error"), "Expected validation error message");
        assertTrue(result.contains("Amount must be a positive value"), "Expected amount-related error message");
    }


    @Test
    void testGetPayments() {
        when(paymentRepository.getPayments(anyString(), anyString(), anyString())).thenReturn(
                Arrays.asList(new PaymentEntity(), new PaymentEntity()));

        List<PaymentEntity> result = paymentService.getPayments("2023-01-01", "2023-12-31", "SUCCESS");

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
