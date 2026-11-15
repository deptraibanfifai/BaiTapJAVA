package com.library.controller;

import com.library.model.Rental;
import com.library.model.RentalDetail;
import com.library.model.User;
import com.library.service.CartService;
import com.library.service.RentalService;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private RentalService rentalService;
    @Autowired private CartService cartService;

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", userService.findById(user.getId()));
        model.addAttribute("cartCount", cartService.countCart(user.getId()));
        return "user/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String fullName,
                                @RequestParam String email,
                                @RequestParam String phone,
                                HttpSession session,
                                RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        userService.update(user);
        session.setAttribute("user", userService.findById(user.getId()));
        ra.addFlashAttribute("success", "Cập nhật thông tin thành công!");
        return "redirect:/user/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        String result = userService.changePassword(user.getId(), oldPassword, newPassword);
        if ("OK".equals(result)) {
            ra.addFlashAttribute("success", "Đổi mật khẩu thành công!");
        } else {
            ra.addFlashAttribute("error", result);
        }
        return "redirect:/user/profile";
    }

    @GetMapping("/history")
    public String rentalHistory(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        List<Rental> rentals = rentalService.findByUserId(user.getId());
        for (Rental r : rentals) {
            r.setBooks(null);
            List<RentalDetail> details = rentalService.findDetails(r.getId());
            // Gán tên sách vào rental để hiển thị
            model.addAttribute("details_" + r.getId(), details);
        }
        model.addAttribute("rentals", rentals);
        model.addAttribute("cartCount", cartService.countCart(user.getId()));
        return "user/history";
    }
}