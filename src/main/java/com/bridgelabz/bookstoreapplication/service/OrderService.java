package com.bridgelabz.bookstoreapplication.service;

import com.bridgelabz.bookstoreapplication.util.EmailSenderService;
import com.bridgelabz.bookstoreapplication.util.TokenUtil;
import com.bridgelabz.bookstoreapplication.dto.OrderDTO;
import com.bridgelabz.bookstoreapplication.entity.BookData;
import com.bridgelabz.bookstoreapplication.entity.OrderData;
import com.bridgelabz.bookstoreapplication.entity.UserRegistrationData;
import com.bridgelabz.bookstoreapplication.exception.BookStoreException;
import com.bridgelabz.bookstoreapplication.repository.BookRepository;
import com.bridgelabz.bookstoreapplication.repository.OrderRepository;
import com.bridgelabz.bookstoreapplication.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService implements IOrderService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TokenUtil util;
    @Autowired
    private EmailSenderService mailService;


    @Override
    public String insert(OrderDTO orderDTO) {
        Optional<BookData> book = bookRepository.findById(orderDTO.getBookId());
        Optional<UserRegistrationData> user = userRegistrationRepository.findById(orderDTO.getUserId());
        if (book.isPresent() && user.isPresent()) {
            if (orderDTO.getQuantity() <= book.get().getQuantity()) {
                int quantity = book.get().getQuantity() - orderDTO.getQuantity();
                book.get().setQuantity(quantity);
                bookRepository.save(book.get());
                int totalPrice = book.get().getPrice() * orderDTO.getQuantity();
                OrderData newOrder = new OrderData(totalPrice, orderDTO.getQuantity(), orderDTO.getAddress(), book.get(), user.get(), orderDTO.isCancel());
                orderRepository.save(newOrder);
                String token = util.createToken(newOrder.getOrderId());
                mailService.sendEmail(newOrder.getUserRegistrationData().getEmail(), "Test Email", "Order Created SuccessFully "
                        + newOrder.getOrderId() );

                log.info("Order Record Inserted Successfully");
                return token;
            } else {
                throw new BookStoreException("Requested Quantity Is Out Of Stock");
            }
        } else {
            throw new BookStoreException("Book Or User Doesn't Exists");
        }
    }


    @Override
    public List<OrderData> getAllOrder(String token) {
        Integer id = util.decodeToken(token);
        Optional<OrderData> orderData = orderRepository.findById(id);
        if (orderData.isPresent()) {
            List<OrderData> listOrderData = orderRepository.findAll();
            log.info("ALL Order Records Retrieved Successfully");
            mailService.sendEmail("priyankachaudhary0620@gmail.com", "Email", "Get Your Data With This Token "
                    + orderData.get().getUserRegistrationData().getEmail());

            return listOrderData;
        } else {
            System.out.println("Exception ...Token not found!");
            return null;
        }
    }


    @Override
    public OrderData getOrderById(String token) {
        Integer id = util.decodeToken(token);
        Optional<OrderData> orderData = orderRepository.findById(id);
        if (orderData.isPresent()) {
            log.info("Order Record Retrieved Successfully For id " + id);
            mailService.sendEmail("priyankachaudhary0620@gmail.com", " Email", "Get Your Data With This Token "
                    + orderData.get().getUserRegistrationData().getEmail() );

            return orderData.get();
        } else {
            throw new BookStoreException("Order Doesn't Exists");
        }
    }


    @Override
    public OrderData cancelOrderById(String token, int userId) {

        Integer id = util.decodeToken(token);
        Optional<OrderData> order = orderRepository.findById(id);
        Optional<UserRegistrationData> user = userRegistrationRepository.findById(userId);
        if (order.isPresent() && user.isPresent()) {
            order.get().setCancel(true);
            orderRepository.save(order.get());
            mailService.sendEmail(order.get().getUserRegistrationData().getEmail(), "Test Email", "Canceled Order SuccessFully "
                    + order.get().getOrderId() );

            return order.get();
        } else {
            throw new BookStoreException("Order Record Doesn't Exists");
        }
    }
}
