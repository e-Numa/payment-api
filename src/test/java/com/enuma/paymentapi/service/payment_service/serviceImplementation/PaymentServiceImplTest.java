package com.enuma.paymentapi.service.payment_service.serviceImplementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.enuma.paymentapi.entity.enums.PaymentMode;
import com.enuma.paymentapi.entity.model.PaymentEntity;
import com.enuma.paymentapi.exception.PaymentException;
import com.enuma.paymentapi.payload.request.PaymentRequest;
import com.enuma.paymentapi.repository.PaymentRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PaymentServiceImpl.class})
@ExtendWith(SpringExtension.class)

class PaymentServiceImplTest {
    @MockBean
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentServiceImpl paymentServiceImpl;

    @Test
    void testProcessPaymentByMode() {
        PaymentRequest paymentRequest = new PaymentRequest();

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(10.0d);
        paymentEntity.setCardHolderName("Card Holder Name");
        paymentEntity.setCardNumber("42");
        paymentEntity.setCreatedAt(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        paymentEntity.setCvv(1);
        paymentEntity.setDeviceType("Device Type");
        paymentEntity.setExpiryDate("2020-03-01");
        paymentEntity.setId(1L);
        paymentEntity.setStatus("Status");
        paymentEntity.setUpdatedAt(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        assertEquals("Payment process failed: Invalid card number. Card number must be a 16-digit numeric value.",
                paymentServiceImpl.processPaymentByMode(paymentRequest, paymentEntity));
    }

    @Test
    void testProcessPaymentByMode2() {
        PaymentRequest paymentRequest = new PaymentRequest(10.0d,
                "Invalid card number. Card number must be a 16-digit numeric value.", "42", "2020-03-01", 1,
                "Invalid card number. Card number must be a 16-digit numeric value.", PaymentMode.CARD);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(10.0d);
        paymentEntity.setCardHolderName("Card Holder Name");
        paymentEntity.setCardNumber("42");
        paymentEntity.setCreatedAt(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        paymentEntity.setCvv(1);
        paymentEntity.setDeviceType("Device Type");
        paymentEntity.setExpiryDate("2020-03-01");
        paymentEntity.setId(1L);
        paymentEntity.setStatus("Status");
        paymentEntity.setUpdatedAt(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        assertEquals("Payment process failed: Invalid card number. Card number must be a 16-digit numeric value.",
                paymentServiceImpl.processPaymentByMode(paymentRequest, paymentEntity));
    }

    @Test
    void testProcessPaymentByMode3() {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(10.0d);
        paymentEntity.setCardHolderName("Card Holder Name");
        paymentEntity.setCardNumber("42");
        paymentEntity.setCreatedAt(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        paymentEntity.setCvv(1);
        paymentEntity.setDeviceType("Device Type");
        paymentEntity.setExpiryDate("2020-03-01");
        paymentEntity.setId(1L);
        paymentEntity.setStatus("Status");
        paymentEntity.setUpdatedAt(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        assertEquals(
                "Payment process failed: Cannot invoke \"com.enuma.paymentapi.payload.request.PaymentRequest.getCardNumber()\""
                        + " because \"paymentRequest\" is null",
                paymentServiceImpl.processPaymentByMode(null, paymentEntity));
    }

    @Test
    void testProcessPaymentByMode4() {
        PaymentRequest paymentRequest = new PaymentRequest();

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(10.0d);
        paymentEntity.setCardHolderName("Card Holder Name");
        paymentEntity.setCardNumber("42");
        paymentEntity.setCreatedAt(mock(java.sql.Date.class));
        paymentEntity.setCvv(1);
        paymentEntity.setDeviceType("Device Type");
        paymentEntity.setExpiryDate("2020-03-01");
        paymentEntity.setId(1L);
        paymentEntity.setStatus("Status");
        paymentEntity
                .setUpdatedAt(java.util.Date.from(
                        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        assertEquals("Payment process failed: Invalid card number. Card number must be a 16-digit numeric value.",
                paymentServiceImpl.processPaymentByMode(paymentRequest, paymentEntity));
    }

    @Test
    void testProcessPaymentByMode5() {
        PaymentRequest paymentRequest = new PaymentRequest(10.0d,
                "Invalid card number. Card number must be a 16-digit numeric value.", "9999999999999999", "2020-03-01",
                1,
                "Invalid card number. Card number must be a 16-digit numeric value.", PaymentMode.CARD);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(10.0d);
        paymentEntity.setCardHolderName("Card Holder Name");
        paymentEntity.setCardNumber("42");
        paymentEntity.setCreatedAt(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        paymentEntity.setCvv(1);
        paymentEntity.setDeviceType("Device Type");
        paymentEntity.setExpiryDate("2020-03-01");
        paymentEntity.setId(1L);
        paymentEntity.setStatus("Status");
        paymentEntity.setUpdatedAt(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        assertEquals("Payment process failed: Invalid expiry date. Expiry date must be in the format MM/YY.",
                paymentServiceImpl.processPaymentByMode(paymentRequest, paymentEntity));
    }

    @Test
    void testProcessPaymentByMode6() {
        PaymentRequest paymentRequest = new PaymentRequest(10.0d,
                "Invalid card number. Card number must be a 16-digit numeric value.", "", "2020-03-01", 1,
                "Invalid card number. Card number must be a 16-digit numeric value.", PaymentMode.CARD);

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(10.0d);
        paymentEntity.setCardHolderName("Card Holder Name");
        paymentEntity.setCardNumber("42");
        paymentEntity.setCreatedAt(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        paymentEntity.setCvv(1);
        paymentEntity.setDeviceType("Device Type");
        paymentEntity.setExpiryDate("2020-03-01");
        paymentEntity.setId(1L);
        paymentEntity.setStatus("Status");
        paymentEntity.setUpdatedAt(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        assertEquals("Payment process failed: Invalid card number. Card number must be a 16-digit numeric value.",
                paymentServiceImpl.processPaymentByMode(paymentRequest, paymentEntity));
    }

    @Test
    void testGetPayments() {
        ArrayList<PaymentEntity> paymentEntityList = new ArrayList<>();
        when(paymentRepository.findAll()).thenReturn(paymentEntityList);
        List<PaymentEntity> actualPayments = paymentServiceImpl.getPayments("2020-03-01", "2020-03-01", "Status");
        verify(paymentRepository).findAll();
        assertTrue(actualPayments.isEmpty());
        assertSame(paymentEntityList, actualPayments);
    }

    @Test
    void testGetPayments2() {
        when(paymentRepository.findAll()).thenThrow(new PaymentException("An error occurred"));
        assertThrows(PaymentException.class,
                () -> paymentServiceImpl.getPayments("2020-03-01", "2020-03-01", "Status"));
        verify(paymentRepository).findAll();
    }
}
