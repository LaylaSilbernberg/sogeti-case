package com.layla.filmlandbackend.exception;

public class UserAlreadyExistsException extends IllegalArgumentException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
