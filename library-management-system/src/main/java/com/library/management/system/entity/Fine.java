package com.library.management.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity đại diện cho tiền phạt của độc giả
 * Tiền phạt được tạo khi độc giả trả sách quá hạn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fines")
public class Fine extends BaseEntity {

    // Quan hệ ManyToOne: Nhiều tiền phạt có thể thuộc về 1 phiếu mượn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_receipt_id", nullable = false)
    private BorrowReceipt borrowReceipt;

    // Quan hệ ManyToOne: Nhiều tiền phạt thuộc về 1 độc giả
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 255)
    private String reason;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    // Trạng thái thanh toán: UNPAID, PAID
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    /**
     * Enum định nghĩa trạng thái thanh toán
     */
    public enum PaymentStatus {
        UNPAID,  // Chưa thanh toán
        PAID     // Đã thanh toán
    }
}