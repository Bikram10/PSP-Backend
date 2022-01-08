package com.bikram.appliedproject.service;

import javax.servlet.http.HttpServletRequest;

public interface PasswordResetService {

    String resetPassword(HttpServletRequest httpServletRequest, String email);

    String validatePasswordResetToken(String token);


}
