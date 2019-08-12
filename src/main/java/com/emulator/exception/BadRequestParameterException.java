package com.emulator.exception;

public class BadRequestParameterException extends RuntimeException {

    private String parameterName;
    private String message;

    public BadRequestParameterException(String message) {
        super(message);
        this.message = message;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getMessage() {
        return this.message;
    }
}
