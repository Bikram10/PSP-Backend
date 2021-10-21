package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.domain.authentication.Role;
import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.domain.authentication.VerificationToken;
import com.bikram.appliedproject.repositories.UserRepository;
import com.bikram.appliedproject.repositories.VerificationTokenRepository;
import com.bikram.appliedproject.service.RoleService;
import com.bikram.appliedproject.service.UserService;
import com.bikram.appliedproject.service.dto.UserDto;
import com.bikram.appliedproject.service.exception.UserAlreadyExistException;
import com.bikram.appliedproject.service.mapper.UserMapper;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean crediantialNonExpired = true;
        boolean accountNonLocked = true;

        User user = userRepository.findByEmail(s);

        if(user == null){
            throw new UsernameNotFoundException("Invalid Username or password");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                user.isEnabled(),
                accountNonExpired,
                crediantialNonExpired,
                accountNonLocked,
                getAuthority(user));
    }

    private Collection<? extends GrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole_name()));
        });
        return authorities;
    }

    @Override
    public UserDto save(UserDto userDto) throws UserAlreadyExistException {

        Optional<Role> optionalRole = roleService.findByRole_id(new Long(2));
        Role role = optionalRole.isPresent() ? optionalRole.get() : null;

        User user = new User();

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        user.setRoles(roles);
       user = userRepository.save(user);

       return userMapper.userToDTO(user);
    }

    @Override
    public User findOne(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUser(String verificationToken) {
        return tokenRepository.findByToken(verificationToken).getUser();
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        tokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public boolean emailExist(String email) {
        return userRepository.findByEmail(email) !=null;
    }

    @Override
    public void savedRegisteredUser(User user) {
        userRepository.save(user);
    }
}
