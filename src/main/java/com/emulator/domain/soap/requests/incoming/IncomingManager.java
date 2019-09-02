package com.emulator.domain.soap.requests.incoming;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.com.bssys.sbns.upg.ObjectFactory;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequests;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequestsResponse;
import com.emulator.domain.soap.requests.ErrorResponseHandler;
import com.emulator.domain.soap.requests.RequestMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;

@Component
public class IncomingManager {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    @Qualifier("IncomingMessageBuilder")
    private MessageBuilder requestMessageBuilder;

    public IncomingDto runIncoming(AppUser user, IncomingData data) {
        String requestMessage = requestMessageBuilder.build(data);

        RequestMessageHandler requestMessageHandler = new RequestMessageHandler("ns2:requests", requestMessage);

        JAXBElement<SendRequests> request = buildRequest(user, requestMessage);
        JAXBElement<SendRequestsResponse> response = null;

        response = (JAXBElement<SendRequestsResponse>) webServiceTemplate
                .marshalSendAndReceive(request, requestMessageHandler);

        String incomingResponseId = getResult(response);
        return data.getDto(incomingResponseId);
    }


    @Autowired
    private ObjectFactory factory;

    private JAXBElement<SendRequests> buildRequest(AppUser user, String message) {
        SendRequests request = factory.createSendRequests();
        request.setSessionId(user.getSessionId());
        request.getRequests().add("");

        return factory.createSendRequests(request);
    }

    @Autowired
    private ErrorResponseHandler errorHandler;

    private String getResult(JAXBElement<SendRequestsResponse> response) {
        String responseMessage = "";
        for (String responseLine : response.getValue().getReturn()) {
            responseMessage += responseLine;
        }
        errorHandler.check(responseMessage);

        return responseMessage;
    }

}
