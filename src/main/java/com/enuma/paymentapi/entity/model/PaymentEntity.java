package com.enuma.paymentapi.entity.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "payment_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

//Entity class representing a payment record in the application.
public class PaymentEntity {

    //Unique identifier for the payment entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //The amount associated with the payment
    @Column(nullable = false)
    private Double amount;

    //The status of the payment (PENDING, SUCCESSFUL, FAILED)
    @Column(nullable = false)
    private String status;

    //The date and time when the payment entity was created
    @Column(name = "created_at")
    private Date createdAt;

    //The date and time when the payment entity was last updated
    @Column(name = "updated_at")
    private Date updatedAt;

    //The type of device used for the payment (mobile, desktop, browser, POS)
    @Column(nullable = false)
    private String deviceType;

    //The card name on the face of the card used for payment
    @Column(nullable = false)
    private String cardHolderName;

    //The card number associated with the card used for payment
    @Column(nullable = false)
    private String cardNumber;

    //The expiry date of the card used for the payment
    @Column(nullable = false)
    private String expiryDate;

    //The Card Verification Value (CVV) associated with the card used for payment
    @Column(nullable = false)
    private Integer cvv;


//Callback method triggered before the payment entity is created/persisted
// Sets the createdAt field to the current date and time
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

//Callback method triggered before the payment entity is updated
//Sets the updatedAt field to the current date and time
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
