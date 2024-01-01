package com.layla.filmlandbackend.exception;

import com.layla.filmlandbackend.controller.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    protected ResponseEntity<ResponseDTO> userAlreadyExists(UserAlreadyExistsException ex,
                                                            WebRequest request){
        String status = "Registration denied.";

        return ResponseEntity.badRequest().body(new ResponseDTO(status, ex.getMessage()));
    }
    @ExceptionHandler(value = {UsernameNotFoundException.class})
    protected ResponseEntity<ResponseDTO> usernameNotFound(UsernameNotFoundException ex){
        String status = "Username not found. User does not exist.";

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(status, ex.getMessage()));
    }
    @ExceptionHandler(value = {InvalidCategoryException.class})
    protected ResponseEntity<ResponseDTO> invalidCategory(InvalidCategoryException ex){
        String status = "Category does not exist!";

        return ResponseEntity
                .badRequest()
                .body(new ResponseDTO(status, ex.getMessage()));
    }
    @ExceptionHandler(value = {InvalidSubscriptionException.class})
    protected ResponseEntity<ResponseDTO> invalidSubscription(InvalidSubscriptionException ex){
        String status = "Already registered to this subscription!";

        return ResponseEntity
                .badRequest()
                .body(new ResponseDTO(status, ex.getMessage()));
    }
    @ExceptionHandler(value = {EmailSchedulingException.class})
    protected ResponseEntity<ResponseDTO> emailSchedulingFails(EmailSchedulingException ex){
        String status = "Something went wrong with scheduling an email!";

        return ResponseEntity
                .badRequest()
                .body(new ResponseDTO(status, ex.getMessage()));
    }
}
