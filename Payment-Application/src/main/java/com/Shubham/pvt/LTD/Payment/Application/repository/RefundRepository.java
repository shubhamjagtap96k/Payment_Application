package com.Shubham.pvt.LTD.Payment.Application.repository;

import com.Shubham.pvt.LTD.Payment.Application.entity.Refund;
import com.Shubham.pvt.LTD.Payment.Application.entity.RefundStatus;
import com.Shubham.pvt.LTD.Payment.Application.entity.RefundType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

    Optional<Refund> findByRefundReference(String refundReference);

    List<Refund> findByUserId(Long userId);

    List<Refund> findByPaymentId(Long paymentId);

    List<Refund> findByStatus(RefundStatus status);

    List<Refund> findByRefundType(RefundType refundType);

    List<Refund> findByUserIdAndStatus(Long userId, RefundStatus status);

    @Query("SELECT r FROM Refund r WHERE r.user.id = :userId AND r.initiatedDate BETWEEN :startDate AND :endDate")
    List<Refund> findRefundsByUserAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT r FROM Refund r WHERE r.initiatedDate BETWEEN :startDate AND :endDate")
    List<Refund> findRefundsByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT SUM(r.refundAmount) FROM Refund r WHERE r.user.id = :userId AND r.status = :status")
    BigDecimal getTotalRefundAmountByUserAndStatus(
            @Param("userId") Long userId,
            @Param("status") RefundStatus status
    );

    @Query("SELECT COUNT(r) FROM Refund r WHERE r.status = :status")
    Long countRefundsByStatus(@Param("status") RefundStatus status);

    @Query("SELECT r FROM Refund r WHERE r.status = 'PENDING_APPROVAL' ORDER BY r.initiatedDate ASC")
    List<Refund> findPendingApprovalRefunds();

    List<Refund> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
}
