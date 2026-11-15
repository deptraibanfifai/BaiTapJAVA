package com.library.management.system.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BorrowReceiptResponse {
    private Long id;
    private String receiptCode;
    private Long memberId;
    private String memberName;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;
    private String notes;
    private LocalDateTime createdAt;
    private List<BorrowDetailResponse> details;

    @Data
    public static class BorrowDetailResponse {
        private Long id;
        private Long bookId;
        private String bookTitle;
        private Integer quantity;
        private LocalDate returnDate;
        private String status;
    }
}