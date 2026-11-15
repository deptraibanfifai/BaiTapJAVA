package com.library.management.system.service;

import com.library.management.system.dto.request.FineRequest;
import com.library.management.system.dto.response.FineResponse;

import java.util.List;

public interface FineService {
    List<FineResponse> getAllFines();
    List<FineResponse> getFinesByMemberId(Long memberId);
    List<FineResponse> getUnpaidFines();
    FineResponse createFine(FineRequest request);
    void markAsPaid(Long fineId);
    void createFineForOverdue(Long borrowReceiptId, int overdueDays);
}