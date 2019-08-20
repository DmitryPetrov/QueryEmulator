package com.emulator.exception;

public class RequestChainIsNotExistException extends RuntimeException {

    public RequestChainIsNotExistException(String message) {
        super(message);
    }
}
