package com.emulator.domain.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Client {

    @Autowired
    AuthorizationManager authManager;

    public Client() {
        super();
    }

    public String authorization(String userName, String password) {
        return authManager.authorization(userName, password);
    }

}
