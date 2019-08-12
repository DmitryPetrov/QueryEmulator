package com.emulator.exception;

public class RequestParameterLengthException extends RuntimeException {

    private String parameterName;
    private int maxLength;

    public RequestParameterLengthException(String message) {
        super(message);
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
