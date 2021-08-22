package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.authentication.Role;
import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.repositories.UserRepository;
import com.bikram.appliedproject.service.RoleService;
import com.bikram.appliedproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);

        if(user == null){
            throw new UsernameNotFoundException("Invalid Username or password");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Collection<? extends GrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole_name()));
        });
        return authorities;
    }

    @Override
    public User save(User user){
        Optional<Role> optionalRole = roleService.findByRole_id(new Long(2));
        Role role = optionalRole.isPresent() ? optionalRole.get() : null;

        User newUser = new User();

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }

    @Override
    public User findOne(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
}
