package com.emulator.domain.entity;

import com.emulator.domain.soap.exception.RequestParameterLengthException;

public abstract class RequestParameters {

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
