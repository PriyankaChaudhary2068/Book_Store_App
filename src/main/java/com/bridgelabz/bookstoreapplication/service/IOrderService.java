package com.bridgelabz.bookstoreapplication.service;

import com.bridgelabz.bookstoreapplication.dto.OrderDTO;
import com.bridgelabz.bookstoreapplication.entity.OrderData;

import java.util.List;

public interface IOrderService {
    String insert(OrderDTO orderDTO);

    List<OrderData> getAllOrder(String token);

    OrderData getOrderById(String token);

    OrderData cancelOrderById(String token, int userId);
}
