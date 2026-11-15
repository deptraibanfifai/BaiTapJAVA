package com.library.controller;

import com.library.model.Book;
import com.library.model.User;
import com.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private BookService bookService;
    @Autowired private UserService userService;
    @Autowired private RentalService rentalService;
    @Autowired private CategoryService categoryService;

    // Kiểm tra quyền admin
    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "ADMIN".equals(user.getRole());
    }

    // ===================== DASHBOARD =====================
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {

        if (!isAdmin(session))
            return "redirect:/";

        model.addAttribute(
                "totalBooks",
                bookService.countAll()
        );

        model.addAttribute(
                "totalUsers",
                userService.countAll()
        );

        model.addAttribute(
                "totalRentals",
                rentalService.countAll()
        );

        model.addAttribute(
                "availableBooks",
                bookService.countAvailable()
        );

        model.addAttribute(
                "recentRentals",
                rentalService.findRecent(5)
        );

        // THỐNG KÊ MỚI

        model.addAttribute(
                "rentingBooks",
                rentalService.countRentingBooks()
        );

        model.addAttribute(
                "overdueRentals",
                rentalService.countOverdueRentals()
        );

        model.addAttribute(
                "monthlyRevenue",
                rentalService.revenueCurrentMonth()
        );

        model.addAttribute(
                "revenueData",
                rentalService.getRevenueByMonth()
        );

        return "admin/dashboard";
    }
    @GetMapping("/payments")
    public String payments(Model model) {

        model.addAttribute(
                "monthlyRevenue",
                rentalService.revenueCurrentMonth()
        );

        model.addAttribute(
                "yearlyRevenue",
                rentalService.revenueCurrentYear()
        );

        model.addAttribute(
                "rentals",
                rentalService.findAll()
        );

        return "admin/payments";
    }

    // ===================== BOOKS =====================
    @GetMapping("/books")
    public String listBooks(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/books";
    }

    @GetMapping("/books/add")
    public String addBookPage(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        model.addAttribute("categories", categoryService.findAll());
        return "admin/book-form";
    }

    @PostMapping("/books/save")
    public String saveBook(@RequestParam String title,
                           @RequestParam String author,
                           @RequestParam int categoryId,
                           @RequestParam String description,
                           @RequestParam double price,
                           @RequestParam int quantity,
                           @RequestParam(defaultValue = "default-book.jpg") String image,
                           @RequestParam int publishYear,
                           @RequestParam(defaultValue = "0") int bookId,
                           HttpSession session,
                           RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/";
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategoryId(categoryId);
        book.setDescription(description);
        book.setPrice(price);
        book.setQuantity(quantity);
        book.setImage(image);
        book.setPublishYear(publishYear);

        if (bookId > 0) {
            book.setId(bookId);
            bookService.update(book);
            ra.addFlashAttribute("success", "Cập nhật sách thành công!");
        } else {
            bookService.save(book);
            ra.addFlashAttribute("success", "Thêm sách thành công!");
        }
        return "redirect:/admin/books";
    }

    @GetMapping("/books/edit/{id}")
    public String editBook(@PathVariable int id, Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "admin/book-form";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable int id,
                             HttpSession session,
                             RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/";
        bookService.delete(id);
        ra.addFlashAttribute("success", "Xóa sách thành công!");
        return "redirect:/admin/books";
    }

    // ===================== USERS =====================
    @GetMapping("/users")
    public String listUsers(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/users/lock/{id}")
    public String lockUser(@PathVariable int id,
                           HttpSession session,
                           RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/";
        userService.lockUser(id);
        ra.addFlashAttribute("success", "Đã khóa tài khoản!");
        return "redirect:/admin/users";
    }

    @GetMapping("/users/unlock/{id}")
    public String unlockUser(@PathVariable int id,
                             HttpSession session,
                             RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/";
        userService.unlockUser(id);
        ra.addFlashAttribute("success", "Đã mở khóa tài khoản!");
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id,
                             HttpSession session,
                             RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/";
        userService.delete(id);
        ra.addFlashAttribute("success", "Đã xóa người dùng!");
        return "redirect:/admin/users";
    }

    // ===================== RENTALS =====================
    @GetMapping("/rentals")
    public String listRentals(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        model.addAttribute("rentals", rentalService.findAll());
        return "admin/rentals";
    }

    @GetMapping("/rentals/confirm/{id}")
    public String confirmRental(@PathVariable int id,
                                HttpSession session,
                                RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/";
        rentalService.confirmRental(id);
        ra.addFlashAttribute("success", "Đã xác nhận cho thuê!");
        return "redirect:/admin/rentals";
    }

    @GetMapping("/rentals/return/{id}")
    public String confirmReturn(@PathVariable int id,
                                HttpSession session,
                                RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/";
        rentalService.confirmReturn(id);
        ra.addFlashAttribute("success", "Đã xác nhận trả sách!");
        return "redirect:/admin/rentals";
    }

}