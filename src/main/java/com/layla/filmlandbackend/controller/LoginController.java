package com.layla.filmlandbackend.controller;

import com.layla.filmlandbackend.controller.dto.LoginRequestDTO;
import com.layla.filmlandbackend.controller.dto.LoginResponse;
import com.layla.filmlandbackend.interfaces.UserService;
import com.layla.filmlandbackend.model.entity.FilmlandUser;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final UserService service;

    public LoginController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody @Validated LoginRequestDTO dto){
        FilmlandUser user = service.registerNewUser(dto);
        String status = String.format("Registration of %s successful", user.getUsername());

        return ResponseEntity
                .ok(new LoginResponse(status, "Welcome to Filmland!"));
    }
}