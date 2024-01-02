package com.layla.filmlandbackend.controller;

import com.layla.filmlandbackend.model.dto.LoginRequestDTO;
import com.layla.filmlandbackend.model.dto.ResponseDTO;
import com.layla.filmlandbackend.interfaces.UserService;
import com.layla.filmlandbackend.model.entity.FilmlandUser;
import com.layla.filmlandbackend.model.service.JwtService;
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

@RestController
@RequestMapping("/api")
public class LoginController {

    private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public LoginController(UserService userService, JwtService jwtService, AuthenticationManager authManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody @Validated LoginRequestDTO login)
            throws AuthenticationException {

         Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(login.username(), login.password()));

        String token = jwtService.generateToken(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token));
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseDTO("Login successful", "Welcome to Filmland!"));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Validated LoginRequestDTO dto){
        FilmlandUser user = userService.registerNewUser(dto);
        String status = String.format("Registration of %s successful", user.getUsername());

        return ResponseEntity
                .ok(new ResponseDTO(status, "Welcome to Filmland!"));
    }
}