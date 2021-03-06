package com.emulator.domain.soap.requests.authorization;

import com.emulator.exception.ParameterIsNullException;

import java.util.Objects;

public class AppUser{

    private final String userName;
    private final String password;
    private final String sessionId;

    public AppUser(String userName, String password, String sessionId) {
        if (userName == null) {
            throw new ParameterIsNullException("AppUser.userName must not be 'null'");
        }
        if (password == null) {
            throw new ParameterIsNullException("AppUser.password must not be 'null'");
        }
        if (sessionId == null) {
            throw new ParameterIsNullException("AppUser.sessionId must not be 'null'");
        }

        this.userName = userName;
        this.password = password;
        this.sessionId = sessionId;
    }

    public AppUser(AppUser user, String sessionId) {
        if (user == null) {
            throw new ParameterIsNullException("AppUser.AppUser must not be 'null'");
        }
        if (sessionId == null) {
            throw new ParameterIsNullException("AppUser.sessionId must not be 'null'");
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(userName, appUser.userName) &&
                Objects.equals(password, appUser.password) &&
                Objects.equals(sessionId, appUser.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, sessionId);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
