package com.library.management.system.service;

import com.library.management.system.dto.request.BookRequest;
import com.library.management.system.dto.response.BookResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    List<BookResponse> getAllBooks();
    Page<BookResponse> getBooksByPage(int page, int size);
    BookResponse getBookById(Long id);
    BookResponse createBook(BookRequest request);
    BookResponse updateBook(Long id, BookRequest request);
    void deleteBook(Long id);
    void updateAvailableQuantity(Long bookId, int quantity, boolean isIncrease);
}