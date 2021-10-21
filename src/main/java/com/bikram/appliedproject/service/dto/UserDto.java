package com.bikram.appliedproject.service.dto;


import java.util.Set;

public class UserDto {

    private Long user_id;

    private String username;

    private String email;

    private String password;

    private Set<RolesDto> roles;

    private boolean enabled;

    public UserDto(Long user_id, String username, String email, String password, Set<RolesDto> roles) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RolesDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolesDto> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
