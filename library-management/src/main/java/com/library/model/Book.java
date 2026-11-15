package com.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int id;
    private String title;
    private String author;
    private int categoryId;
    private String categoryName;
    private String description;
    private double price;
    private int quantity;
    private int availableQuantity;
    private String image;
    private int publishYear;
    private String createdAt;
}