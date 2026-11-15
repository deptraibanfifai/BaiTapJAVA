package com.library.management.system.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FineResponse {
    private Long id;
    private Long borrowReceiptId;
    private String receiptCode;
    private Long memberId;
    private String memberName;
    private String reason;
    private BigDecimal amount;
    private String paymentStatus;
    private LocalDate paymentDate;
    private LocalDateTime createdAt;
}