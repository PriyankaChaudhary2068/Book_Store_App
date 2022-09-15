package com.bridgelabz.bookstoreapplication.controller;

import com.bridgelabz.bookstoreapplication.dto.CartDTO;
import com.bridgelabz.bookstoreapplication.dto.ResponseDTO;
import com.bridgelabz.bookstoreapplication.entity.CartData;
import com.bridgelabz.bookstoreapplication.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("cart")
@CrossOrigin
public class CartController {

    @Autowired
    private ICartService iCartService;

    //localhost:8080/cart/getAll/{token}
    @GetMapping("/getAll/{token}")
    public ResponseEntity<ResponseDTO> getAllCart(@PathVariable String token) {
        List<CartData> cartData = iCartService.getAllCart(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success", cartData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/cart/give/{token}
    @GetMapping("/give/{token}")
    public ResponseEntity<ResponseDTO> getCartById(@PathVariable String token) {
        CartData cartData = iCartService.getCartById(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success For Id Successfully", cartData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    // localhost:8080/cart/create
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> addCart(@Valid @RequestBody CartDTO cartDTO) {
        String cartData = iCartService.insert(cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("Created Cart Data Successfully", cartData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/cart/update/{token}
    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateBooksById(@PathVariable String token, @Valid @RequestBody CartDTO cartDTO) {
        CartData cartData = iCartService.updateCartById(token, cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated Cart Data Successfully", cartData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/cart/cartDelete/{token}
    @DeleteMapping("/cartDelete/{token}")
    public ResponseEntity<ResponseDTO> deleteCartData(@PathVariable String token) {
        iCartService.deleteCartData(token);
        ResponseDTO responseDTO = new ResponseDTO("Deleted Successfully", token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
