package com.library.management.system.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberResponse {
    private Long id;
    private String memberCode;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private LocalDate registrationDate;
    private LocalDateTime createdAt;
}