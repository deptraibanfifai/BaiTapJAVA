package com.library.management.system.mapper;

import com.library.management.system.dto.request.BorrowRequest;
import com.library.management.system.dto.response.BorrowReceiptResponse;
import com.library.management.system.entity.BorrowDetail;
import com.library.management.system.entity.BorrowReceipt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BorrowMapper {

    public BorrowReceipt toEntity(BorrowRequest request) {
        if (request == null) return null;

        BorrowReceipt receipt = BorrowReceipt.builder()
                .borrowDate(request.getBorrowDate())
                .dueDate(request.getDueDate())
                .notes(request.getNotes())
                .build();

        if (request.getId() != null) {
            receipt.setId(request.getId());
        }

        return receipt;
    }

    public BorrowReceiptResponse toResponse(BorrowReceipt entity) {
        if (entity == null) return null;

        BorrowReceiptResponse response = new BorrowReceiptResponse();
        response.setId(entity.getId());
        response.setReceiptCode(entity.getReceiptCode());
        response.setMemberId(entity.getMember().getId());
        response.setMemberName(entity.getMember().getFullName());
        response.setBorrowDate(entity.getBorrowDate());
        response.setDueDate(entity.getDueDate());
        response.setReturnDate(entity.getReturnDate());
        response.setStatus(entity.getStatus().name());
        response.setNotes(entity.getNotes());
        response.setCreatedAt(entity.getCreatedAt());

        // Map chi tiết
        if (entity.getBorrowDetails() != null) {
            List<BorrowReceiptResponse.BorrowDetailResponse> details = entity.getBorrowDetails().stream()
                    .map(this::toDetailResponse)
                    .collect(Collectors.toList());
            response.setDetails(details);
        }

        return response;
    }

    private BorrowReceiptResponse.BorrowDetailResponse toDetailResponse(BorrowDetail detail) {
        BorrowReceiptResponse.BorrowDetailResponse response = new BorrowReceiptResponse.BorrowDetailResponse();
        response.setId(detail.getId());
        response.setBookId(detail.getBook().getId());
        response.setBookTitle(detail.getBook().getTitle());
        response.setQuantity(detail.getQuantity());
        response.setReturnDate(detail.getReturnDate());
        response.setStatus(detail.getStatus().name());
        return response;
    }
}