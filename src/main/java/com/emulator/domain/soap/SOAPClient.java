package com.emulator.domain.soap;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.StatementRequestData;
import com.emulator.domain.soap.authorization.AuthorizationManager;
import com.emulator.domain.soap.exception.SOAPServerLoginException;
import com.emulator.domain.soap.exception.SOAPServerStatementRequestException;
import com.emulator.domain.soap.sendrequest.StatementRequestManager;
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
    StatementRequestManager statementRequestManager;

    public void sendStatementRequest(AppUser user, StatementRequestData data) throws SOAPServerStatementRequestException {
        statementRequestManager.runStatementRequest(user, data);
    }
}
