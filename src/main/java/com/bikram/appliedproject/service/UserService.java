package com.bikram.appliedproject.service;

import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.domain.authentication.VerificationToken;
import com.bikram.appliedproject.service.dto.UserDto;

import java.util.List;


public interface UserService {

    UserDto save(UserDto userDto);
    User findOne(String email);

    User getUser(String verificationToken);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String verificationToken);

    boolean emailExist(String email);

    List<User> findAll();

    void savedRegisteredUser(User user);
}
