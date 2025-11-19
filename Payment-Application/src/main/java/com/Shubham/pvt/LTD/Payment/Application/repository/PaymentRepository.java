package com.Shubham.pvt.LTD.Payment.Application.repository;

import com.Shubham.pvt.LTD.Payment.Application.entity.Payment;
import com.Shubham.pvt.LTD.Payment.Application.entity.PaymentStatus;
import com.Shubham.pvt.LTD.Payment.Application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUser(User user);

    List<Payment> findByUserId(Long userId);

    List<Payment> findByStatus(PaymentStatus status);

    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByUserIdAndStatus(Long userId, PaymentStatus status);

    @Query("SELECT p FROM Payment p WHERE p.user.id = :userId AND p.createdAt BETWEEN :startDate AND :endDate")
    List<Payment> findPaymentsByUserAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.user.id = :userId AND p.status = :status")
    BigDecimal getTotalAmountByUserAndStatus(
            @Param("userId") Long userId,
            @Param("status") PaymentStatus status
    );
}