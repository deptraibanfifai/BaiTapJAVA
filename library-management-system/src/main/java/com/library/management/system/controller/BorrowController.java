package com.library.management.system.controller;

import com.library.management.system.dto.request.BorrowRequest;
import com.library.management.system.dto.response.BorrowReceiptResponse;
import com.library.management.system.entity.Book;
import com.library.management.system.entity.Member;
import com.library.management.system.repository.BookRepository;
import com.library.management.system.repository.MemberRepository;
import com.library.management.system.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/borrows")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @GetMapping
    public String listBorrows(Model model) {
        model.addAttribute("borrows", borrowService.getAllBorrowReceipts());
        model.addAttribute("activeMenu", "borrows");
        model.addAttribute("pageTitle", "Quản lý Mượn / Trả sách");
        model.addAttribute("pageSubtitle", "Danh sách phiếu mượn");
        return "borrows/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("borrow", new BorrowRequest());
        loadMembersAndBooks(model);
        model.addAttribute("activeMenu", "borrows");
        model.addAttribute("pageTitle", "Tạo phiếu mượn mới");
        model.addAttribute("pageSubtitle", "Chọn độc giả và sách cần mượn");
        return "borrows/create";
    }

    @PostMapping("/create")
    public String createBorrow(@Valid @ModelAttribute("borrow") BorrowRequest request,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            loadMembersAndBooks(model); // Load lại dropdown nếu form lỗi
            model.addAttribute("activeMenu", "borrows");
            model.addAttribute("pageTitle", "Tạo phiếu mượn mới");
            return "borrows/create";
        }
        try {
            borrowService.createBorrowReceipt(request);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo phiếu mượn thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/borrows";
    }

    @GetMapping("/return/{id}")
    public String showReturnForm(@PathVariable Long id, Model model) {
        BorrowReceiptResponse receipt = borrowService.getBorrowReceiptById(id);
        model.addAttribute("borrow", receipt);
        model.addAttribute("activeMenu", "borrows");
        model.addAttribute("pageTitle", "Trả sách");
        model.addAttribute("pageSubtitle", "Phiếu mượn: " + receipt.getReceiptCode());
        return "borrows/return";
    }

    @PostMapping("/return/{receiptId}/book/{bookId}")
    public String returnBook(@PathVariable Long receiptId,
                             @PathVariable Long bookId,
                             RedirectAttributes redirectAttributes) {
        try {
            borrowService.returnBook(receiptId, bookId);
            redirectAttributes.addFlashAttribute("successMessage", "Trả sách thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/borrows/return/" + receiptId;
    }

    @PostMapping("/return-all/{id}")
    public String returnAllBooks(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            borrowService.returnAllBooks(id);
            redirectAttributes.addFlashAttribute("successMessage", "Trả tất cả sách thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/borrows";
    }

    private void loadMembersAndBooks(Model model) {
        List<Member> members = memberRepository.findAll();
        List<Book> books = bookRepository.findAll();
        model.addAttribute("members", members);
        model.addAttribute("books", books);
    }
}