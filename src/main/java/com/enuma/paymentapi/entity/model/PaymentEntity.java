package com.enuma.paymentapi.entity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "payment_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "Amount must be a positive value")
    private double amount;

    private String status;

    @Column(name = "timestamp")
    private String timestamp;

    @NotBlank(message = "Device type is required")
    private String deviceType;

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "\\d{16}", message = "Card number must be a 16-digit numeric value")
    private String cardNumber;

    @PrePersist
    protected void onCreate() {
        timestamp = String.valueOf(new Date());
    }
}
