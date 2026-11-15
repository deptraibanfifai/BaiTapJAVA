package com.library.management.system.service;

import com.library.management.system.dto.request.CategoryRequest;
import com.library.management.system.dto.response.CategoryResponse;

import java.util.List;

/**
 * Interface định nghĩa các nghiệp vụ quản lý danh mục
 */
public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
}