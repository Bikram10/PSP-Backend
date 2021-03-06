package com.bikram.appliedproject.service.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws ServletException, IOException {
        setDefaultFailureUrl("http://localhost:4200/login?error = true");

        super.onAuthenticationFailure(request, response, e);

        Locale locale = localeResolver.resolveLocale(request);

        String errorMessage = messageSource.getMessage("message.badCredentials", null, locale);

        if(e.getMessage().equalsIgnoreCase("User is disabled")){
            errorMessage = messageSource.getMessage("auth.message.disabled", null, locale);
        }
        else if(e.getMessage().equalsIgnoreCase("User account has expired")){
            errorMessage = messageSource.getMessage("auth.message.expired", null, locale);
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}
