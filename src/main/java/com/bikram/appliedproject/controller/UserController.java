package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.domain.authentication.VerificationToken;
import com.bikram.appliedproject.service.UserService;
import com.bikram.appliedproject.service.dto.UserDto;
import com.bikram.appliedproject.service.exception.UserAlreadyExistException;
import com.bikram.appliedproject.service.mapper.UserMapper;
import com.bikram.appliedproject.service.validation.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, UserDto>> save(@RequestPart UserDto registerUser, HttpServletRequest servletRequest, Errors errors){
        Map<String, UserDto> response = new HashMap<>();
        try{
            if(userService.emailExist(registerUser.getEmail())){
                response.put("error", registerUser);
                return ResponseEntity.ok().body(response);
            }
            UserDto registeredUser= userService.save(registerUser);

            response.put("success", registeredUser);


            String appUrl = servletRequest.getContextPath();
            applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(userMapper.DtoToUser(registeredUser), servletRequest.getLocale(), appUrl));
        }
        catch (UserAlreadyExistException e){
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmRegistration(WebRequest webRequest, @RequestParam("token") String token){
        System.out.println(token);
        Locale locale = webRequest.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);

        if(verificationToken == null){
            String message = messageSource.getMessage("auth.message.invalidToken", null, locale);

            return ResponseEntity.ok().body(message);
        }

        User user = verificationToken.getUser();

        Calendar calendar = Calendar.getInstance();

        if((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <=0){
            String messageValue = messageSource.getMessage("auth.message.expired", null, locale);
            return ResponseEntity.ok().body(messageValue);
        }

        user.setEnabled(true);
        userService.savedRegisteredUser(user);
        return ResponseEntity.ok().body("success");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listUser")
    public ResponseEntity<List<User>> listUser(){
        return ResponseEntity.ok().body(userService.findAll());
    }
}
