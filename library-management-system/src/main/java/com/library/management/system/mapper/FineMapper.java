package com.library.management.system.mapper;

import com.library.management.system.dto.request.FineRequest;
import com.library.management.system.dto.response.FineResponse;
import com.library.management.system.entity.Fine;
import org.springframework.stereotype.Component;

@Component
public class FineMapper {

    public Fine toEntity(FineRequest request) {
        if (request == null) return null;

        Fine fine = Fine.builder()
                .reason(request.getReason())
                .amount(request.getAmount())
                .build();

        if (request.getId() != null) {
            fine.setId(request.getId());
        }

        return fine;
    }

    public FineResponse toResponse(Fine entity) {
        if (entity == null) return null;

        FineResponse response = new FineResponse();
        response.setId(entity.getId());
        response.setBorrowReceiptId(entity.getBorrowReceipt().getId());
        response.setReceiptCode(entity.getBorrowReceipt().getReceiptCode());
        response.setMemberId(entity.getMember().getId());
        response.setMemberName(entity.getMember().getFullName());
        response.setReason(entity.getReason());
        response.setAmount(entity.getAmount());
        response.setPaymentStatus(entity.getPaymentStatus().name());
        response.setPaymentDate(entity.getPaymentDate());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}