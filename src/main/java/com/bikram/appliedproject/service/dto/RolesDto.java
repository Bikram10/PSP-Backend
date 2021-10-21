package com.bikram.appliedproject.service.dto;

import java.util.Set;

public class RolesDto {

    private long role_id;

    private String role_name;

    private Set<UserDto> userDtoSet;

    public RolesDto(long role_id) {
        this.role_id = role_id;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Set<UserDto> getUserDtoSet() {
        return userDtoSet;
    }

    public void setUserDtoSet(Set<UserDto> userDtoSet) {
        this.userDtoSet = userDtoSet;
    }
}
