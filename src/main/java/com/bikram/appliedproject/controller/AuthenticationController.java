package com.bikram.appliedproject.controller;

import com.bikram.appliedproject.domain.authentication.AuthToken;
import com.bikram.appliedproject.domain.authentication.LoginUser;
import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.security.JwtTokenUtil;
import com.bikram.appliedproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/token")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/generated-token")
    public ResponseEntity<AuthToken> register(@RequestBody LoginUser loginUser) throws Exception{
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final User user = userService.findOne(loginUser.getEmail());

        final String token = jwtTokenUtil.generateToken(authentication);

        return ResponseEntity.ok().body(new AuthToken(token));
    }
}
