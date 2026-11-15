package com.library.management.system.repository;

import com.library.management.system.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByBookCode(String bookCode);
    boolean existsByBookCode(String bookCode);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:categoryId IS NULL OR b.category.id = :categoryId)")
    Page<Book> searchAndFilter(@Param("title") String title,
                               @Param("categoryId") Long categoryId,
                               Pageable pageable);
}