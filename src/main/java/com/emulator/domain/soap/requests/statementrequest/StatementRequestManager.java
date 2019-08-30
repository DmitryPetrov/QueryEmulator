package com.emulator.domain.soap.requests.statementrequest;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.com.bssys.sbns.upg.ObjectFactory;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequests;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequestsResponse;
import com.emulator.domain.soap.requests.ErrorResponseHandler;
import com.emulator.domain.soap.requests.RequestMessageHandler;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;

@Component
public class StatementRequestManager {

    private static final String NODE_NAME_WITH_REQUEST_MESSAGE = "ns2:requests";

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    @Qualifier("StatementRequestMessageBuilder")
    private MessageBuilder requestMessageBuilder;

    public StatementRequestDto runStatementRequest(AppUser user, StatementRequestData data) {
        String statementRequestMessage = requestMessageBuilder.build(data);

        RequestMessageHandler requestMessageHandler = new RequestMessageHandler(NODE_NAME_WITH_REQUEST_MESSAGE,
                statementRequestMessage);

        JAXBElement<SendRequests> request = buildRequest(user);
        JAXBElement<SendRequestsResponse> response = null;

        response = (JAXBElement<SendRequestsResponse>) webServiceTemplate
                .marshalSendAndReceive(request, requestMessageHandler);

        StatementRequestResult result = getResult(response);
        return data.getDto(result);
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

    private StatementRequestResult getResult(JAXBElement<SendRequestsResponse> response) {
        String responseMessage = "";
        for (String responseLine : response.getValue().getReturn()) {
            responseMessage += responseLine;
        }

        errorHandler.check(responseMessage);

        StatementRequestResult result = new StatementRequestResult(responseMessage);
        return result;
    }
}
