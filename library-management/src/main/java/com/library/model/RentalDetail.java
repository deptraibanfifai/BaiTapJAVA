package com.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDetail {
    private int id;
    private int rentalId;
    private int bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookImage;
}