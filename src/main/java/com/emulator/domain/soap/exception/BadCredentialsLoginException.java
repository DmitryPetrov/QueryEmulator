package com.emulator.domain.soap.exception;

public class BadCredentialsLoginException extends Exception {

    private String message;

    public BadCredentialsLoginException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
