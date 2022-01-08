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
        System.out.println(onRegistrationCompleteEvent.getUserDto().getEmail());
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event){
        User user = event.getUserDto();

        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("sbikrm@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/confirm?token="+token);
        javaMailSender.send(mailMessage);
    }
}
