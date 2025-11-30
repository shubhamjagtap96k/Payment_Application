package com.Shubham.pvt.LTD.Payment.Application.controller;

import com.Shubham.pvt.LTD.Payment.Application.dto.CreateRefundRequest;
import com.Shubham.pvt.LTD.Payment.Application.dto.RefundDTO;
import com.Shubham.pvt.LTD.Payment.Application.entity.RefundStatus;
import com.Shubham.pvt.LTD.Payment.Application.service.RefundService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/refunds")
public class RefundController {
    @Autowired
    private RefundService refundService;

    @PostMapping
    public ResponseEntity<RefundDTO> createRefund(@Valid @RequestBody CreateRefundRequest request) {
        RefundDTO refund = refundService.createRefund(request);
        return new ResponseEntity<>(refund, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundDTO> getRefundById(@PathVariable Long id) {
        RefundDTO refund = refundService.getRefundById(id);
        return ResponseEntity.ok(refund);
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<RefundDTO> getRefundByReference(@PathVariable String reference) {
        RefundDTO refund = refundService.getRefundByReference(reference);
        return ResponseEntity.ok(refund);
    }

    @GetMapping
    public ResponseEntity<List<RefundDTO>> getAllRefunds() {
        List<RefundDTO> refunds = refundService.getAllRefunds();
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RefundDTO>> getRefundsByUserId(@PathVariable Long userId) {
        List<RefundDTO> refunds = refundService.getRefundsByUserId(userId);
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<RefundDTO>> getRefundsByPaymentId(@PathVariable Long paymentId) {
        List<RefundDTO> refunds = refundService.getRefundsByPaymentId(paymentId);
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RefundDTO>> getRefundsByStatus(@PathVariable RefundStatus status) {
        List<RefundDTO> refunds = refundService.getRefundsByStatus(status);
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/pending-approval")
    public ResponseEntity<List<RefundDTO>> getPendingApprovalRefunds() {
        List<RefundDTO> refunds = refundService.getPendingApprovalRefunds();
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<RefundDTO>> getRecentRefundsByUser(@PathVariable Long userId) {
        List<RefundDTO> refunds = refundService.getRecentRefundsByUser(userId);
        return ResponseEntity.ok(refunds);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<RefundDTO> approveRefund(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String approvedBy = request.get("approvedBy");
        RefundDTO refund = refundService.approveRefund(id, approvedBy);
        return ResponseEntity.ok(refund);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<RefundDTO> rejectRefund(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String rejectionReason = request.get("rejectionReason");
        RefundDTO refund = refundService.rejectRefund(id, rejectionReason);
        return ResponseEntity.ok(refund);
    }

    @PutMapping("/{id}/process")
    public ResponseEntity<RefundDTO> processRefund(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String gatewayRefundId = request.get("gatewayRefundId");
        RefundDTO refund = refundService.processRefund(id, gatewayRefundId);
        return ResponseEntity.ok(refund);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<RefundDTO> completeRefund(@PathVariable Long id) {
        RefundDTO refund = refundService.completeRefund(id);
        return ResponseEntity.ok(refund);
    }

    @PutMapping("/{id}/fail")
    public ResponseEntity<RefundDTO> failRefund(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String failureReason = request.get("failureReason");
        RefundDTO refund = refundService.failRefund(id, failureReason);
        return ResponseEntity.ok(refund);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<RefundDTO> cancelRefund(@PathVariable Long id) {
        RefundDTO refund = refundService.cancelRefund(id);
        return ResponseEntity.ok(refund);
    }

    @GetMapping("/user/{userId}/total")
    public ResponseEntity<BigDecimal> getTotalRefundAmountByUserAndStatus(
            @PathVariable Long userId,
            @RequestParam RefundStatus status) {
        BigDecimal total = refundService.getTotalRefundAmountByUserAndStatus(userId, status);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countRefundsByStatus(@RequestParam RefundStatus status) {
        Long count = refundService.countRefundsByStatus(status);
        return ResponseEntity.ok(count);
    }
}