package com.emulator.exception;

public class UserIsNotAuthorizedException extends RuntimeException {

    public UserIsNotAuthorizedException(String message) {
        super(message);
    }
}
