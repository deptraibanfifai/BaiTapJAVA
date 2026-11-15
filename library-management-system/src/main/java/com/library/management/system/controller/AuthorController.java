package com.library.management.system.controller;

import com.library.management.system.dto.request.AuthorRequest;
import com.library.management.system.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("activeMenu", "authors");
        model.addAttribute("pageTitle", "Quản lý Tác giả");
        model.addAttribute("pageSubtitle", "Danh sách tác giả");
        return "authors/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new AuthorRequest());
        model.addAttribute("activeMenu", "authors");
        model.addAttribute("pageTitle", "Thêm tác giả mới");
        return "authors/form";
    }

    @PostMapping("/create")
    public String createAuthor(@Valid @ModelAttribute("author") AuthorRequest request,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "authors/form";
        }
        try {
            authorService.createAuthor(request);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm tác giả thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.getAuthorById(id));
        model.addAttribute("isEdit", true);
        model.addAttribute("activeMenu", "authors");
        model.addAttribute("pageTitle", "Chỉnh sửa tác giả");
        return "authors/form";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id,
                               @Valid @ModelAttribute("author") AuthorRequest request,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "authors/form";
        }
        try {
            authorService.updateAuthor(id, request);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật tác giả thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/authors";
    }

    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            authorService.deleteAuthor(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa tác giả thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/authors";
    }
}