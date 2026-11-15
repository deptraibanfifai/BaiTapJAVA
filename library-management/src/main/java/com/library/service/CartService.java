package com.library.service;

import com.library.dao.CartDAO;
import com.library.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;

    public List<Cart> getCartByUser(int userId) {
        return cartDAO.findByUserId(userId);
    }

    public int countCart(int userId) {
        return cartDAO.countByUserId(userId);
    }

    public String addToCart(int userId, int bookId) {
        if (cartDAO.exists(userId, bookId))
            return "Sách đã có trong giỏ hàng!";
        cartDAO.add(userId, bookId);
        return "OK";
    }

    public void removeFromCart(int cartId) {
        cartDAO.remove(cartId);
    }

    public void clearCart(int userId) {
        cartDAO.clearByUserId(userId);
    }

    public double getTotalCartPrice(int userId){

        List<Cart> items =
                cartDAO.findByUserId(userId);

        double total = 0;

        for(Cart item : items){
            total += item.getBookPrice();
        }

        return total;
    }
}