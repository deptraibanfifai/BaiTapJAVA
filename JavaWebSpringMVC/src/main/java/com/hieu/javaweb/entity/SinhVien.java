package com.hieu.javaweb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "SINHVIEN")
@Data
public class SinhVien {
    @Id
    @NotBlank(message = "Số CMND không được để trống")
    private String soCMND;
    private String hoTen;
    private String email;
    private String soDT;
    private String diaChi;
}
