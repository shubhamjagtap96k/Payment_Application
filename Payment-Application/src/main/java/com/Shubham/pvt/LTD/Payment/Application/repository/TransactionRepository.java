package com.Shubham.pvt.LTD.Payment.Application.repository;

import com.Shubham.pvt.LTD.Payment.Application.entity.Transaction;
import com.Shubham.pvt.LTD.Payment.Application.entity.TransactionType;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionReference(String transactionReference);

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByPaymentId(Long paymentId);

    List<Transaction> findByStatus(TransactionStatus status);

    List<Transaction> findByTransactionType(TransactionType transactionType);

    List<Transaction> findByUserIdAndStatus(Long userId, TransactionStatus status);

    List<Transaction> findByUserIdAndTransactionType(Long userId, TransactionType transactionType);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findTransactionsByUserAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT t FROM Transaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findTransactionsByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.status = :status AND t.transactionType = :type")
    BigDecimal getTotalAmountByUserStatusAndType(
            @Param("userId") Long userId,
            @Param("status") TransactionStatus status,
            @Param("type") TransactionType type
    );

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.user.id = :userId AND t.status = :status")
    Long countTransactionsByUserAndStatus(
            @Param("userId") Long userId,
            @Param("status") TransactionStatus status
    );

    List<Transaction> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
}