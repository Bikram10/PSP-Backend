package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.authentication.PasswordResetToken;
import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.repositories.PasswordTokenRepository;
import com.bikram.appliedproject.repositories.UserRepository;
import com.bikram.appliedproject.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private Environment environment;

    @Override
    public String resetPassword(HttpServletRequest request, String email) {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        String token = UUID.randomUUID().toString();
        createPasswordTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail("http://localhost:4200", request.getLocale(), token, user));

        return "Success";
    }

    public void createPasswordTokenForUser(User user, String token){
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);

        passwordTokenRepository.save(passwordResetToken);
    }

    public SimpleMailMessage constructResetTokenEmail(String path, Locale locale, String token, User user) {
        String url = path + "/success?token="+token;
        String message = messageSource.getMessage("message.resetPassword", null, locale);

        return constructEmail("Reset Password", message+"\r\n"+url, user);
    }

    public SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom(environment.getProperty("support.email"));

        return mailMessage;
    }

    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);
        return !isTokenFound(passwordResetToken)? "invalid token": isTokenExpired(passwordResetToken) ? "expired" : null;
    }

    private boolean isTokenFound(PasswordResetToken resetToken){
        return resetToken !=null;
    }

    private boolean isTokenExpired(PasswordResetToken resetToken){
        final Calendar calendar = Calendar.getInstance();
        return resetToken.getExpiryDate().before(calendar.getTime());
    }
}
