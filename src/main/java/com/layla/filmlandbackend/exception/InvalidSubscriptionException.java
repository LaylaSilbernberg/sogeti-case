package com.layla.filmlandbackend.exception;

public class InvalidSubscriptionException extends IllegalArgumentException {

    public InvalidSubscriptionException(String message) {
        super(message);
    }
}
