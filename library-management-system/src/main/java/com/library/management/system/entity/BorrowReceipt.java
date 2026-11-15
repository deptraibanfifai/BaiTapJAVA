package com.library.management.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity đại diện cho phiếu mượn sách
 * Mỗi phiếu mượn thuộc về một độc giả và có thể chứa nhiều chi tiết mượn sách
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "borrow_receipts")
public class BorrowReceipt extends BaseEntity {

    @Column(name = "receipt_code", unique = true, nullable = false, length = 20)
    private String receiptCode;

    // Quan hệ ManyToOne: Nhiều phiếu mượn thuộc về 1 độc giả
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    // Trạng thái phiếu mượn: BORROWED, RETURNED, OVERDUE
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private BorrowStatus status = BorrowStatus.BORROWED;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Quan hệ OneToMany: 1 phiếu mượn có nhiều chi tiết
    @OneToMany(mappedBy = "borrowReceipt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<BorrowDetail> borrowDetails = new ArrayList<>();

    /**
     * Enum định nghĩa trạng thái của phiếu mượn
     */
    public enum BorrowStatus {
        BORROWED,   // Đang mượn
        RETURNED,   // Đã trả
        OVERDUE     // Quá hạn
    }
}