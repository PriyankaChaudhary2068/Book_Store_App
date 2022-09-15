package com.bridgelabz.bookstoreapplication.dto;

import lombok.Data;

@Data
public class BookDTO {

    private String bookName;

    private String authorName;

    private String bookDescription;
    private String bookImg;

    private int price;
    private int quantity;
}
