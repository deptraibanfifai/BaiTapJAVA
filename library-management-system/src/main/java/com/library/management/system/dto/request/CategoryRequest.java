package com.library.management.system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO dùng để nhận dữ liệu từ Client khi Thêm/Sửa danh mục
 */
@Data
public class CategoryRequest {
    private Long id;

    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;

    private String description;
}