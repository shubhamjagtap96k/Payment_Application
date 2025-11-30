package com.Shubham.pvt.LTD.Payment.Application.dto;

import com.Shubham.pvt.LTD.Payment.Application.entity.TransactionType;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private Long paymentId;
    private Long userId;
    private String username;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String currency;
    private TransactionStatus status;
    private String transactionReference;
    private String description;
    private String externalTransactionId;
    private String remarks;
    private String initiatedBy;
    private LocalDateTime transactionDate;
    private LocalDateTime completedDate;
    private BigDecimal fee;
    private BigDecimal tax;
    private BigDecimal totalAmount;
    private String sourceAccount;
    private String destinationAccount;
    private String failureMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public TransactionDTO() {
    }

    public TransactionDTO(Long id, Long paymentId, Long userId, String username,
                          TransactionType transactionType, BigDecimal amount, String currency,
                          TransactionStatus status, String transactionReference, String description,
                          String externalTransactionId, String remarks, String initiatedBy,
                          LocalDateTime transactionDate, LocalDateTime completedDate,
                          BigDecimal fee, BigDecimal tax, BigDecimal totalAmount,
                          String sourceAccount, String destinationAccount, String failureMessage,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.paymentId = paymentId;
        this.userId = userId;
        this.username = username;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.transactionReference = transactionReference;
        this.description = description;
        this.externalTransactionId = externalTransactionId;
        this.remarks = remarks;
        this.initiatedBy = initiatedBy;
        this.transactionDate = transactionDate;
        this.completedDate = completedDate;
        this.fee = fee;
        this.tax = tax;
        this.totalAmount = totalAmount;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.failureMessage = failureMessage;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
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
