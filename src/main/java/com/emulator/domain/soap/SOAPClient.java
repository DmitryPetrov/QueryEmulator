package com.emulator.domain.soap;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.SendRequestRequest1Data;
import com.emulator.domain.soap.authorization.AuthorizationManager;
import com.emulator.domain.soap.exception.SOAPServerLoginException;
import com.emulator.domain.soap.exception.SOAPServerSendRequestRequest1Exception;
import com.emulator.domain.soap.sendrequest.SendRequestManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SOAPClient {

    @Autowired
    AuthorizationManager authManager;

    public AppUser authorization(String userName, String password) throws SOAPServerLoginException {
        return authManager.authorization(userName, password);
    }

    @Autowired
    SendRequestManager sendRequestManager;

    public void sendRequestRequest1(SendRequestRequest1Data data) throws SOAPServerSendRequestRequest1Exception {
        sendRequestManager.request1(data);

    }
}
