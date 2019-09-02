package com.emulator.domain.soap;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.requests.authorization.AuthorizationManager;
import com.emulator.domain.soap.requests.getrequeststatus.GetRequestStatusManager;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.incoming.IncomingManager;
import com.emulator.domain.soap.requests.statementrequest.StatementRequestData;
import com.emulator.domain.soap.requests.statementrequest.StatementRequestManager;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;

@Component
public class SoapClient {

    @Autowired
    AuthorizationManager authManager;

    public AppUser authorization(AppUser user) throws IOException {
        return authManager.authorization(user);
    }

    @Autowired
    StatementRequestManager statementRequestManager;

    public StatementRequestDto sendStatementRequest(AppUser user, StatementRequestData data) {
        return statementRequestManager.runStatementRequest(user, data);
    }

    @Autowired
    GetRequestStatusManager getRequestStatusManager;

    public GetRequestStatusDto sendGetRequestStatus(AppUser user, String requestId) throws IOException,
            SAXException {
        return getRequestStatusManager.runGetRequestStatus(user, requestId);
    }

    @Autowired
    IncomingManager incomingManager;

    public IncomingDto sendIncoming(AppUser user, IncomingData data) {
        return incomingManager.runIncoming(user, data);
    }
}
