package com.emulator.domain.entity;

import com.emulator.domain.soap.authorization.login.LoginResult;

public class AppUser {

    private String userName;
    private String password;
    private String sessionId;

    public AppUser(String name, String password) {
        this.userName = name;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
