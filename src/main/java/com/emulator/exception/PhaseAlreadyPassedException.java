package com.emulator.exception;

public class PhaseAlreadyPassedException extends RuntimeException{

    public PhaseAlreadyPassedException(String message) {
        super(message);
    }
}
