package com.Shubham.pvt.LTD.Payment.Application.entity;

public enum RefundStatus {
    INITIATED,
    PENDING_APPROVAL,
    APPROVED,
    REJECTED,
    PROCESSING,
    COMPLETED,
    FAILED,
    CANCELLED
}