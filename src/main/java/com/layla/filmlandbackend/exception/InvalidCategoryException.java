package com.layla.filmlandbackend.exception;

public class InvalidCategoryException extends IllegalArgumentException{
    public InvalidCategoryException(String message){
        super(message);
    }
}
