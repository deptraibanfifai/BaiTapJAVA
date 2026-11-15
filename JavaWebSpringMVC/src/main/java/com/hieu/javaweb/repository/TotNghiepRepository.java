package com.hieu.javaweb.repository;

import com.hieu.javaweb.entity.TotNghiep;
import com.hieu.javaweb.entity.TotNghiepId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TotNghiepRepository extends JpaRepository<TotNghiep, TotNghiepId> {
    
    @Query("SELECT sv.soCMND as soCMND, sv.hoTen as hoTen, tn.maNganh as maNganh, " +
           "tn.maTruong as maTruong, tn.maNganhCongTy as maNganhCongTy, " +
           "tn.tenCongTy as tenCongTy, tn.thoiGianLamViec as thoiGianLamViec " +
           "FROM SinhVien sv JOIN TotNghiep tn ON sv.soCMND = tn.soCMND " +
           "WHERE LOWER(sv.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Map<String, Object>> searchJobInfo(@Param("keyword") String keyword);
}
