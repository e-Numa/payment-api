package com.enuma.paymentapi.repository;

import com.enuma.paymentapi.entity.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//Repository interface for performing CRUD operations on PaymentEntity entities
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

//Checks whether a payment entity with the given card number already exists.
    boolean existsByCardNumber(String cardNumber);
}
