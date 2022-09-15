package com.bridgelabz.bookstoreapplication.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderDTO {

    private int quantity;
    @NotEmpty(message = "Address Should Not Be Empty")
    private String address;
    private int userId;
    private int bookId;
    private boolean cancel;
    private int price;
}
