package com.library.management.system.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BorrowRequest {
    private Long id;

    @NotNull(message = "Vui lòng chọn độc giả")
    private Long memberId;

    @NotEmpty(message = "Vui lòng chọn ít nhất một sách")
    private List<BookItem> books;

    @NotNull(message = "Ngày mượn không được để trống")
    private LocalDate borrowDate;

    @NotNull(message = "Ngày phải trả không được để trống")
    @Future(message = "Ngày phải trả phải là ngày trong tương lai")
    private LocalDate dueDate;

    private String notes;

    @Data
    public static class BookItem {
        @NotNull(message = "Vui lòng chọn sách")
        private Long bookId;

        @Min(value = 1, message = "Số lượng phải lớn hơn 0")
        private Integer quantity = 1;
    }
}