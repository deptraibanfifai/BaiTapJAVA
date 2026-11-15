package com.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

    private int id;

    private int userId;

    private String username;

    private String rentalDate;

    private String dueDate;

    private String returnDate;

    private String status;

    private String createdAt;

    private List<Book> books;

    private double rentalFee;

    private double lateFee;

    private double totalAmount;

    private String paymentStatus;
}