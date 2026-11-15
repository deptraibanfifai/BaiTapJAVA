package com.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String role;      // USER, ADMIN
    private String status;    // ACTIVE, LOCKED
    private String createdAt;
}