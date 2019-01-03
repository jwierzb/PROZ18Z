package com.proz2018.exception;


public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String username) {
        super("Invalid password or username.");
    }
}