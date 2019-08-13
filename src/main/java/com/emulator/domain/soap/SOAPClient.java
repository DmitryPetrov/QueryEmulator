package com.emulator.domain.soap;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.authorization.AuthorizationManager;
import com.emulator.domain.soap.getrequeststatus.GetRequestStatusManager;
import com.emulator.domain.soap.getrequeststatus.GetRequestStatusResult;
import com.emulator.domain.soap.statementrequest.StatementRequestData;
import com.emulator.domain.soap.statementrequest.StatementRequestManager;
import com.emulator.domain.soap.statementrequest.StatementRequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;

@Component
public class SOAPClient {

    @Autowired
    AuthorizationManager authManager;

    public AppUser authorization(AppUser user) throws IOException {
        return authManager.authorization(user);
    }

    @Autowired
    StatementRequestManager statementRequestManager;

    public StatementRequestResult sendStatementRequest(AppUser user, StatementRequestData data) {
        return statementRequestManager.runStatementRequest(user, data);
    }

    @Autowired
    GetRequestStatusManager getRequestStatusManager;

    public GetRequestStatusResult sendGetRequestStatus(AppUser user, String requestId) throws IOException,
            SAXException {
        return getRequestStatusManager.runGetRequestStatus(user, requestId);
    }
}
