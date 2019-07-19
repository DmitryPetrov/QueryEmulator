package com.emulator.domain.soap;

import com.emulator.domain.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SOAPClient {

    @Autowired
    AuthorizationManager authManager;

    public AppUser authorization(String userName, String password) {
        return authManager.authorization(userName, password);
    }

}
