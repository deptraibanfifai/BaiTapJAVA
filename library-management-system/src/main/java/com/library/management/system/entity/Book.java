package com.library.management.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(name = "book_code", unique = true, nullable = false, length = 20)
    private String bookCode;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(nullable = false, length = 255)
    private String title;

    // Quan hệ ManyToOne với Author (Nhiều sách thuộc 1 tác giả)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    // Quan hệ ManyToOne với Category (Nhiều sách thuộc 1 danh mục)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(length = 100)
    private String publisher; // Lưu dưới dạng String theo yêu cầu

    @Column(name = "publish_year")
    private Integer publishYear;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 0;

    @Column(name = "available_quantity", nullable = false)
    @Builder.Default
    private Integer availableQuantity = 0;

    @Column(name = "cover_image", length = 255)
    private String coverImage;

    @Column(columnDefinition = "TEXT")
    private String description;
}