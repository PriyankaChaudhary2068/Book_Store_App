package com.bridgelabz.bookstoreapplication.service;

import com.bridgelabz.bookstoreapplication.dto.LoginDTO;
import com.bridgelabz.bookstoreapplication.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapplication.entity.UserRegistrationData;

import java.util.List;
import java.util.Optional;

public interface IUserRegistrationService {
    String createUser(UserRegistrationDTO userRegistrationDTO);

    List<UserRegistrationData> getAllUsers(String token);

    UserRegistrationData getUserById(String token);

    List<UserRegistrationData> getUserByEmail(String token);

    UserRegistrationData updateUser(String token, UserRegistrationDTO userRegistrationDTO);

    Optional<UserRegistrationData> login(LoginDTO loginDTO);

}
