package com.Shubham.pvt.LTD.Payment.Application.service;

import com.Shubham.pvt.LTD.Payment.Application.dto.CreateRefundRequest;
import com.Shubham.pvt.LTD.Payment.Application.dto.RefundDTO;
import com.Shubham.pvt.LTD.Payment.Application.entity.Payment;
import com.Shubham.pvt.LTD.Payment.Application.entity.Refund;
import com.Shubham.pvt.LTD.Payment.Application.entity.RefundStatus;
import com.Shubham.pvt.LTD.Payment.Application.entity.User;
import com.Shubham.pvt.LTD.Payment.Application.repository.PaymentRepository;
import com.Shubham.pvt.LTD.Payment.Application.repository.RefundRepository;
import com.Shubham.pvt.LTD.Payment.Application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RefundService {

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public RefundDTO createRefund(CreateRefundRequest request) {
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + request.getPaymentId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        // Validate refund amount against payment amount
        if (request.getRefundAmount().compareTo(payment.getAmount()) > 0) {
            throw new RuntimeException("Refund amount cannot exceed payment amount");
        }

        Refund refund = new Refund();
        refund.setPayment(payment);
        refund.setUser(user);
        refund.setRefundAmount(request.getRefundAmount());
        refund.setCurrency(request.getCurrency());
        refund.setRefundType(request.getRefundType());
        refund.setReason(request.getReason());
        refund.setDescription(request.getDescription());
        refund.setInitiatedBy(request.getInitiatedBy());
        refund.setNotes(request.getNotes());
        refund.setStatus(RefundStatus.INITIATED);

        // Set refund fee
        BigDecimal refundFee = request.getRefundFee() != null ? request.getRefundFee() : BigDecimal.ZERO;
        refund.setRefundFee(refundFee);

        // Calculate net refund amount
        BigDecimal netRefundAmount = request.getRefundAmount().subtract(refundFee);
        refund.setNetRefundAmount(netRefundAmount);

        // Generate unique refund reference
        String refundRef = "REF-" + UUID.randomUUID().toString();
        refund.setRefundReference(refundRef);

        // Set initiated date
        refund.setInitiatedDate(LocalDateTime.now());

        Refund savedRefund = refundRepository.save(refund);
        return convertToDTO(savedRefund);
    }

    public RefundDTO getRefundById(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refund not found with id: " + id));
        return convertToDTO(refund);
    }

    public RefundDTO getRefundByReference(String reference) {
        Refund refund = refundRepository.findByRefundReference(reference)
                .orElseThrow(() -> new RuntimeException("Refund not found with reference: " + reference));
        return convertToDTO(refund);
    }

    public List<RefundDTO> getAllRefunds() {
        return refundRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RefundDTO> getRefundsByUserId(Long userId) {
        return refundRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RefundDTO> getRefundsByPaymentId(Long paymentId) {
        return refundRepository.findByPaymentId(paymentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RefundDTO> getRefundsByStatus(RefundStatus status) {
        return refundRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RefundDTO> getPendingApprovalRefunds() {
        return refundRepository.findPendingApprovalRefunds().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RefundDTO> getRecentRefundsByUser(Long userId) {
        return refundRepository.findTop10ByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RefundDTO approveRefund(Long id, String approvedBy) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refund not found with id: " + id));

        if (refund.getStatus() != RefundStatus.INITIATED && refund.getStatus() != RefundStatus.PENDING_APPROVAL) {
            throw new RuntimeException("Refund cannot be approved in current status: " + refund.getStatus());
        }

        refund.setStatus(RefundStatus.APPROVED);
        refund.setApprovedBy(approvedBy);
        refund.setApprovedDate(LocalDateTime.now());

        Refund updatedRefund = refundRepository.save(refund);
        return convertToDTO(updatedRefund);
    }

    @Transactional
    public RefundDTO rejectRefund(Long id, String rejectionReason) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refund not found with id: " + id));

        refund.setStatus(RefundStatus.REJECTED);
        refund.setRejectionReason(rejectionReason);

        Refund updatedRefund = refundRepository.save(refund);
        return convertToDTO(updatedRefund);
    }

    @Transactional
    public RefundDTO processRefund(Long id, String gatewayRefundId) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refund not found with id: " + id));

        refund.setStatus(RefundStatus.PROCESSING);
        refund.setGatewayRefundId(gatewayRefundId);
        refund.setProcessedDate(LocalDateTime.now());

        Refund updatedRefund = refundRepository.save(refund);
        return convertToDTO(updatedRefund);
    }

    @Transactional
    public RefundDTO completeRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refund not found with id: " + id));

        refund.setStatus(RefundStatus.COMPLETED);
        refund.setCompletedDate(LocalDateTime.now());

        // Update payment status to REFUNDED
        Payment payment;

        Refund updatedRefund = refundRepository.save(refund);
        return convertToDTO(updatedRefund);
    }

    @Transactional
    public RefundDTO failRefund(Long id, String failureReason) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refund not found with id: " + id));

        refund.setStatus(RefundStatus.FAILED);
        refund.setFailureReason(failureReason);

        Refund updatedRefund = refundRepository.save(refund);
        return convertToDTO(updatedRefund);
    }

    @Transactional
    public RefundDTO cancelRefund(Long id) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refund not found with id: " + id));

        if (refund.getStatus() == RefundStatus.COMPLETED) {
            throw new RuntimeException("Cannot cancel a completed refund");
        }

        refund.setStatus(RefundStatus.CANCELLED);

        Refund updatedRefund = refundRepository.save(refund);
        return convertToDTO(updatedRefund);
    }

    public BigDecimal getTotalRefundAmountByUserAndStatus(Long userId, RefundStatus status) {
        BigDecimal total = refundRepository.getTotalRefundAmountByUserAndStatus(userId, status);
        return total != null ? total : BigDecimal.ZERO;
    }

    public Long countRefundsByStatus(RefundStatus status) {
        return refundRepository.countRefundsByStatus(status);
    }

    private RefundDTO convertToDTO(Refund refund) {
        return new RefundDTO(
                refund.getId(),
                refund.getPayment().getId(),
                refund.getUser().getId(),
                refund.getUser().getUsername(),
                refund.getRefundAmount(),
                refund.getCurrency(),
                refund.getStatus(),
                refund.getRefundType(),
                refund.getRefundReference(),
                refund.getReason(),
                refund.getDescription(),
                refund.getInitiatedBy(),
                refund.getApprovedBy(),
                refund.getGatewayRefundId(),
                refund.getInitiatedDate(),
                refund.getApprovedDate(),
                refund.getProcessedDate(),
                refund.getCompletedDate(),
                refund.getRejectionReason(),
                refund.getFailureReason(),
                refund.getRefundFee(),
                refund.getNetRefundAmount(),
                refund.getNotes(),
                refund.getCreatedAt(),
                refund.getUpdatedAt()
        );
    }
}