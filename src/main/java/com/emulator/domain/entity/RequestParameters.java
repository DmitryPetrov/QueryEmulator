package com.emulator.domain.entity;

import com.emulator.exception.RequestParameterLengthException;

public abstract class RequestParameters {

    private String requestId = "";

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    abstract public void check() throws RequestParameterLengthException;

    protected String checkNull(String value, String defaultValue) {
        if ((value == null) || (value.equals("(initialState)"))) {
            return defaultValue;
        }
        return value;
    }

    protected void checkStringLength(String paramName, String string, int maxLength) throws RequestParameterLengthException {
        if (string.length() > maxLength) {
            RequestParameterLengthException exception = new RequestParameterLengthException("String is too long");
            exception.setMaxLength(maxLength);
            exception.setParameterName(paramName);
            throw exception;
        }
    }
}
