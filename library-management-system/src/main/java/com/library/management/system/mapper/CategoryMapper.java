package com.library.management.system.mapper;

import com.library.management.system.dto.request.CategoryRequest;
import com.library.management.system.dto.response.CategoryResponse;
import com.library.management.system.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        if (request == null) return null;

        // Sửa lại: Không dùng builder cho id, set riêng sau
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        // Set id riêng nếu có (dành cho trường hợp update)
        if (request.getId() != null) {
            category.setId(request.getId());
        }

        return category;
    }

    public CategoryResponse toResponse(Category entity) {
        if (entity == null) return null;

        CategoryResponse response = new CategoryResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}