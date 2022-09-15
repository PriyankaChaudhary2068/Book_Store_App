package com.bridgelabz.bookstoreapplication.service;

import com.bridgelabz.bookstoreapplication.util.EmailSenderService;
import com.bridgelabz.bookstoreapplication.util.TokenUtil;
import com.bridgelabz.bookstoreapplication.dto.CartDTO;
import com.bridgelabz.bookstoreapplication.entity.BookData;
import com.bridgelabz.bookstoreapplication.entity.CartData;
import com.bridgelabz.bookstoreapplication.entity.UserRegistrationData;
import com.bridgelabz.bookstoreapplication.exception.BookStoreException;
import com.bridgelabz.bookstoreapplication.repository.BookRepository;
import com.bridgelabz.bookstoreapplication.repository.CartRepository;
import com.bridgelabz.bookstoreapplication.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private TokenUtil util;
    @Autowired
    private EmailSenderService mailService;


    @Override
    public String insert(CartDTO cartDTO) {
        Optional<BookData> bookData = bookRepository.findById(cartDTO.getBookId());
        Optional<UserRegistrationData> userRegistrationData = userRegistrationRepository.findById(cartDTO.getUserId());
        if (bookData.isPresent() && userRegistrationData.isPresent()) {
            CartData cartData = new CartData(userRegistrationData.get(), bookData.get(), cartDTO.getQuantity());
            cartRepository.save(cartData);
            String token = util.createToken(cartData.getCartId());
            return token;
        } else {
            throw new BookStoreException("BookData Or UserRegistrationData Not Found");
        }
    }

    @Override
    public List<CartData> getAllCart(String token) {
        int id = util.decodeToken(token);
        Optional<CartData> cartData = cartRepository.findById(id);
        if (cartData.isPresent()) {
            List<CartData> listOfCartData = cartRepository.findAll();
            log.info("ALL Cart Records Retrieved Successfully");
            return listOfCartData;
        } else {
            System.out.println("Exception ...Token Not Found!");
            return null;
        }
    }

    @Override
    public CartData getCartById(String token) {
        int id = util.decodeToken(token);
        Optional<CartData> cartData = cartRepository.findById(id);
        if (cartData.isPresent()) {
            return cartData.get();
        } else {
            throw new BookStoreException(" Didn't Find Any record For This Particular Cart Id");
        }
    }

    @Override
    public CartData updateCartById(String token, CartDTO cartDTO) {
        int id = util.decodeToken(token);
        Optional<CartData> cart = cartRepository.findById(id);
        Optional<BookData> book = bookRepository.findById(cartDTO.getBookId());
        Optional<UserRegistrationData> user = userRegistrationRepository.findById(cartDTO.getUserId());
        if (cart.isPresent()) {
            if (book.isPresent() && user.isPresent()) {
                CartData cartData = new CartData(id, user.get(), book.get(), cartDTO.getQuantity());
                cartRepository.save(cartData);
                log.info("Cart Record Updated Successfully For id " + id);
                return cartData;
            } else {
                throw new BookStoreException("Book Or User Doesn't Exists");
            }
        } else {
            throw new BookStoreException("Cart Record Doesn't Exists");
        }
    }

    @Override
    public void deleteCartData(String token) {
        int id = util.decodeToken(token);
        Optional<CartData> deleteData = cartRepository.findById(id);
        if (deleteData.isPresent()) {
            cartRepository.deleteById(id);
        } else {
            throw new BookStoreException(" Did Not Get Any Cart For Specific Cart Id ");
        }
    }
}
