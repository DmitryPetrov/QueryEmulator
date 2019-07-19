package com.emulator.domain.soap.exception;

public class SOAPServerLoginException extends Exception {

    private String message;

    public SOAPServerLoginException(String message) {
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