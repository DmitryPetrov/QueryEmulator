package com.emulator.domain.soap.requests;

import com.emulator.exception.RequestParameterLengthException;

public abstract class RequestParameters {

    private String requestId = "";
    private String requestName = "";

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    abstract public void check();

    protected String checkNull(String value, String defaultValue) {
        if ((value == null) || (value.equals("(initialState)"))) {
            return defaultValue;
        }
        return value;
    }

    protected void checkStringLength(String paramName, String string, int maxLength) {
        if (string.length() > maxLength) {
            RequestParameterLengthException exception = new RequestParameterLengthException("String is too long");
            exception.setMaxLength(maxLength);
            exception.setParameterName(paramName);
            throw exception;
        }
    }
}
