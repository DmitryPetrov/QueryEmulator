package com.emulator.domain.soap.statementrequest;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.com.bssys.sbns.upg.ObjectFactory;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequests;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequestsResponse;
import com.emulator.exception.SOAPServerStatementRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;

@Component
public class StatementRequestManager {

    private static final String NODE_NAME_WITH_REQUEST_MESSAGE = "ns2:requests";

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    private StatementRequestMessageBuilder requestMessageBuilder;

    public StatementRequestResult runStatementRequest(AppUser user, StatementRequestData data) throws
            SOAPServerStatementRequestException {
        String statementRequestMessage = requestMessageBuilder.build(data);

        MessageHandler messageHandler = new MessageHandler(NODE_NAME_WITH_REQUEST_MESSAGE, statementRequestMessage);

        JAXBElement<SendRequests> request = buildRequest(user);
        JAXBElement<SendRequestsResponse> response = null;

        response = (JAXBElement<SendRequestsResponse>) webServiceTemplate
                .marshalSendAndReceive(request, messageHandler);

        return getResult(response);
    }


    @Autowired
    private ObjectFactory factory;

    private JAXBElement<SendRequests> buildRequest(AppUser user) {
        SendRequests request = factory.createSendRequests();
        request.setSessionId(user.getSessionId());
        request.getRequests().add("");

        return factory.createSendRequests(request);
    }

    private StatementRequestResult getResult(JAXBElement<SendRequestsResponse> response) throws
            SOAPServerStatementRequestException {
        String responseMessage = "";
        for (String responseLine : response.getValue().getReturn()) {
            responseMessage += responseLine;
        }

        checkErrors(responseMessage);

        StatementRequestResult result = new StatementRequestResult();
        result.setRequestId(responseMessage);
        return result;
    }

    private void checkErrors(String response) {
        System.out.println("StatementRequest response: " + response);

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
        exceptionMessage += soapMessageList.getAsString();

        SOAPServerStatementRequestException exception = new SOAPServerStatementRequestException(exceptionMessage);
        exception.setSoapMessages(soapMessageList.getAsString());
        exception.setSoapResponse(response);
        throw exception;
    }


}
