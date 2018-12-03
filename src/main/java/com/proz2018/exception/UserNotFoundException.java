package com.proz2018.exception;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super("Could not find user " + id);
    }
}