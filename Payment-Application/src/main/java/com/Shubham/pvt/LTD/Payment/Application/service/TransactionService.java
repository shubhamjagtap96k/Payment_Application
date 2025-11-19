package com.Shubham.pvt.LTD.Payment.Application.service;

import com.Shubham.pvt.LTD.Payment.Application.dto.CreateTransactionRequest;
import com.Shubham.pvt.LTD.Payment.Application.dto.TransactionDTO;
import com.Shubham.pvt.LTD.Payment.Application.entity.Payment;
import com.Shubham.pvt.LTD.Payment.Application.entity.Transaction;
import com.Shubham.pvt.LTD.Payment.Application.entity.TransactionType;
import com.Shubham.pvt.LTD.Payment.Application.entity.User;
import com.Shubham.pvt.LTD.Payment.Application.repository.PaymentRepository;
import com.Shubham.pvt.LTD.Payment.Application.repository.TransactionRepository;
import com.Shubham.pvt.LTD.Payment.Application.repository.UserRepository;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TransactionDTO createTransaction(CreateTransactionRequest request) {
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + request.getPaymentId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        Transaction transaction = new Transaction();
        transaction.setPayment(payment);
        transaction.setUser(user);
        transaction.setTransactionType(request.getTransactionType());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setDescription(request.getDescription());
        transaction.setExternalTransactionId(request.getExternalTransactionId());
        transaction.setRemarks(request.getRemarks());
        transaction.setInitiatedBy(request.getInitiatedBy());
        transaction.setSourceAccount(request.getSourceAccount());
        transaction.setDestinationAccount(request.getDestinationAccount());

        // Set fee and tax
        BigDecimal fee = request.getFee() != null ? request.getFee() : BigDecimal.ZERO;
        BigDecimal tax = request.getTax() != null ? request.getTax() : BigDecimal.ZERO;
        transaction.setFee(fee);
        transaction.setTax(tax);

        // Calculate total amount
        BigDecimal totalAmount = request.getAmount().add(fee).add(tax);
        transaction.setTotalAmount(totalAmount);

        // Generate unique transaction reference
        String transactionRef = "TRX-" + UUID.randomUUID().toString();
        transaction.setTransactionReference(transactionRef);

        // Set transaction date
        transaction.setTransactionDate(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDTO(savedTransaction);
    }

    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        return convertToDTO(transaction);
    }

    public TransactionDTO getTransactionByReference(String reference) {
        Transaction transaction = transactionRepository.findByTransactionReference(reference)
                .orElseThrow(() -> new RuntimeException("Transaction not found with reference: " + reference));
        return convertToDTO(transaction);
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByPaymentId(Long paymentId) {
        return transactionRepository.findByPaymentId(paymentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByStatus(TransactionStatus status) {
        return transactionRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByTransactionType(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getRecentTransactionsByUser(Long userId) {
        return transactionRepository.findTop10ByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findTransactionsByDateRange(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByUserAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findTransactionsByUserAndDateRange(userId, startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransactionDTO updateTransactionStatus(Long id, TransactionStatus newStatus) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        transaction.setStatus(newStatus);

        if (newStatus == TransactionStatus.COMMITTED) {
            transaction.setCompletedDate(LocalDateTime.now());
        }

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return convertToDTO(updatedTransaction);
    }

    @Transactional
    public TransactionDTO completeTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        transaction.setStatus(TransactionStatus.COMMITTED);
        transaction.setCompletedDate(LocalDateTime.now());

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return convertToDTO(updatedTransaction);
    }

    @Transactional
    public TransactionDTO failTransaction(Long id, String failureMessage) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        transaction.setStatus(TransactionStatus.NOT_ACTIVE);
        transaction.setFailureMessage(failureMessage);

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return convertToDTO(updatedTransaction);
    }

    public BigDecimal getTotalAmountByUserStatusAndType(Long userId, TransactionStatus status, TransactionType type) {
        BigDecimal total = transactionRepository.getTotalAmountByUserStatusAndType(userId, status, type);
        return total != null ? total : BigDecimal.ZERO;
    }

    public Long countTransactionsByUserAndStatus(Long userId, TransactionStatus status) {
        return transactionRepository.countTransactionsByUserAndStatus(userId, status);
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getPayment().getId(),
                transaction.getUser().getId(),
                transaction.getUser().getUsername(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getStatus(),
                transaction.getTransactionReference(),
                transaction.getDescription(),
                transaction.getExternalTransactionId(),
                transaction.getRemarks(),
                transaction.getInitiatedBy(),
                transaction.getTransactionDate(),
                transaction.getCompletedDate(),
                transaction.getFee(),
                transaction.getTax(),
                transaction.getTotalAmount(),
                transaction.getSourceAccount(),
                transaction.getDestinationAccount(),
                transaction.getFailureMessage(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }
}