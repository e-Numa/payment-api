package com.enuma.paymentapi.repository;

import com.enuma.paymentapi.entity.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PaymentEntity p WHERE p.cardNumber = :cardNumber")
    boolean existsByCardNumber(@Param("cardNumber") String cardNumber);

    @Query("SELECT p FROM PaymentEntity p WHERE (:startDate IS NULL OR p.timestamp >= :startDate) " +
            "AND (:endDate IS NULL OR p.timestamp <= :endDate) " +
            "AND (:status IS NULL OR p.status = :status)")
    List<PaymentEntity> getPayments(@Param("startDate") String startDate,
                                    @Param("endDate") String endDate,
                                    @Param("status") String status);

   // Optional<PaymentEntity> findByCardNumber(String cardNumber);
}
