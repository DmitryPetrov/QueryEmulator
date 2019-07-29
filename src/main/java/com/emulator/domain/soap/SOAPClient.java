package com.emulator.domain.soap;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.statementRequest.StatementRequestData;
import com.emulator.domain.soap.authorization.AuthorizationManager;
import com.emulator.domain.soap.exception.SOAPServerLoginException;
import com.emulator.domain.soap.exception.SOAPServerStatementRequestException;
import com.emulator.domain.soap.statementRequest.StatementRequestManager;
import com.emulator.domain.soap.statementRequest.StatementRequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SOAPClient {

    @Autowired
    AuthorizationManager authManager;

    public void authorization(AppUser user) throws SOAPServerLoginException {
        authManager.authorization(user);
    }

    @Autowired
    StatementRequestManager statementRequestManager;

    public StatementRequestResult sendStatementRequest(AppUser user, StatementRequestData data) throws SOAPServerStatementRequestException {
        return statementRequestManager.runStatementRequest(user, data);
    }
}
