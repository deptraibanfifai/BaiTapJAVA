package com.library.management.system.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookRequest {
    private Long id;

    @NotBlank(message = "Mã sách không được để trống")
    private String bookCode;

    private String isbn;

    @NotBlank(message = "Tên sách không được để trống")
    private String title;

    @NotNull(message = "Vui lòng chọn tác giả")
    private Long authorId;

    @NotNull(message = "Vui lòng chọn danh mục")
    private Long categoryId;

    private String publisher;

    private Integer publishYear;

    @Min(value = 0, message = "Giá không được âm")
    private BigDecimal price;

    @Min(value = 0, message = "Số lượng không được âm")
    @NotNull(message = "Số lượng không được để trống")
    private Integer quantity;

    private String coverImage;

    private String description;
}