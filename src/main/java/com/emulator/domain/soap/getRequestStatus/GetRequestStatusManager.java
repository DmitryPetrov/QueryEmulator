package com.emulator.domain.soap.getrequeststatus;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.com.bssys.sbns.upg.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBElement;
import java.io.IOException;

@Component
public class GetRequestStatusManager {

    @Autowired
    private ObjectFactory factory;

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public GetRequestStatusResult runGetRequestStatus(AppUser user, String responseId) throws IOException,
            SAXException {
        JAXBElement<GetRequestStatus> request = buildRequest(user, responseId);
        JAXBElement<GetRequestStatusResponse> response;

        response = (JAXBElement<GetRequestStatusResponse>) webServiceTemplate.marshalSendAndReceive(request);

        return getGetRequestStatusResult(response);
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
