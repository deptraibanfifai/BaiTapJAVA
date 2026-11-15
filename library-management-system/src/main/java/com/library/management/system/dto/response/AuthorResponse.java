package com.library.management.system.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuthorResponse {
    private Long id;
    private String name;
    private String nationality;
    private String biography;
    private LocalDateTime createdAt;
}