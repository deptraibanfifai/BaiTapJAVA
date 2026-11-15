package com.library.management.system.repository;

import com.library.management.system.entity.Fine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
    // Lấy danh sách tiền phạt của một độc giả
    List<Fine> findByMemberId(Long memberId);

    // Lấy danh sách tiền phạt chưa thanh toán
    List<Fine> findByPaymentStatus(Fine.PaymentStatus paymentStatus);

    // Phân trang danh sách tiền phạt
    Page<Fine> findAll(Pageable pageable);

    // Lấy tiền phạt theo phiếu mượn
    List<Fine> findByBorrowReceiptId(Long borrowReceiptId);
}