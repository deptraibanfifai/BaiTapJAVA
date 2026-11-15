package com.library.management.system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberRequest {
    private Long id;

    @NotBlank(message = "Mã độc giả không được để trống")
    private String memberCode;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @Email(message = "Email không hợp lệ")
    private String email;

    private String phone;

    private String address;

    @PastOrPresent(message = "Ngày đăng ký không được là tương lai")
    private LocalDate registrationDate;
}