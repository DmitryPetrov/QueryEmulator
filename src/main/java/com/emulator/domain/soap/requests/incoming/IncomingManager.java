package com.emulator.domain.soap.requests.incoming;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.com.bssys.sbns.upg.ObjectFactory;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequests;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequestsResponse;
import com.emulator.domain.soap.requests.ErrorResponseHandler;
import com.emulator.domain.soap.requests.RequestMessageHandler;
import com.emulator.domain.soap.requests.statementrequest.StatementRequestManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;

@Component
public class IncomingManager {

    private static Logger log = LoggerFactory.getLogger(IncomingManager.class);

    private static final String NODE_NAME_WITH_REQUEST_MESSAGE = "ns2:requests";

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    @Qualifier("IncomingMessageBuilder")
    private MessageBuilder requestMessageBuilder;

    public IncomingDto runIncoming(AppUser user, IncomingData data) {
        log.debug("Build message for Incoming");
        String requestMessage = requestMessageBuilder.build(data);
        log.debug("Message was build. Message='" + requestMessage + "'");

        RequestMessageHandler handler = new RequestMessageHandler(NODE_NAME_WITH_REQUEST_MESSAGE, requestMessage);

        JAXBElement<SendRequests> request = buildRequest(user, requestMessage);
        JAXBElement<SendRequestsResponse> response = null;

        log.debug("Send StatementRequest request");
        response = (JAXBElement<SendRequestsResponse>) webServiceTemplate
                .marshalSendAndReceive(request, handler);

        String incomingResponseId = getResult(response);
        log.info("Handle Incoming response. Incoming responseId=" + incomingResponseId);
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
