package com.library.management.system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequest {
    private Long id;

    @NotBlank(message = "Tên tác giả không được để trống")
    private String name;

    private String nationality;

    private String biography;
}