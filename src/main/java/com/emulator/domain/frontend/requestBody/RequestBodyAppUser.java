package com.emulator.domain.frontend.requestBody;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.RequestParameters;
import com.emulator.exception.RequestParameterLengthException;

public class RequestBodyAppUser extends RequestParameters {

    private final String USERNAME_DEFAULT_VALUE = "testui";
    private final String PASSWORD_DEFAULT_VALUE = "ZWF5S2";

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

}
