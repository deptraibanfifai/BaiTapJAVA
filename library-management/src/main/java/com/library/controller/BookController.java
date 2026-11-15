package com.library.controller;

import com.library.model.User;
import com.library.service.BookService;
import com.library.service.CartService;
import com.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired private BookService bookService;
    @Autowired private CategoryService categoryService;
    @Autowired private CartService cartService;

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model, HttpSession session) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("cartCount", cartService.countCart(user.getId()));
        }
        return "book/detail";
    }
}