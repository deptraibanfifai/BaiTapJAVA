package com.library.controller;

import com.library.model.User;
import com.library.service.BookService;
import com.library.service.CartService;
import com.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @Autowired private BookService bookService;
    @Autowired private CategoryService categoryService;
    @Autowired private CartService cartService;

    // Tự động thêm categories và cartCount vào Model của mọi request trong Controller này
    @ModelAttribute
    public void addCommonAttributes(Model model, HttpSession session) {
        model.addAttribute("categories", categoryService.findAll());
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("cartCount", cartService.countCart(user.getId()));
        } else {
            model.addAttribute("cartCount", 0);
        }
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("newestBooks",
                bookService.findNewest(4));

        model.addAttribute("allBooks",
                bookService.findAll());

        return "home";
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String keyword,
                         @RequestParam(defaultValue = "0") int categoryId,
                         Model model) {
        if (categoryId > 0) {
            model.addAttribute("books", bookService.findByCategory(categoryId));
        } else if (!keyword.isEmpty()) {
            model.addAttribute("books", bookService.search(keyword));
        } else {
            model.addAttribute("books", bookService.findAll());
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        return "book/search";
    }
}