package com.hieu.javaweb.controller;

import com.hieu.javaweb.dto.SinhVienTotNghiepDTO;
import com.hieu.javaweb.entity.Nganh;
import com.hieu.javaweb.entity.Truong;
import com.hieu.javaweb.repository.NganhRepository;
import com.hieu.javaweb.repository.SinhVienRepository;
import com.hieu.javaweb.repository.TotNghiepRepository;
import com.hieu.javaweb.repository.TruongRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sinhvien")
public class SinhVienController {

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private TotNghiepRepository totNghiepRepository;

    @Autowired
    private TruongRepository truongRepository;

    @Autowired
    private NganhRepository nganhRepository;

    @ModelAttribute("dsTruong")
    public List<Truong> getDsTruong() {
        return truongRepository.findAll();
    }

    @ModelAttribute("dsNganh")
    public List<Nganh> getDsNganh() {
        return nganhRepository.findAll();
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("dto", new SinhVienTotNghiepDTO());
        return "sinhvien/add";
    }

    @PostMapping("/add")
    public String saveSinhVien(@Valid @ModelAttribute("dto") SinhVienTotNghiepDTO dto, 
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "sinhvien/add";
        }
        
        // Sync CMND
        dto.getTotNghiep().setSoCMND(dto.getSinhVien().getSoCMND());
        
        sinhVienRepository.save(dto.getSinhVien());
        totNghiepRepository.save(dto.getTotNghiep());
        
        return "redirect:/sinhvien/search-basic";
    }

    @GetMapping("/search-basic")
    public String searchBasic(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("results", sinhVienRepository.findByHoTenContainingIgnoreCase(keyword));
        }
        return "sinhvien/search-basic";
    }

    @GetMapping("/search-job")
    public String searchJob(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("results", totNghiepRepository.searchJobInfo(keyword));
        }
        return "sinhvien/search-job";
    }
}
