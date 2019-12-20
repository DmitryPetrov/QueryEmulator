package com.emulator.exception;

public class RequestChainPhaseNotReadyException extends RuntimeException {

    public RequestChainPhaseNotReadyException(String message) {
        super(message);
    }
}
