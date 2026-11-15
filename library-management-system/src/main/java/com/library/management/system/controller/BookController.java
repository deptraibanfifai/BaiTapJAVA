package com.library.management.system.controller;

import com.library.management.system.dto.request.BookRequest;
import com.library.management.system.dto.response.BookResponse;
import com.library.management.system.entity.Author;
import com.library.management.system.entity.Category;
import com.library.management.system.repository.AuthorRepository;
import com.library.management.system.repository.CategoryRepository;
import com.library.management.system.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String listBooks(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        Page<BookResponse> bookPage = bookService.getBooksByPage(page, size);
        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("totalElements", bookPage.getTotalElements());
        model.addAttribute("activeMenu", "books");
        model.addAttribute("pageTitle", "Quản lý Sách");
        model.addAttribute("pageSubtitle", "Danh sách tất cả sách trong thư viện");
        return "books/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new BookRequest());
        loadAuthorsAndCategories(model);
        model.addAttribute("activeMenu", "books");
        model.addAttribute("pageTitle", "Thêm sách mới");
        model.addAttribute("pageSubtitle", "Nhập thông tin sách");
        return "books/form";
    }

    @PostMapping("/create")
    public String createBook(@Valid @ModelAttribute("book") BookRequest request,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            loadAuthorsAndCategories(redirectAttributes);
            return "books/form";
        }
        try {
            bookService.createBook(request);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sách thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("isEdit", true);
        loadAuthorsAndCategories(model);
        model.addAttribute("activeMenu", "books");
        model.addAttribute("pageTitle", "Chỉnh sửa sách");
        model.addAttribute("pageSubtitle", "Cập nhật thông tin sách");
        return "books/form";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") BookRequest request,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            loadAuthorsAndCategories(redirectAttributes);
            return "books/form";
        }
        try {
            bookService.updateBook(id, request);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sách thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa sách thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/books";
    }

    private void loadAuthorsAndCategories(Model model) {
        List<Author> authors = authorRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
    }
}