package com.enuma.paymentapi.service.payment_service.serviceImplementation;

import com.enuma.paymentapi.entity.enums.Status;
import com.enuma.paymentapi.entity.model.PaymentEntity;
import com.enuma.paymentapi.exception.PaymentException;
import com.enuma.paymentapi.payload.request.PaymentRequest;
import com.enuma.paymentapi.repository.PaymentRepository;
import com.enuma.paymentapi.service.payment_service.PaymentService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final Validator validator;

    @Override
    public String processPayment(PaymentRequest paymentRequest) {
        try {
            Set<ConstraintViolation<PaymentRequest>> requestViolations = validator.validate(paymentRequest);

            if (!requestViolations.isEmpty()) {
                return "Validation error: " + requestViolations.iterator().next().getMessage();
            }

            PaymentEntity paymentEntity = createPaymentEntity(paymentRequest);

            if (isCardAlreadyExist(paymentRequest.getCardNumber())) {
                throw new PaymentException("Card already exists. Payment not processed.");
            }

            if (paymentRequest.getAmount() <= 0) {
                return "Validation error: Amount must be a positive value";
            }

            if (isCardExpired(paymentRequest.getExpiryDate())) {
                throw new PaymentException("Card is expired. Payment not processed.");
            }

            if (isPaymentConfirmed()) {
                paymentEntity.setStatus(Status.SUCCESSFUL.name());
            } else {
                paymentEntity.setStatus(Status.PENDING.name());
            }

            paymentEntity.setTimestamp(String.valueOf(new Date()));
            paymentEntity.setDeviceType(paymentRequest.getDeviceType());

            paymentRepository.save(paymentEntity);

            return "Payment process " + paymentEntity.getStatus();
        } catch (PaymentException pe) {
            return "Payment process failed: " + pe.getMessage();

        } catch (Exception e) {
            return "Payment process failed: " + e.getMessage();
        }
    }

    @Override
    public List<PaymentEntity> getPayments(String startDate, String endDate, String status) {
        return paymentRepository.getPayments(startDate, endDate, status);
    }

    private boolean isCardAlreadyExist(String cardNumber) {
        return paymentRepository.existsByCardNumber(cardNumber);
    }

    private PaymentEntity createPaymentEntity(PaymentRequest paymentRequest) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setCardNumber(paymentRequest.getCardNumber());
        paymentEntity.setAmount(paymentRequest.getAmount());
        paymentEntity.setDeviceType("DefaultDeviceType");
        return paymentEntity;
    }

    private boolean isPaymentConfirmed() {
        return true;
    }

    private boolean isCardExpired(String expirationDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
        try {
            Date expiration = dateFormat.parse(expirationDate);
            return expiration.before(new Date());
        } catch (ParseException e) {
            return true;
        }
    }
}
