package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.service.UserService;
import com.bikram.appliedproject.service.validation.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event){
        User user = event.getUserDto();

        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String recepientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/confirm?token="+token;

        String message = messageSource.getMessage("message.regSucc", null, event.getLocale());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recepientAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        javaMailSender.send(simpleMailMessage);
    }
}
