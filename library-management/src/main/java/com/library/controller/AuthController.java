package com.library.controller;

import com.library.model.User;
import com.library.service.CategoryService;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired private UserService userService;
    @Autowired private CategoryService categoryService;

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("categories", categoryService.findAll());
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("user") != null) return "redirect:/";
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {
        User user = userService.login(username, password);
        if (user == null) {
            ra.addFlashAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng hoặc tài khoản bị khóa!");
            return "redirect:/login";
        }
        session.setAttribute("user", user);
        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        if (session.getAttribute("user") != null) return "redirect:/";
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String fullName,
                           RedirectAttributes ra) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);

        String result = userService.register(user);
        if ("OK".equals(result)) {
            ra.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login";
        }
        ra.addFlashAttribute("error", result);
        return "redirect:/register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}