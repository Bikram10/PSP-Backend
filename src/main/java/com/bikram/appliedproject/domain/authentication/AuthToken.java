package com.bikram.appliedproject.domain.authentication;

public class AuthToken {
    private String token;
    private String email;

    public AuthToken(){

    }

    public AuthToken(String token){
        this.token = token;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
