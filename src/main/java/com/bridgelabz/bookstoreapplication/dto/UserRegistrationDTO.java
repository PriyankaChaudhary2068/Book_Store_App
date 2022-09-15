package com.bridgelabz.bookstoreapplication.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserRegistrationDTO {

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "first name pattern is invalid")
    private String firstName;

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "first name pattern is invalid")
    private String lastName;

    private String email;
    private String address;
    private String password;
}
