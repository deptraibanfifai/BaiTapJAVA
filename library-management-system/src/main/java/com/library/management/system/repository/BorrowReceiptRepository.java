package com.library.management.system.repository;

import com.library.management.system.entity.BorrowReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowReceiptRepository extends JpaRepository<BorrowReceipt, Long> {
    Optional<BorrowReceipt> findByReceiptCode(String receiptCode);
    boolean existsByReceiptCode(String receiptCode);

    // Lấy danh sách phiếu mượn theo độc giả
    List<BorrowReceipt> findByMemberId(Long memberId);

    // Lấy danh sách phiếu mượn đang mượn (chưa trả)
    List<BorrowReceipt> findByStatus(BorrowReceipt.BorrowStatus status);

    // Lấy danh sách phiếu mượn quá hạn (due_date < hôm nay và status = BORROWED)
    @Query("SELECT br FROM BorrowReceipt br WHERE br.dueDate < :today AND br.status = 'BORROWED'")
    List<BorrowReceipt> findOverdueReceipts(@Param("today") LocalDate today);

    // Phân trang danh sách phiếu mượn
    Page<BorrowReceipt> findAll(Pageable pageable);

    // Tìm kiếm theo mã phiếu hoặc tên độc giả
    @Query("SELECT br FROM BorrowReceipt br WHERE " +
            "(:keyword IS NULL OR LOWER(br.receiptCode) LIKE LOWER(CONCAT('%', :keyword, '%'))) OR " +
            "(:keyword IS NULL OR LOWER(br.member.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<BorrowReceipt> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}