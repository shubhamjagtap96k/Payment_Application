package com.Shubham.pvt.LTD.Payment.Application.controller;


import com.Shubham.pvt.LTD.Payment.Application.dto.CreateTransactionRequest;
import com.Shubham.pvt.LTD.Payment.Application.dto.TransactionDTO;
import com.Shubham.pvt.LTD.Payment.Application.entity.TransactionType;
import com.Shubham.pvt.LTD.Payment.Application.service.TransactionService;
import jakarta.validation.Valid;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        TransactionDTO transaction = transactionService.createTransaction(request);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<TransactionDTO> getTransactionByReference(@PathVariable String reference) {
        TransactionDTO transaction = transactionService.getTransactionByReference(reference);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByUserId(@PathVariable Long userId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByPaymentId(@PathVariable Long paymentId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByPaymentId(paymentId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByStatus(@PathVariable TransactionStatus status) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByStatus(status);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByType(@PathVariable TransactionType type) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByType(type);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<TransactionDTO>> getRecentTransactionsByUser(@PathVariable Long userId) {
        List<TransactionDTO> transactions = transactionService.getRecentTransactionsByUser(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByUserAndDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByUserAndDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TransactionDTO> updateTransactionStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        TransactionStatus status = TransactionStatus.valueOf(request.get("status"));
        TransactionDTO transaction = transactionService.updateTransactionStatus(id, status);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<TransactionDTO> completeTransaction(@PathVariable Long id) {
        TransactionDTO transaction = transactionService.completeTransaction(id);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{id}/fail")
    public ResponseEntity<TransactionDTO> failTransaction(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String failureMessage = request.get("failureMessage");
        TransactionDTO transaction = transactionService.failTransaction(id, failureMessage);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/user/{userId}/total")
    public ResponseEntity<BigDecimal> getTotalAmountByUserStatusAndType(
            @PathVariable Long userId,
            @RequestParam TransactionStatus status,
            @RequestParam TransactionType type) {
        BigDecimal total = transactionService.getTotalAmountByUserStatusAndType(userId, status, type);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> countTransactionsByUserAndStatus(
            @PathVariable Long userId,
            @RequestParam TransactionStatus status) {
        Long count = transactionService.countTransactionsByUserAndStatus(userId, status);
        return ResponseEntity.ok(count);
    }
}