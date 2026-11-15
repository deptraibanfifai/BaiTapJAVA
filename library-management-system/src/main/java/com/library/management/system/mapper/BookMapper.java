package com.library.management.system.mapper;

import com.library.management.system.dto.request.BookRequest;
import com.library.management.system.dto.response.BookResponse;
import com.library.management.system.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(BookRequest request) {
        if (request == null) return null;

        Book book = Book.builder()
                .bookCode(request.getBookCode())
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .publisher(request.getPublisher())
                .publishYear(request.getPublishYear())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .availableQuantity(request.getQuantity())
                .coverImage(request.getCoverImage())
                .description(request.getDescription())
                .build();

        if (request.getId() != null) {
            book.setId(request.getId());
        }

        return book;
    }

    public BookResponse toResponse(Book entity) {
        if (entity == null) return null;

        BookResponse response = new BookResponse();
        response.setId(entity.getId());
        response.setBookCode(entity.getBookCode());
        response.setIsbn(entity.getIsbn());
        response.setTitle(entity.getTitle());
        response.setAuthorId(entity.getAuthor().getId());
        response.setAuthorName(entity.getAuthor().getName());
        response.setCategoryId(entity.getCategory().getId());
        response.setCategoryName(entity.getCategory().getName());
        response.setPublisher(entity.getPublisher());
        response.setPublishYear(entity.getPublishYear());
        response.setPrice(entity.getPrice());
        response.setQuantity(entity.getQuantity());
        response.setAvailableQuantity(entity.getAvailableQuantity());
        response.setCoverImage(entity.getCoverImage());
        response.setDescription(entity.getDescription());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}