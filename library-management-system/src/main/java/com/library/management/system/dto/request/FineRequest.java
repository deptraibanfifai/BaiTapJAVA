package com.library.management.system.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FineRequest {
    private Long id;

    @NotNull(message = "Vui lòng chọn phiếu mượn")
    private Long borrowReceiptId;

    @NotBlank(message = "Lý do không được để trống")
    private String reason;

    @NotNull(message = "Số tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Số tiền phải lớn hơn 0")
    private BigDecimal amount;
}