package com.library.management.system.controller;

import com.library.management.system.dto.request.MemberRequest;
import com.library.management.system.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String listMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("activeMenu", "members");
        model.addAttribute("pageTitle", "Quản lý Độc giả");
        model.addAttribute("pageSubtitle", "Danh sách độc giả");
        return "members/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("member", new MemberRequest());
        model.addAttribute("activeMenu", "members");
        model.addAttribute("pageTitle", "Thêm độc giả mới");
        return "members/form";
    }

    @PostMapping("/create")
    public String createMember(@Valid @ModelAttribute("member") MemberRequest request,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "members/form";
        }
        try {
            memberService.createMember(request);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm độc giả thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("member", memberService.getMemberById(id));
        model.addAttribute("isEdit", true);
        model.addAttribute("activeMenu", "members");
        model.addAttribute("pageTitle", "Chỉnh sửa độc giả");
        return "members/form";
    }

    @PostMapping("/update/{id}")
    public String updateMember(@PathVariable Long id,
                               @Valid @ModelAttribute("member") MemberRequest request,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "members/form";
        }
        try {
            memberService.updateMember(id, request);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật độc giả thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/members";
    }

    @PostMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            memberService.deleteMember(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa độc giả thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/members";
    }
}