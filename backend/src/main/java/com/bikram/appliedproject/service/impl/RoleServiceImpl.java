package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.authentication.Role;
import com.bikram.appliedproject.repositories.RoleRepository;
import com.bikram.appliedproject.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<Role> findByRole_id(Long id) {
        return roleRepository.findById(id);
    }
}
