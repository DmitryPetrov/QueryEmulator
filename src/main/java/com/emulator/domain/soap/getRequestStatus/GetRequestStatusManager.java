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
    ObjectFactory factory;

    @Autowired
    WebServiceTemplate webServiceTemplate;

    public GetRequestStatusResult runGetRequestStatus(AppUser user, String requestId) throws IOException,
            SAXException {
        JAXBElement<GetRequestStatus> request = buildRequest(user, requestId);
        JAXBElement<GetRequestStatusResponse> response;

        response = (JAXBElement<GetRequestStatusResponse>) webServiceTemplate.marshalSendAndReceive(request);

        return getGetRequestStatusResult(response);
    }

    private JAXBElement<GetRequestStatus> buildRequest(AppUser user, String requestId) {
        GetRequestStatus request = factory.createGetRequestStatus();
        request.setSessionId(user.getSessionId());
        request.getRequests().add(requestId);

        return factory.createGetRequestStatus(request);
    }

    @Autowired
    GetRequestStatusResponseHandler responseHandler;

    private GetRequestStatusResult getGetRequestStatusResult(JAXBElement<GetRequestStatusResponse> response)
            throws IOException, SAXException {
        responseHandler.setSoapResponse(response);
        return responseHandler.parse();
    }


}
