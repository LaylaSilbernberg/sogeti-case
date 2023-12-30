package com.layla.filmlandbackend.controller;

import com.layla.filmlandbackend.controller.dto.LoginRequestDTO;
import com.layla.filmlandbackend.controller.dto.ResponseDTO;
import com.layla.filmlandbackend.enums.AuthRoles;
import com.layla.filmlandbackend.interfaces.UserService;
import com.layla.filmlandbackend.model.entity.FilmlandUser;
import com.layla.filmlandbackend.model.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {


    @InjectMocks
    LoginController controller;

    @Mock
    UserService userService;
    @Mock
    JwtService jwtService;
    @Mock
    AuthenticationManager authManager;


    private final LoginRequestDTO dto = new LoginRequestDTO("mock@filmland-assessment.nl", "Java/90");

    @Test
    void register(){
        when(userService.registerNewUser(any(LoginRequestDTO.class)))
                .thenReturn(new FilmlandUser("info@filmland-assessment.nl",
                        "JavaIsCool90",
                        AuthRoles.USER.name()));

        ResponseEntity<ResponseDTO> response = controller.register(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new ResponseDTO("Registration of info@filmland-assessment.nl successful",
                "Welcome to Filmland!"), response.getBody());
    }

    @Test
    void loginResponseShouldHaveAuthenticationHeader() {
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("Test", "Test"));

       ResponseEntity<ResponseDTO> response = controller.login(dto);

       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertTrue(response.getHeaders().containsKey(HttpHeaders.AUTHORIZATION));
    }


}