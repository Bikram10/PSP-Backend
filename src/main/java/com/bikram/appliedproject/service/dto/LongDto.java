package com.bikram.appliedproject.service.dto;

public class LongDto {

    private String oldpassword;

    private String password;


    public LongDto(String oldpassword, String password) {
        this.oldpassword = oldpassword;
        this.password = password;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
