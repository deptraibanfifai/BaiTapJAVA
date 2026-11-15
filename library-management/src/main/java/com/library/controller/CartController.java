package com.library.controller;

import com.library.model.User;
import com.library.service.CartService;
import com.library.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private RentalService rentalService;

    // =========================
    // HIỂN THỊ GIỎ HÀNG
    // =========================

    @GetMapping("/cart")
    public String viewCart(
            Model model,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "cartItems",
                cartService.getCartByUser(
                        user.getId()
                )
        );

        model.addAttribute(
                "cartCount",
                cartService.countCart(
                        user.getId()
                )
        );

        double totalPrice =
                cartService.getTotalCartPrice(
                        user.getId()
                );

        model.addAttribute(
                "totalPrice",
                totalPrice
        );

        return "cart/cart";
    }

    // =========================
    // THÊM VÀO GIỎ
    // =========================

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam int bookId,
            HttpSession session,
            RedirectAttributes ra) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {

            ra.addFlashAttribute(
                    "error",
                    "Vui lòng đăng nhập!"
            );

            return "redirect:/login";
        }

        String result =
                cartService.addToCart(
                        user.getId(),
                        bookId
                );

        if ("OK".equals(result)) {

            ra.addFlashAttribute(
                    "success",
                    "Đã thêm vào giỏ hàng!"
            );

        } else {

            ra.addFlashAttribute(
                    "error",
                    result
            );
        }

        return "redirect:/book/detail/" + bookId;
    }

    // =========================
    // XÓA KHỎI GIỎ
    // =========================

    @GetMapping("/cart/remove/{cartId}")
    public String removeFromCart(
            @PathVariable int cartId,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        cartService.removeFromCart(cartId);

        return "redirect:/cart";
    }

    // =========================
    // GỬI YÊU CẦU THUÊ SÁCH
    // =========================

    @PostMapping("/cart/checkout")
    public String checkout(

            @RequestParam(
                    value = "cartIds",
                    required = false
            )
            List<Integer> cartIds,

            @RequestParam String rentalDate,

            @RequestParam String dueDate,

            HttpSession session,

            RedirectAttributes ra) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        if (cartIds == null || cartIds.isEmpty()) {

            ra.addFlashAttribute(
                    "error",
                    "Vui lòng chọn ít nhất 1 cuốn sách!"
            );

            return "redirect:/cart";
        }

        String result =
                rentalService.rentFromCart(
                        user.getId(),
                        cartIds,
                        rentalDate,
                        dueDate
                );

        if ("OK".equals(result)) {

            ra.addFlashAttribute(
                    "success",
                    "Yêu cầu thuê sách đã được gửi. Vui lòng chờ Admin xác nhận."
            );

        } else {

            ra.addFlashAttribute(
                    "error",
                    result
            );
        }

        return "redirect:/cart";
    }
}