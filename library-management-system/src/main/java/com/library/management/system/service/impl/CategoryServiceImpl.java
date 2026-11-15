package com.library.management.system.service.impl;

import com.library.management.system.dto.request.CategoryRequest;
import com.library.management.system.dto.response.CategoryResponse;
import com.library.management.system.entity.Category;
import com.library.management.system.mapper.CategoryMapper;
import com.library.management.system.repository.CategoryRepository;
import com.library.management.system.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation của CategoryService, chứa toàn bộ Business Logic
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        // Kiểm tra trùng tên
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Danh mục '" + request.getName() + "' đã tồn tại!");
        }

        Category category = categoryMapper.toEntity(request);
        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + id));

        // Kiểm tra trùng tên (nếu đổi tên)
        if (!existingCategory.getName().equals(request.getName()) &&
                categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Danh mục '" + request.getName() + "' đã tồn tại!");
        }

        existingCategory.setName(request.getName());
        existingCategory.setDescription(request.getDescription());

        existingCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toResponse(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy danh mục với ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}