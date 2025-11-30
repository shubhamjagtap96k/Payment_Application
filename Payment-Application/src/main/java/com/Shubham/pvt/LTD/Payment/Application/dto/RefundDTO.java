package com.Shubham.pvt.LTD.Payment.Application.dto;

import com.Shubham.pvt.LTD.Payment.Application.entity.RefundStatus;
import com.Shubham.pvt.LTD.Payment.Application.entity.RefundType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RefundDTO {

    private Long id;
    private Long paymentId;
    private Long userId;
    private String username;
    private BigDecimal refundAmount;
    private String currency;
    private RefundStatus status;
    private RefundType refundType;
    private String refundReference;
    private String reason;
    private String description;
    private String initiatedBy;
    private String approvedBy;
    private String gatewayRefundId;
    private LocalDateTime initiatedDate;
    private LocalDateTime approvedDate;
    private LocalDateTime processedDate;
    private LocalDateTime completedDate;
    private String rejectionReason;
    private String failureReason;
    private BigDecimal refundFee;
    private BigDecimal netRefundAmount;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public RefundDTO() {
    }

    public RefundDTO(Long id, Long paymentId, Long userId, String username, BigDecimal refundAmount,
                     String currency, RefundStatus status, RefundType refundType, String refundReference,
                     String reason, String description, String initiatedBy, String approvedBy,
                     String gatewayRefundId, LocalDateTime initiatedDate, LocalDateTime approvedDate,
                     LocalDateTime processedDate, LocalDateTime completedDate, String rejectionReason,
                     String failureReason, BigDecimal refundFee, BigDecimal netRefundAmount, String notes,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.paymentId = paymentId;
        this.userId = userId;
        this.username = username;
        this.refundAmount = refundAmount;
        this.currency = currency;
        this.status = status;
        this.refundType = refundType;
        this.refundReference = refundReference;
        this.reason = reason;
        this.description = description;
        this.initiatedBy = initiatedBy;
        this.approvedBy = approvedBy;
        this.gatewayRefundId = gatewayRefundId;
        this.initiatedDate = initiatedDate;
        this.approvedDate = approvedDate;
        this.processedDate = processedDate;
        this.completedDate = completedDate;
        this.rejectionReason = rejectionReason;
        this.failureReason = failureReason;
        this.refundFee = refundFee;
        this.netRefundAmount = netRefundAmount;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public RefundStatus getStatus() {
        return status;
    }

    public void setStatus(RefundStatus status) {
        this.status = status;
    }

    public RefundType getRefundType() {
        return refundType;
    }

    public void setRefundType(RefundType refundType) {
        this.refundType = refundType;
    }

    public String getRefundReference() {
        return refundReference;
    }

    public void setRefundReference(String refundReference) {
        this.refundReference = refundReference;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getGatewayRefundId() {
        return gatewayRefundId;
    }

    public void setGatewayRefundId(String gatewayRefundId) {
        this.gatewayRefundId = gatewayRefundId;
    }

    public LocalDateTime getInitiatedDate() {
        return initiatedDate;
    }

    public void setInitiatedDate(LocalDateTime initiatedDate) {
        this.initiatedDate = initiatedDate;
    }

    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public LocalDateTime getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(LocalDateTime processedDate) {
        this.processedDate = processedDate;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public BigDecimal getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(BigDecimal refundFee) {
        this.refundFee = refundFee;
    }

    public BigDecimal getNetRefundAmount() {
        return netRefundAmount;
    }

    public void setNetRefundAmount(BigDecimal netRefundAmount) {
        this.netRefundAmount = netRefundAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}