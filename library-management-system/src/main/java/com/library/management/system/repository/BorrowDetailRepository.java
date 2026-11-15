package com.library.management.system.repository;

import com.library.management.system.entity.BorrowDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowDetailRepository extends JpaRepository<BorrowDetail, Long> {
    // Lấy tất cả chi tiết của một phiếu mượn
    List<BorrowDetail> findByBorrowReceiptId(Long borrowReceiptId);

    // Lấy chi tiết đang mượn của một phiếu mượn
    List<BorrowDetail> findByBorrowReceiptIdAndStatus(Long borrowReceiptId, BorrowDetail.BorrowDetailStatus status);
}