package com.bridgelabz.bookstoreapplication.service;

import com.bridgelabz.bookstoreapplication.util.EmailSenderService;
import com.bridgelabz.bookstoreapplication.util.TokenUtil;
import com.bridgelabz.bookstoreapplication.dto.LoginDTO;
import com.bridgelabz.bookstoreapplication.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapplication.entity.UserRegistrationData;
import com.bridgelabz.bookstoreapplication.exception.BookStoreException;
import com.bridgelabz.bookstoreapplication.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRegistrationService implements IUserRegistrationService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private EmailSenderService mailService;
    @Autowired
    private TokenUtil util;


    @Override
    public String createUser(UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = new UserRegistrationData(userRegistrationDTO);
        userRegistrationRepository.save(userRegistrationData);
        String token = util.createToken(userRegistrationData.getUserId());
        mailService.sendEmail(userRegistrationData.getEmail(), "Test Email", "Registered Successfully, "
                + userRegistrationData.getFirstName() + token);

        return token;
    }

    @Override
    public List<UserRegistrationData> getAllUsers(String token) {
        int id = util.decodeToken(token);
        Optional<UserRegistrationData> isContactPresent = userRegistrationRepository.findById(id);
        if (isContactPresent.isPresent()) {
            List<UserRegistrationData> listOfBook = userRegistrationRepository.findAll();
            mailService.sendEmail("priyankachaudhary0620@gmail.com", "Test Email", "Get Your Data With This Token "
                    + isContactPresent.get().getFirstName() + token);

            return listOfBook;
        } else {
            System.out.println("Exception ...Token Not Found!");
            return null;
        }
    }

    @Override
    public UserRegistrationData getUserById(String token) {
        int id = util.decodeToken(token);
        Optional<UserRegistrationData> getUser = userRegistrationRepository.findById(id);
        if (getUser.isPresent()) {
            mailService.sendEmail("priyankachaudhary0620@gmail.com", "Test Email", "Get Your Data With This Token "
                    + getUser.get().getEmail() + token);

            return getUser.get();
        } else {
            throw new BookStoreException("Record For Provided UserId Is Not Found");
        }
    }


    @Override
    public List<UserRegistrationData> getUserByEmail(String token) {
        List<UserRegistrationData> findEmail = userRegistrationRepository.findByEmail(token);
        if (findEmail.isEmpty()) {
            throw new BookStoreException(" Details For Provided User Is Not Found");
        }
        return findEmail;
    }

    @Override
    public UserRegistrationData updateUser(String token, UserRegistrationDTO userRegistrationDTO) {
        Integer id = util.decodeToken(token);
        Optional<UserRegistrationData> userRegistrationData = userRegistrationRepository.findById(id);
        if (userRegistrationData.isPresent()) {
            UserRegistrationData newBook = new UserRegistrationData(id, userRegistrationDTO);
            userRegistrationRepository.save(newBook);
            mailService.sendEmail(newBook.getEmail(), "Test Email", "Updated Successfully "
                    + newBook.getFirstName() + token);

            return newBook;
        }
        throw new BookStoreException("User Details For Id Not Found");
    }


    @Override
    public Optional<UserRegistrationData> login(LoginDTO loginDTO) {
        Optional<UserRegistrationData> userRegistrationData = userRegistrationRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (userRegistrationData.isPresent()) {
            return userRegistrationData;
        } else {
            return null;
        }
    }
}
