package com.library.management.system.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO dùng để hiển thị thông tin danh mục ra giao diện
 */
@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}