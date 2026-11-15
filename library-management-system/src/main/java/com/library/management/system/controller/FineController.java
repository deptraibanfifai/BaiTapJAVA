package com.library.management.system.controller;

import com.library.management.system.dto.request.FineRequest;
import com.library.management.system.service.FineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/fines")
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;

    @GetMapping
    public String listFines(Model model) {
        model.addAttribute("fines", fineService.getAllFines());
        model.addAttribute("activeMenu", "fines");
        model.addAttribute("pageTitle", "Quản lý Tiền phạt");
        model.addAttribute("pageSubtitle", "Danh sách các khoản tiền phạt");
        return "fines/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("fine", new FineRequest());
        model.addAttribute("activeMenu", "fines");
        model.addAttribute("pageTitle", "Tạo tiền phạt mới");
        model.addAttribute("pageSubtitle", "Nhập thông tin tiền phạt");
        return "fines/form";
    }

    @PostMapping("/create")
    public String createFine(@Valid @ModelAttribute("fine") FineRequest request,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "fines/form";
        }
        try {
            fineService.createFine(request);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo tiền phạt thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/fines";
    }

    @PostMapping("/pay/{id}")
    public String markAsPaid(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            fineService.markAsPaid(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã đánh dấu thanh toán thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/fines";
    }
}