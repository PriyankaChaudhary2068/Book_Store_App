package com.bridgelabz.bookstoreapplication.controller;

import com.bridgelabz.bookstoreapplication.dto.LoginDTO;
import com.bridgelabz.bookstoreapplication.dto.ResponseDTO;
import com.bridgelabz.bookstoreapplication.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapplication.entity.UserRegistrationData;
import com.bridgelabz.bookstoreapplication.service.IUserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserRegistrationController {

    @Autowired
    private IUserRegistrationService iUserRegistrationService;

    //localhost:8080/user/create
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> addUserRegistration(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        String userRegistrationData = iUserRegistrationService.createUser(userRegistrationDTO);
        ResponseDTO responseDTO = new ResponseDTO("Created UserRegistration Data Succesfully", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/user/token
    @GetMapping("/getall/{token}")
    public ResponseEntity<ResponseDTO> getAllUsers(@PathVariable String token) {
        List<UserRegistrationData> userRegistrationData = iUserRegistrationService.getAllUsers(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/user/getby/{token}
    @GetMapping("/getby/{token}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable String token) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.getUserById(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success For Id Successfully", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/user/email/{token}
    @GetMapping("/email/{token}")
    public ResponseEntity<ResponseDTO> getUserByEmail(@PathVariable String token) {
        List<UserRegistrationData> userRegistrationData = iUserRegistrationService.getUserByEmail(token);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success For Id Successfully", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/user/login
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Optional<UserRegistrationData> userRegistrationData = iUserRegistrationService.login(loginDTO);
        if (userRegistrationData != null) {
            ResponseDTO responseDTO = new ResponseDTO("Login Succesfully", userRegistrationData);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.ACCEPTED);
        } else {
            ResponseDTO responseDTO = new ResponseDTO("login Failed", userRegistrationData);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.ACCEPTED);
        }
    }

    //localhost:8080/user/update/{token}
    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable String token, @Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.updateUser(token, userRegistrationDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated User Data Successfully", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
