package com.emulator.domain.soap.requests.authorization;

import com.emulator.domain.soap.requests.RequestParameters;
import com.emulator.exception.RequestParameterLengthException;

public class AppUserData extends RequestParameters {

    private final String USERNAME_DEFAULT_VALUE = "testui";
    private final String PASSWORD_DEFAULT_VALUE = "9ULT45";

    private String userName = "";
    private String password = "";

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

    public AppUser getAppUser() {
        return new AppUser(this.userName, this.password, "");
    }

    @Override
    public String toString() {
        return "AppUserData{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
