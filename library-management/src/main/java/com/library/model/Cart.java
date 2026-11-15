package com.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private int id;
    private int userId;
    private int bookId;
    private String createdAt;
    private String bookTitle;
    private String bookAuthor;
    private String bookImage;
    private double bookPrice;
}