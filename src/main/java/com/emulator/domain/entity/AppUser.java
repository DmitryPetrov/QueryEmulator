package com.emulator.domain.entity;

import com.emulator.domain.soap.authorization.login.LoginResult;
import com.emulator.domain.soap.exception.RequestParameterLengthException;

public class AppUser implements RequestParameters {

    private final String USERNAME_DEFAULT_VALUE = "testui";
    private final String PASSWORD_DEFAULT_VALUE = "L8UWRF";

    private String userName = "";
    private String password = "";
    private String sessionId = "";

    public AppUser() {

    }

    private String validate(String value, String defaultValue) {
        if ((value == null) || (value.equals("(initialState)"))) {
            return defaultValue;
        }
        return value;
    }

    private void validateStringLength(String paramName, String string, int maxLength) throws RequestParameterLengthException {
        if (string.length() > maxLength) {
            RequestParameterLengthException exception = new RequestParameterLengthException("String is too long");
            exception.setMaxLength(maxLength);
            exception.setParameterName(paramName);
            throw exception;
        }
    }

    @Override
    public void check() throws RequestParameterLengthException {
        validateStringLength("userName", this.userName, 255);
        validateStringLength("password", this.password, 255);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = validate(userName, USERNAME_DEFAULT_VALUE);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = validate(password, PASSWORD_DEFAULT_VALUE);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setSessionId(LoginResult loginResult) {
        this.sessionId = loginResult.getSessionId();
    }
}
