package com.library.management.system.service.impl;

import com.library.management.system.dto.request.FineRequest;
import com.library.management.system.dto.response.FineResponse;
import com.library.management.system.entity.BorrowReceipt;
import com.library.management.system.entity.Fine;
import com.library.management.system.mapper.FineMapper;
import com.library.management.system.repository.BorrowReceiptRepository;
import com.library.management.system.repository.FineRepository;
import com.library.management.system.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final BorrowReceiptRepository borrowReceiptRepository;
    private final FineMapper fineMapper;

    // Hằng số tiền phạt mỗi ngày quá hạn (5000 VNĐ)
    private static final BigDecimal FINE_PER_DAY = new BigDecimal("5000");

    @Override
    public List<FineResponse> getAllFines() {
        return fineRepository.findAll().stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FineResponse> getFinesByMemberId(Long memberId) {
        return fineRepository.findByMemberId(memberId).stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FineResponse> getUnpaidFines() {
        return fineRepository.findByPaymentStatus(Fine.PaymentStatus.UNPAID).stream()
                .map(fineMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FineResponse createFine(FineRequest request) {
        BorrowReceipt receipt = borrowReceiptRepository.findById(request.getBorrowReceiptId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mượn với ID: " + request.getBorrowReceiptId()));

        Fine fine = fineMapper.toEntity(request);
        fine.setBorrowReceipt(receipt);
        fine.setMember(receipt.getMember());
        fine.setPaymentStatus(Fine.PaymentStatus.UNPAID);

        fine = fineRepository.save(fine);
        return fineMapper.toResponse(fine);
    }

    @Override
    @Transactional
    public void markAsPaid(Long fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tiền phạt với ID: " + fineId));

        if (fine.getPaymentStatus() == Fine.PaymentStatus.PAID) {
            throw new RuntimeException("Tiền phạt này đã được thanh toán!");
        }

        fine.setPaymentStatus(Fine.PaymentStatus.PAID);
        fine.setPaymentDate(LocalDate.now());
        fineRepository.save(fine);
    }

    @Override
    @Transactional
    public void createFineForOverdue(Long borrowReceiptId, int overdueDays) {
        if (overdueDays <= 0) {
            return; // Không quá hạn thì không phạt
        }

        BorrowReceipt receipt = borrowReceiptRepository.findById(borrowReceiptId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mượn với ID: " + borrowReceiptId));

        BigDecimal amount = FINE_PER_DAY.multiply(BigDecimal.valueOf(overdueDays));

        Fine fine = Fine.builder()
                .borrowReceipt(receipt)
                .member(receipt.getMember())
                .reason("Trả sách quá hạn " + overdueDays + " ngày")
                .amount(amount)
                .paymentStatus(Fine.PaymentStatus.UNPAID)
                .build();

        fineRepository.save(fine);
    }
}