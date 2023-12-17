package com.layla.filmlandbackend.controller;

import com.layla.filmlandbackend.controller.dto.LoginRequestDTO;
import com.layla.filmlandbackend.controller.dto.LoginResponse;
import com.layla.filmlandbackend.interfaces.UserService;
import com.layla.filmlandbackend.model.entity.FilmlandUser;
import com.layla.filmlandbackend.model.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.StringJoiner;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;

    public LoginController(UserService userService, TokenService tokenService, AuthenticationManager authManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequestDTO login)
            throws AuthenticationException {

        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(login.username(), login.password()));

        String token = tokenService.generateToken(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token));
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new LoginResponse("Login successful", "Welcome to Filmland!"));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody @Validated LoginRequestDTO dto){
        FilmlandUser user = userService.registerNewUser(dto);
        String status = String.format("Registration of %s successful", user.getUsername());

        return ResponseEntity
                .ok(new LoginResponse(status, "Welcome to Filmland!"));
    }
}