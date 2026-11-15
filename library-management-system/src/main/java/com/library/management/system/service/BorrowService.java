package com.library.management.system.service;

import com.library.management.system.dto.request.BorrowRequest;
import com.library.management.system.dto.response.BorrowReceiptResponse;

import java.util.List;

public interface BorrowService {
    List<BorrowReceiptResponse> getAllBorrowReceipts();
    BorrowReceiptResponse getBorrowReceiptById(Long id);
    BorrowReceiptResponse createBorrowReceipt(BorrowRequest request);
    void returnBook(Long receiptId, Long bookId);
    void returnAllBooks(Long receiptId);
}