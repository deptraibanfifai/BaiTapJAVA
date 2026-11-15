package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.CartDAO;
import com.library.dao.RentalDAO;
import com.library.model.Cart;
import com.library.model.Rental;
import com.library.model.RentalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalDAO rentalDAO;

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private BookDAO bookDAO;

    // ==================================
    // THUÊ SÁCH TỪ GIỎ HÀNG
    // ==================================

    public String rentFromCart(
            int userId,
            List<Integer> cartIds,
            String rentalDate,
            String dueDate) {

        List<Cart> cartItems =
                cartDAO.findByIds(cartIds);

        if (cartItems.isEmpty()) {
            return "Bạn chưa chọn sách!";
        }

        for (Cart item : cartItems) {

            if (bookDAO.findById(item.getBookId())
                    .getAvailableQuantity() <= 0) {

                return "Sách "
                        + item.getBookTitle()
                        + " đã hết!";
            }
        }

        LocalDate start =
                LocalDate.parse(rentalDate);

        LocalDate end =
                LocalDate.parse(dueDate);

        long days =
                java.time.temporal.ChronoUnit.DAYS
                        .between(start, end);

        if (days <= 0) {
            return "Ngày trả phải lớn hơn ngày thuê!";
        }

        double totalBookPrice = 0;

        for (Cart item : cartItems) {
            totalBookPrice += item.getBookPrice();
        }

        double rentalFee =
                totalBookPrice * 0.10 * days;

        Rental rental = new Rental();

        rental.setUserId(userId);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);

        rental.setRentalFee(rentalFee);
        rental.setLateFee(0);
        rental.setTotalAmount(rentalFee);

        int rentalId =
                rentalDAO.save(rental);

        for (Cart item : cartItems) {

            rentalDAO.saveDetail(
                    rentalId,
                    item.getBookId()
            );

            bookDAO.decreaseQuantity(
                    item.getBookId()
            );
        }

        cartDAO.removeSelected(cartIds);

        return "OK";
    }

    // ==================================
    // CRUD RENTAL
    // ==================================

    public List<Rental> findAll() {
        return rentalDAO.findAll();
    }

    public List<Rental> findByUserId(int userId) {
        return rentalDAO.findByUserId(userId);
    }

    public Rental findById(int id) {
        return rentalDAO.findById(id);
    }

    public List<RentalDetail> findDetails(int rentalId) {
        return rentalDAO.findDetailsByRentalId(rentalId);
    }

    public int countAll() {
        return rentalDAO.countAll();
    }

    public List<Rental> findRecent(int limit) {
        return rentalDAO.findRecent(limit);
    }

    // ==================================
    // ADMIN
    // ==================================

    public void confirmRental(int rentalId) {

        rentalDAO.updateStatus(
                rentalId,
                "RENTING"
        );
    }

    public void confirmReturn(int rentalId) {

        Rental rental =
                rentalDAO.findById(rentalId);

        double lateFee = 0;

        LocalDate today =
                LocalDate.now();

        LocalDate dueDate =
                LocalDate.parse(
                        rental.getDueDate()
                );

        // Phạt 6% nếu trả trễ
        if (today.isAfter(dueDate)) {

            lateFee =
                    rental.getRentalFee()
                            * 0.10;

            rentalDAO.updateLateFee(
                    rentalId,
                    lateFee
            );
        }

        List<RentalDetail> details =
                rentalDAO.findDetailsByRentalId(
                        rentalId
                );

        for (RentalDetail detail : details) {

            bookDAO.increaseQuantity(
                    detail.getBookId()
            );
        }

        rentalDAO.confirmReturn(rentalId);
    }

    // ==================================
    // DASHBOARD
    // ==================================

    public int countRentingBooks() {
        return rentalDAO.countRentingBooks();
    }

    public int countOverdueRentals() {
        return rentalDAO.countOverdueRentals();
    }

    public double revenueCurrentMonth() {
        return rentalDAO.revenueCurrentMonth();
    }

    public double revenueCurrentYear() {
        return rentalDAO.revenueCurrentYear();
    }

    public List<Double> getRevenueByMonth() {
        return rentalDAO.getRevenueByMonth();
    }
}