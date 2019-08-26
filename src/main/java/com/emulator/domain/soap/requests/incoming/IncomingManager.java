package com.emulator.domain.soap.requests.incoming;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.com.bssys.sbns.upg.ObjectFactory;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequests;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequestsResponse;
import com.emulator.domain.soap.requests.RequestMessageHandler;
import com.emulator.exception.SoapServerIncomingException;
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

        IncomingResult result = getResult(response);
        IncomingDto dto = data.getDto(result);
        return dto;
    }


    @Autowired
    private ObjectFactory factory;

    private JAXBElement<SendRequests> buildRequest(AppUser user, String message) {
        SendRequests request = factory.createSendRequests();
        request.setSessionId(user.getSessionId());
        request.getRequests().add("");

        return factory.createSendRequests(request);
    }

    private IncomingResult getResult(JAXBElement<SendRequestsResponse> response) {
        String responseMessage = "";
        for (String responseLine : response.getValue().getReturn()) {
            responseMessage += responseLine;
        }

        checkErrors(responseMessage);

        IncomingResult result = new IncomingResult(responseMessage);
        return result;
    }

    private void checkErrors(String response) {
        System.out.println("Incoming response: " + response);

        if ((response.contains("NONEXISTENT SESSION"))
                || (response.contains("Error"))) {
            handleError(response);
        }
    }


    @Autowired
    private SoapMessageList soapMessageList;

    private void handleError(String response) {
        String exceptionMessage = response;
        exceptionMessage += "\n>>>>SAOP Messages:";
        exceptionMessage += soapMessageList.getLastRequestAsString();

        SoapServerIncomingException exception = new SoapServerIncomingException(exceptionMessage);
        exception.setSoapMessages(soapMessageList.getLastRequestAsString());
        exception.setSoapResponse(response);
        throw exception;
    }

}
