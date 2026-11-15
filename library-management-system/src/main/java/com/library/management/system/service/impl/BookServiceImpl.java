package com.library.management.system.service.impl;

import com.library.management.system.dto.request.BookRequest;
import com.library.management.system.dto.response.BookResponse;
import com.library.management.system.entity.Author;
import com.library.management.system.entity.Book;
import com.library.management.system.entity.Category;
import com.library.management.system.mapper.BookMapper;
import com.library.management.system.repository.AuthorRepository;
import com.library.management.system.repository.BookRepository;
import com.library.management.system.repository.CategoryRepository;
import com.library.management.system.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookResponse> getBooksByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable).map(bookMapper::toResponse);
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với ID: " + id));
        return bookMapper.toResponse(book);
    }

    @Override
    public BookResponse createBook(BookRequest request) {
        if (bookRepository.existsByBookCode(request.getBookCode())) {
            throw new RuntimeException("Mã sách '" + request.getBookCode() + "' đã tồn tại!");
        }

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả với ID: " + request.getAuthorId()));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + request.getCategoryId()));

        Book book = bookMapper.toEntity(request);
        book.setAuthor(author);
        book.setCategory(category);
        book.setAvailableQuantity(request.getQuantity());

        book = bookRepository.save(book);
        return bookMapper.toResponse(book);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với ID: " + id));

        if (!existingBook.getBookCode().equals(request.getBookCode()) &&
                bookRepository.existsByBookCode(request.getBookCode())) {
            throw new RuntimeException("Mã sách '" + request.getBookCode() + "' đã tồn tại!");
        }

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả với ID: " + request.getAuthorId()));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + request.getCategoryId()));

        existingBook.setBookCode(request.getBookCode());
        existingBook.setIsbn(request.getIsbn());
        existingBook.setTitle(request.getTitle());
        existingBook.setAuthor(author);
        existingBook.setCategory(category);
        existingBook.setPublisher(request.getPublisher());
        existingBook.setPublishYear(request.getPublishYear());
        existingBook.setPrice(request.getPrice());

        // Tính lại availableQuantity dựa trên sự thay đổi của quantity
        int quantityDiff = request.getQuantity() - existingBook.getQuantity();
        existingBook.setQuantity(request.getQuantity());
        existingBook.setAvailableQuantity(existingBook.getAvailableQuantity() + quantityDiff);

        existingBook.setCoverImage(request.getCoverImage());
        existingBook.setDescription(request.getDescription());

        existingBook = bookRepository.save(existingBook);
        return bookMapper.toResponse(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sách với ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public void updateAvailableQuantity(Long bookId, int quantity, boolean isIncrease) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với ID: " + bookId));

        if (isIncrease) {
            book.setAvailableQuantity(book.getAvailableQuantity() + quantity);
        } else {
            if (book.getAvailableQuantity() < quantity) {
                throw new RuntimeException("Số lượng sách không đủ!");
            }
            book.setAvailableQuantity(book.getAvailableQuantity() - quantity);
        }
        bookRepository.save(book);
    }
}