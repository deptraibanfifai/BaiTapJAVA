package com.library.management.system.dto.response;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class DashboardStatsResponse {
    private long totalTitles;           // Tổng số đầu sách
    private long totalCopies;           // Tổng số bản sách
    private long booksBorrowed;         // Số sách đang mượn
    private long booksAvailable;        // Số sách còn trong thư viện
    private long totalMembers;          // Tổng số độc giả
    private long borrowsToday;          // Phiếu mượn hôm nay
    private long returnsToday;          // Phiếu trả hôm nay
    private List<BorrowReceiptResponse> overdueReceipts; // Sách quá hạn

    // Dữ liệu cho biểu đồ
    private Map<String, Long> borrowsByMonth;   // Lượt mượn theo tháng
    private Map<String, Long> booksByCategory;  // Số sách theo thể loại
}