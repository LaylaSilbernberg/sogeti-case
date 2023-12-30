package com.layla.filmlandbackend.exception;

public class NoDuplicateSubscriptionException extends IllegalArgumentException{

    public NoDuplicateSubscriptionException(String message){
        super(message);
    }
}
