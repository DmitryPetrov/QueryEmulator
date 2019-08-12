package com.emulator.domain.entity;

import com.emulator.exception.ParameterIsNullException;

public class AppUser{

    private final String userName;
    private final String password;
    private final String sessionId;

    public AppUser(String userName, String password, String sessionId) {
        if (userName == null) {
            throw new ParameterIsNullException("userName must not be 'null'");
        }
        if (password == null) {
            throw new ParameterIsNullException("password must not be 'null'");
        }
        if (sessionId == null) {
            throw new ParameterIsNullException("sessionId must not be 'null'");
        }

        this.userName = userName;
        this.password = password;
        this.sessionId = sessionId;
    }

    public AppUser(AppUser user, String sessionId) {
        if (user == null) {
            throw new ParameterIsNullException("user must not be 'null'");
        }
        if (sessionId == null) {
            throw new ParameterIsNullException("sessionId must not be 'null'");
        }
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSessionId() {
        return sessionId;
    }

}
