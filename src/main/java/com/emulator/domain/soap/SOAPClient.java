package com.emulator.domain.soap;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.exception.SOAPServerLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SOAPClient {

    @Autowired
    AuthorizationManager authManager;

    public AppUser authorization(String userName, String password) throws SOAPServerLoginException {
        return authManager.authorization(userName, password);
    }

}
