package com.emulator.exception;

public class RequestChainPhaseAlreadyPassedException extends RuntimeException {

    public RequestChainPhaseAlreadyPassedException(String message) {
        super(message);
    }
}
