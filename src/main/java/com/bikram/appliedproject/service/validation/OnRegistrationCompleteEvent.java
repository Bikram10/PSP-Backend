package com.bikram.appliedproject.service.validation;


import com.bikram.appliedproject.domain.authentication.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;
    private User user;
    private Locale locale;

    public OnRegistrationCompleteEvent(User user, Locale locale){
        super(user);

        this.user = user;
        this.locale=locale;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public User getUserDto() {
        return user;
    }

    public void setUserDto(User user) {
        this.user = user;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
