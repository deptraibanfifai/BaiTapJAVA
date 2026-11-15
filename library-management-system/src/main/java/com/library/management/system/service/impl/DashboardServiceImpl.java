package com.library.management.system.service.impl;

import com.library.management.system.dto.response.BorrowReceiptResponse;
import com.library.management.system.dto.response.DashboardStatsResponse;
import com.library.management.system.entity.Book;
import com.library.management.system.entity.BorrowReceipt;
import com.library.management.system.mapper.BorrowMapper;
import com.library.management.system.repository.*;
import com.library.management.system.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowReceiptRepository borrowReceiptRepository;
    private final BorrowMapper borrowMapper;

    @Override
    public DashboardStatsResponse getDashboardStats() {
        DashboardStatsResponse stats = new DashboardStatsResponse();

        // Thống kê sách
        List<Book> allBooks = bookRepository.findAll();
        stats.setTotalTitles(allBooks.size());
        stats.setTotalCopies(allBooks.stream().mapToInt(Book::getQuantity).sum());
        stats.setBooksBorrowed(allBooks.stream().mapToInt(b -> b.getQuantity() - b.getAvailableQuantity()).sum());
        stats.setBooksAvailable(allBooks.stream().mapToInt(Book::getAvailableQuantity).sum());

        // Thống kê độc giả
        stats.setTotalMembers(memberRepository.count());

        // Thống kê hôm nay
        LocalDate today = LocalDate.now();
        List<BorrowReceipt> allReceipts = borrowReceiptRepository.findAll();
        stats.setBorrowsToday(allReceipts.stream()
                .filter(r -> r.getBorrowDate().equals(today))
                .count());
        stats.setReturnsToday(allReceipts.stream()
                .filter(r -> r.getReturnDate() != null && r.getReturnDate().equals(today))
                .count());

        // Danh sách quá hạn
        List<BorrowReceipt> overdueReceipts = borrowReceiptRepository.findOverdueReceipts(today);
        stats.setOverdueReceipts(overdueReceipts.stream()
                .map(borrowMapper::toResponse)
                .collect(Collectors.toList()));

        // Biểu đồ: Lượt mượn theo tháng (12 tháng gần nhất)
        stats.setBorrowsByMonth(calculateBorrowsByMonth(allReceipts));

        // Biểu đồ: Số sách theo thể loại
        stats.setBooksByCategory(calculateBooksByCategory(allBooks));

        return stats;
    }

    private Map<String, Long> calculateBorrowsByMonth(List<BorrowReceipt> receipts) {
        Map<String, Long> result = new LinkedHashMap<>();
        YearMonth current = YearMonth.now();

        // Khởi tạo 12 tháng với giá trị 0
        for (int i = 11; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            String key = "Tháng " + ym.getMonthValue() + "/" + ym.getYear();
            result.put(key, 0L);
        }

        // Đếm số lượt mượn
        for (BorrowReceipt receipt : receipts) {
            YearMonth ym = YearMonth.from(receipt.getBorrowDate());
            String key = "Tháng " + ym.getMonthValue() + "/" + ym.getYear();
            if (result.containsKey(key)) {
                result.put(key, result.get(key) + 1);
            }
        }

        return result;
    }

    private Map<String, Long> calculateBooksByCategory(List<Book> books) {
        return books.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getCategory().getName(),
                        Collectors.counting()
                ));
    }
}