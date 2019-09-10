package com.emulator.domain.soap.requests.getrequeststatus;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.com.bssys.sbns.upg.*;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.statementrequest.StatementRequestManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBElement;
import java.io.IOException;

@Component
public class GetRequestStatusManager {

    private static Logger log = LoggerFactory.getLogger(GetRequestStatusManager.class);

    @Autowired
    private ObjectFactory factory;

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public GetRequestStatusDto runGetRequestStatus(AppUser user, String responseId) throws IOException,
            SAXException {
        JAXBElement<GetRequestStatus> request = buildRequest(user, responseId);
        JAXBElement<GetRequestStatusResponse> response;

        log.debug("Send GetRequestStatus request");
        response = (JAXBElement<GetRequestStatusResponse>) webServiceTemplate.marshalSendAndReceive(request);

        return getGetRequestStatusResult(response).getDto();
    }

    private JAXBElement<GetRequestStatus> buildRequest(AppUser user, String responseId) {
        GetRequestStatus request = factory.createGetRequestStatus();
        request.setSessionId(user.getSessionId());
        request.getRequests().add(responseId);

        return factory.createGetRequestStatus(request);
    }

    @Autowired
    private ResponseHandler responseHandler;

    private GetRequestStatusResult getGetRequestStatusResult(JAXBElement<GetRequestStatusResponse> response)
            throws IOException, SAXException {
        return responseHandler.getResult(response);
    }


}
