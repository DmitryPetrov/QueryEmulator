package com.emulator.exception;

public class RequestChainPhaseNotReadyOrAlreadyPassedException extends RuntimeException{

    public RequestChainPhaseNotReadyOrAlreadyPassedException(String message) {
        super(message);
    }
}
