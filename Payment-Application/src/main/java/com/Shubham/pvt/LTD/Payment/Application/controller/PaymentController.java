package com.Shubham.pvt.LTD.Payment.Application.controller;

import com.Shubham.pvt.LTD.Payment.Application.dto.CreatePaymentRequest;
import com.Shubham.pvt.LTD.Payment.Application.dto.PaymentDTO;
import com.Shubham.pvt.LTD.Payment.Application.entity.PaymentStatus;
import com.Shubham.pvt.LTD.Payment.Application.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        PaymentDTO payment = paymentService.createPayment(request);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        PaymentDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentDTO> getPaymentByTransactionId(@PathVariable String transactionId) {
        PaymentDTO payment = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(payment);
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByUserId(@PathVariable Long userId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        List<PaymentDTO> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PaymentDTO> updatePaymentStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        PaymentStatus status = PaymentStatus.valueOf(request.get("status"));
        PaymentDTO payment = paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}/process")
    public ResponseEntity<PaymentDTO> processPayment(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String gatewayTransactionId = request.get("gatewayTransactionId");
        PaymentDTO payment = paymentService.processPayment(id, gatewayTransactionId);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<PaymentDTO> completePayment(@PathVariable Long id) {
        PaymentDTO payment = paymentService.completePayment(id);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}/fail")
    public ResponseEntity<PaymentDTO> failPayment(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String failureReason = request.get("failureReason");
        PaymentDTO payment = paymentService.failPayment(id, failureReason);
        return ResponseEntity.ok(payment);
    }
}