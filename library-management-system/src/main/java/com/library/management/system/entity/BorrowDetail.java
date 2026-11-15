package com.library.management.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity đại diện cho chi tiết mượn sách trong một phiếu mượn
 * Mỗi chi tiết ghi nhận một cuốn sách được mượn với số lượng cụ thể
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "borrow_details")
public class BorrowDetail extends BaseEntity {

    // Quan hệ ManyToOne: Nhiều chi tiết thuộc về 1 phiếu mượn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_receipt_id", nullable = false)
    private BorrowReceipt borrowReceipt;

    // Quan hệ ManyToOne: Nhiều chi tiết có thể mượn cùng 1 cuốn sách (ở các thời điểm khác nhau)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 1;

    @Column(name = "return_date")
    private LocalDate returnDate;

    // Trạng thái chi tiết: BORROWED, RETURNED
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private BorrowDetailStatus status = BorrowDetailStatus.BORROWED;

    /**
     * Enum định nghĩa trạng thái của chi tiết mượn
     */
    public enum BorrowDetailStatus {
        BORROWED,   // Đang mượn
        RETURNED    // Đã trả
    }
}