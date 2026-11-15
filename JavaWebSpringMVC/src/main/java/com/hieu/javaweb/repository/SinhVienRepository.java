package com.hieu.javaweb.repository;

import com.hieu.javaweb.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    List<SinhVien> findByHoTenContainingIgnoreCase(String hoTen);
}
