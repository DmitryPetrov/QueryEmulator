package com.emulator.domain.soap;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.authorization.AuthorizationManager;
import com.emulator.exception.SOAPServerGetRequestStatusException;
import com.emulator.exception.SOAPServerLoginException;
import com.emulator.exception.SOAPServerStatementRequestException;
import com.emulator.domain.soap.getRequestStatus.GetRequestStatusManager;
import com.emulator.domain.soap.getRequestStatus.GetRequestStatusResult;
import com.emulator.domain.soap.statementRequest.StatementRequestData;
import com.emulator.domain.soap.statementRequest.StatementRequestManager;
import com.emulator.domain.soap.statementRequest.StatementRequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public GetRequestStatusResult sendGetRequestStatus(AppUser user, String requestId) {
        return getRequestStatusManager.runGetRequestStatus(user, requestId);
    }
}
