package com.bridgelabz.bookstoreapplication.service;

import com.bridgelabz.bookstoreapplication.dto.CartDTO;
import com.bridgelabz.bookstoreapplication.entity.CartData;

import java.util.List;

public interface ICartService {
    String insert(CartDTO cartDTO);

    List<CartData> getAllCart(String token);

    CartData getCartById(String token);

    CartData updateCartById(String token, CartDTO cartDTO);

    void deleteCartData(String token);
}
