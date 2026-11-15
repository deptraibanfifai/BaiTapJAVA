package com.hieu.javaweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "TOT_NGHIEP")
@Data
@IdClass(TotNghiepId.class)
public class TotNghiep {
    @Id
    @NotBlank(message = "Số CMND không được để trống")
    private String soCMND;

    @Id
    @NotBlank(message = "Mã trường không được để trống")
    private String maTruong;

    @Id
    @NotBlank(message = "Mã ngành không được để trống")
    private String maNganh;

    private String heDT;
    private String loaiTN;

    @NotNull(message = "Ngày tốt nghiệp không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayTN;

    // Optional fields for Requirement 3.2
    private String maNganhCongTy;
    private String tenCongTy;
    private String thoiGianLamViec;
}
