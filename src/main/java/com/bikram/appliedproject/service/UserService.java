package com.bikram.appliedproject.service;

import com.bikram.appliedproject.domain.authentication.User;

import java.util.List;


public interface UserService {

    User save(User user);
    User findOne(String username);

    List<User> findAll();
}
