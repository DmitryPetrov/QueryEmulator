package com.emulator.domain.entity;

import com.emulator.domain.soap.authorization.login.LoginResult;
import com.emulator.domain.soap.exception.RequestParameterLengthException;

public class AppUser extends RequestParameters {

    private final String USERNAME_DEFAULT_VALUE = "testui";
    private final String PASSWORD_DEFAULT_VALUE = "L8UWRF";

    private String userName = "";
    private String password = "";
    private String sessionId = "";

    public AppUser() {

    }

    @Override
    public void check() throws RequestParameterLengthException {
        checkStringLength("userName", this.userName, 255);
        checkStringLength("password", this.password, 255);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = checkNull(userName, USERNAME_DEFAULT_VALUE);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = checkNull(password, PASSWORD_DEFAULT_VALUE);
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
