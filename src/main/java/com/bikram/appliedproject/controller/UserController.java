package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> save(@RequestPart User user){
        return ResponseEntity.ok().body(userService.save(user));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listUser")
    public ResponseEntity<List<User>> listUser(){
        return ResponseEntity.ok().body(userService.findAll());
    }
}
