package com.library.management.system.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookResponse {
    private Long id;
    private String bookCode;
    private String isbn;
    private String title;
    private Long authorId;
    private String authorName;
    private Long categoryId;
    private String categoryName;
    private String publisher;
    private Integer publishYear;
    private BigDecimal price;
    private Integer quantity;
    private Integer availableQuantity;
    private String coverImage;
    private String description;
    private LocalDateTime createdAt;
}