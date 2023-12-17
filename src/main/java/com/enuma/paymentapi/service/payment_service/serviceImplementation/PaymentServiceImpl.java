package com.enuma.paymentapi.service.payment_service.serviceImplementation;

import com.enuma.paymentapi.entity.enums.PaymentMode;
import com.enuma.paymentapi.entity.enums.Status;
import com.enuma.paymentapi.entity.model.PaymentEntity;
import com.enuma.paymentapi.exception.PaymentException;
import com.enuma.paymentapi.payload.request.PaymentRequest;
import com.enuma.paymentapi.repository.PaymentRepository;
import com.enuma.paymentapi.service.payment_service.PaymentService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Object processPaymentByMode(PaymentRequest paymentRequest, PaymentEntity paymentEntity) {
        try {
            // Validate the payment request for required fields and constraints
            validatePaymentRequest(paymentRequest);

            // Retrieve the payment mode from the request
            PaymentMode paymentMode = paymentRequest.getPaymentMode();

            // Check if the payment mode is other than "card"
            if (paymentMode != PaymentMode.CARD) {
                // For any payment mode other than "card", consider it as unavailable
                paymentEntity.setStatus(Status.FAILED.name());
                return paymentMode + " payment mode is currently unavailable. Kindly use card payment.";
            }

            // Validate the presence of device type in the payment request
            if (StringUtils.isBlank(paymentRequest.getDeviceType())) {
                return "Validation error: Device type is required.";
            }

            //Check if cardholder name is valid
            if (StringUtils.isBlank(paymentRequest.getCardHolderName()) || !isValidCardHolderName(paymentRequest.getCardHolderName())) {
                throw new PaymentException("Invalid card holder name. Please provide a valid entry of both first name and surname.");
            }

            // Check if the card already exists in the database
            if (isCardAlreadyExist(paymentRequest.getCardNumber())) {
                throw new PaymentException("Card already exists. Payment not processed.");
            }

            // Validate that the amount is a positive value
            if (paymentRequest.getAmount() <= 0) {
                return "Validation error: Amount must be a positive value";
            }

            // Check if the card has expired
            if (isCardExpired(paymentRequest.getExpiryDate())) {
                throw new PaymentException("Card is expired. Payment not processed.");
            }

            // Determine the payment status based on confirmation
            if (isPaymentConfirmed()) {
                paymentEntity.setStatus(Status.SUCCESSFUL.name());
            } else {
                paymentEntity.setStatus(Status.PENDING.name());
            }

            // Set payment entity details and save to the repository
            paymentEntity.setCreatedAt(new Date());
            paymentEntity.setUpdatedAt(new Date());
            paymentEntity.setDeviceType(paymentRequest.getDeviceType());
            paymentEntity.setAmount(paymentRequest.getAmount());
            paymentEntity.setCardNumber(paymentRequest.getCardNumber());
            paymentEntity.setExpiryDate(paymentRequest.getExpiryDate());
            paymentEntity.setCvv(paymentRequest.getCvv());
            paymentEntity.setCardHolderName(paymentRequest.getCardHolderName());

            paymentRepository.save(paymentEntity);

            // Return a message indicating the result of the payment process
            return "Payment process " + paymentEntity.getStatus();
        } catch (PaymentException pe) {
            // Handle specific payment exceptions and return an appropriate failure message
            return "Payment process failed: " + pe.getMessage();
        } catch (Exception e) {
            // Handle general exceptions and return a generic failure message
            return "Payment process failed: " + e.getMessage();
        }
    }

    @Override
    public List<PaymentEntity> getPayments(String startDate, String endDate, String status) {
        // Fetch all payments from the database
        return paymentRepository.findAll();
    }

    //Checks whether a card with the given card number already exists in the database.
    private boolean isCardAlreadyExist(String cardNumber) {
        return paymentRepository.existsByCardNumber(cardNumber);
    }

    //Checks whether the payment is confirmed.
    private boolean isPaymentConfirmed() {
        return true;
    }

    //Checks whether a card with the given expiration date has already expired.
    private boolean isCardExpired(String expirationDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
        dateFormat.setLenient(false); // Ensure strict date parsing

        try {
            Date expiration = dateFormat.parse(expirationDate);
            return expiration.before(new Date());
        } catch (ParseException e) {
            // If date parsing fails, consider the card expired
            return true;
        }
    }

    private boolean isValidCardHolderName(String cardHolderName) {
        // A valid cardholder name should contain both first name and surname
        String[] nameParts = cardHolderName.trim().split("\\s+");
        return nameParts.length == 2 && !StringUtils.isBlank(nameParts[0]) && !StringUtils.isBlank(nameParts[1]);
    }

    private void validatePaymentRequest(PaymentRequest paymentRequest) {
        // Check if card number is not null or empty and matches the 16-digit numeric format
        if (StringUtils.isBlank(paymentRequest.getCardNumber()) || !paymentRequest.getCardNumber().matches("\\d{16}")) {
            throw new PaymentException("Invalid card number. Card number must be a 16-digit numeric value.");
        }

        // Check if expiry date is not null or empty and matches the MM/YY format
        if (StringUtils.isBlank(paymentRequest.getExpiryDate()) ||
                !paymentRequest.getExpiryDate().matches("(0[1-9]|1[0-2])/(\\d{2})")) {
            throw new PaymentException("Invalid expiry date. Expiry date must be in the format MM/YY.");
        }

        // Check if cvv is not null and is a 3-digit numeric value
        if (paymentRequest.getCvv() == null || !String.valueOf(paymentRequest.getCvv()).matches("\\d{3}")) {
            throw new PaymentException("Invalid CVV. CVV must be a 3-digit numeric value.");
        }
    }
}
