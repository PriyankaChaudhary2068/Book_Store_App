package com.bridgelabz.bookstoreapplication.controller;

import com.bridgelabz.bookstoreapplication.dto.OrderDTO;
import com.bridgelabz.bookstoreapplication.dto.ResponseDTO;
import com.bridgelabz.bookstoreapplication.entity.OrderData;
import com.bridgelabz.bookstoreapplication.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    //localhost:8080/order/create
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> insertOrder(@Valid @RequestBody OrderDTO orderdto) {
        String orderData = orderService.insert(orderdto);
        ResponseDTO responseDTO = new ResponseDTO("Order placed successfully !", orderData);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }

    //localhost:8080/order/getallorders/{token}
    @GetMapping("/getallorders/{token}")
    public ResponseEntity<ResponseDTO> getAllOrder(@PathVariable String token) {
        List<OrderData> orderData = orderService.getAllOrder(token);
        ResponseDTO responseDTO = new ResponseDTO("All Records retrieved Successfully !", orderData);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/order/get/{token}
    @GetMapping("/get/{token}")
    public ResponseEntity<ResponseDTO> getOrderById(@PathVariable String token) {
        OrderData orderData = orderService.getOrderById(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successfully !", orderData);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/order/cancelOrder/{token}/{userId}
    @PutMapping("/cancelOrder/{token}/{userId}")
    public ResponseEntity<ResponseDTO> cancelOrderById(@PathVariable String token, @PathVariable int userId) {
        OrderData orderData = orderService.cancelOrderById(token, userId);
        ResponseDTO responseDTO = new ResponseDTO("Order Record Canceled Successfully !", orderData);
        return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
    }
}
