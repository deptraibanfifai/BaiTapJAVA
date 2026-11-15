package com.hieu.javaweb.dto;

import com.hieu.javaweb.entity.SinhVien;
import com.hieu.javaweb.entity.TotNghiep;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class SinhVienTotNghiepDTO {
    @Valid
    private SinhVien sinhVien;
    @Valid
    private TotNghiep totNghiep;

    public SinhVienTotNghiepDTO() {
        this.sinhVien = new SinhVien();
        this.totNghiep = new TotNghiep();
    }
}
