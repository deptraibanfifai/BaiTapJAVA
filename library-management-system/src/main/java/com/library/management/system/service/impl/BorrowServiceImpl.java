package com.library.management.system.service.impl;

import com.library.management.system.dto.request.BorrowRequest;
import com.library.management.system.dto.response.BorrowReceiptResponse;
import com.library.management.system.entity.*;
import com.library.management.system.mapper.BorrowMapper;
import com.library.management.system.repository.*;
import com.library.management.system.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BorrowReceiptRepository borrowReceiptRepository;
    private final BorrowDetailRepository borrowDetailRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BorrowMapper borrowMapper;

    @Override
    public List<BorrowReceiptResponse> getAllBorrowReceipts() {
        return borrowReceiptRepository.findAll().stream()
                .map(borrowMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BorrowReceiptResponse getBorrowReceiptById(Long id) {
        BorrowReceipt receipt = borrowReceiptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mượn với ID: " + id));
        return borrowMapper.toResponse(receipt);
    }

    @Override
    @Transactional
    public BorrowReceiptResponse createBorrowReceipt(BorrowRequest request) {
        // Kiểm tra độc giả
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy độc giả với ID: " + request.getMemberId()));

        // Tạo phiếu mượn
        BorrowReceipt receipt = borrowMapper.toEntity(request);
        receipt.setMember(member);
        receipt.setReceiptCode(generateReceiptCode());
        receipt.setStatus(BorrowReceipt.BorrowStatus.BORROWED);

        // Lưu phiếu mượn trước để có ID
        receipt = borrowReceiptRepository.save(receipt);

        // Tạo chi tiết mượn và cập nhật số lượng sách
        for (BorrowRequest.BookItem bookItem : request.getBooks()) {
            Book book = bookRepository.findById(bookItem.getBookId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với ID: " + bookItem.getBookId()));

            // Kiểm tra số lượng
            if (book.getAvailableQuantity() < bookItem.getQuantity()) {
                throw new RuntimeException("Sách '" + book.getTitle() + "' không đủ số lượng! Còn lại: " + book.getAvailableQuantity());
            }

            // Tạo chi tiết
            BorrowDetail detail = BorrowDetail.builder()
                    .borrowReceipt(receipt)
                    .book(book)
                    .quantity(bookItem.getQuantity())
                    .status(BorrowDetail.BorrowDetailStatus.BORROWED)
                    .build();
            borrowDetailRepository.save(detail);

            // Giảm số lượng sách
            book.setAvailableQuantity(book.getAvailableQuantity() - bookItem.getQuantity());
            bookRepository.save(book);
        }

        // Load lại receipt với details
        receipt = borrowReceiptRepository.findById(receipt.getId()).orElseThrow();
        return borrowMapper.toResponse(receipt);
    }

    @Override
    @Transactional
    public void returnBook(Long receiptId, Long bookId) {
        // Tìm phiếu mượn
        BorrowReceipt receipt = borrowReceiptRepository.findById(receiptId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mượn với ID: " + receiptId));

        // Tìm chi tiết mượn
        BorrowDetail detail = borrowDetailRepository.findByBorrowReceiptIdAndStatus(receiptId, BorrowDetail.BorrowDetailStatus.BORROWED)
                .stream()
                .filter(d -> d.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách này trong phiếu mượn hoặc đã trả rồi!"));

        // Cập nhật chi tiết
        detail.setStatus(BorrowDetail.BorrowDetailStatus.RETURNED);
        detail.setReturnDate(LocalDate.now());
        borrowDetailRepository.save(detail);

        // Tăng số lượng sách
        Book book = detail.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + detail.getQuantity());
        bookRepository.save(book);

        // Kiểm tra nếu tất cả sách đã trả
        List<BorrowDetail> remainingDetails = borrowDetailRepository.findByBorrowReceiptIdAndStatus(receiptId, BorrowDetail.BorrowDetailStatus.BORROWED);
        if (remainingDetails.isEmpty()) {
            receipt.setStatus(BorrowReceipt.BorrowStatus.RETURNED);
            receipt.setReturnDate(LocalDate.now());
            borrowReceiptRepository.save(receipt);
        }
    }

    @Override
    @Transactional
    public void returnAllBooks(Long receiptId) {
        BorrowReceipt receipt = borrowReceiptRepository.findById(receiptId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mượn với ID: " + receiptId));

        List<BorrowDetail> details = borrowDetailRepository.findByBorrowReceiptIdAndStatus(receiptId, BorrowDetail.BorrowDetailStatus.BORROWED);

        for (BorrowDetail detail : details) {
            detail.setStatus(BorrowDetail.BorrowDetailStatus.RETURNED);
            detail.setReturnDate(LocalDate.now());
            borrowDetailRepository.save(detail);

            Book book = detail.getBook();
            book.setAvailableQuantity(book.getAvailableQuantity() + detail.getQuantity());
            bookRepository.save(book);
        }

        receipt.setStatus(BorrowReceipt.BorrowStatus.RETURNED);
        receipt.setReturnDate(LocalDate.now());
        borrowReceiptRepository.save(receipt);
    }

    private String generateReceiptCode() {
        long count = borrowReceiptRepository.count();
        return "PM" + String.format("%05d", count + 1);
    }
}