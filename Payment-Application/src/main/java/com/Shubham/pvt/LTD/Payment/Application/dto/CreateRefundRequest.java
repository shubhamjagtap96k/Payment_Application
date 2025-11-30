package com.Shubham.pvt.LTD.Payment.Application.dto;

import com.Shubham.pvt.LTD.Payment.Application.entity.RefundType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateRefundRequest {

    @NotNull(message = "Payment ID is required")
    private Long paymentId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Refund amount is required")
    @DecimalMin(value = "0.01", message = "Refund amount must be greater than 0")
    private BigDecimal refundAmount;

    private String currency = "USD";

    @NotNull(message = "Refund type is required")
    private RefundType refundType;

    @NotNull(message = "Reason is required")
    private String reason;

    private String description;

    private String initiatedBy;

    private BigDecimal refundFee;

    private String notes;

    // Constructors
    public CreateRefundRequest() {
    }

    public CreateRefundRequest(Long paymentId, Long userId, BigDecimal refundAmount, String currency,
                               RefundType refundType, String reason, String description) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.refundAmount = refundAmount;
        this.currency = currency;
        this.refundType = refundType;
        this.reason = reason;
        this.description = description;
    }

    // Getters and Setters
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

    public RefundType getRefundType() {
        return refundType;
    }

    public void setRefundType(RefundType refundType) {
        this.refundType = refundType;
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

    public BigDecimal getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(BigDecimal refundFee) {
        this.refundFee = refundFee;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
