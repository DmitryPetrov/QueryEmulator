package com.emulator.domain.soap.requests.payrequest;

import com.emulator.domain.soap.com.bssys.sbns.upg.ObjectFactory;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequests;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequestsResponse;
import com.emulator.domain.soap.requests.ErrorResponseHandler;
import com.emulator.domain.soap.requests.RequestMessageHandler;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.payrequest.dto.PayRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;

@Component
public class PayRequestManager {

    private static Logger log = LoggerFactory.getLogger(PayRequestManager.class);
    private static final String NODE_NAME_WITH_REQUEST_MESSAGE = "ns2:requests";

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    @Qualifier("PayRequestMessageBuilder")
    private MessageBuilder requestMessageBuilder;

    public PayRequestDto runPayRequest(AppUser user, PayRequestData data) {
        log.debug("Build message for PayRequest");
        String requestMessage = requestMessageBuilder.build(data);
        log.debug("Message was build. Message='" + requestMessage.replaceAll("\r\n", "") + "'");

        RequestMessageHandler handler = getMessageHandler(requestMessage);

        JAXBElement<SendRequests> request = buildRequest(user);
        JAXBElement<SendRequestsResponse> response = null;

        log.debug("Send PayRequest request");
        response = (JAXBElement<SendRequestsResponse>) webServiceTemplate
                .marshalSendAndReceive(request, handler);

        String responseId = getResult(response);
        log.info("Handle PayRequest response. ResponseId=" + responseId);
        return data.getDto(responseId);
    }

    private RequestMessageHandler getMessageHandler(String requestMessage) {
        return new RequestMessageHandler(NODE_NAME_WITH_REQUEST_MESSAGE, requestMessage);
    }

    @Autowired
    private ObjectFactory factory;

    private JAXBElement<SendRequests> buildRequest(AppUser user) {
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
