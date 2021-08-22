package com.bikram.appliedproject.service;

import com.bikram.appliedproject.domain.authentication.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findByRole_id(Long id);

}
