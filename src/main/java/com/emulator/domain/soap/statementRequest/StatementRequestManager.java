package com.emulator.domain.soap.statementRequest;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.com.bssys.sbns.upg.ObjectFactory;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequests;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequestsResponse;
import com.emulator.domain.soap.exception.SOAPServerStatementRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;
import java.util.List;

@Component
public class StatementRequestManager {

    private static final String NODE_NAME_WITH_REQUEST_MESSAGE = "ns2:requests";

    @Autowired
    WebServiceTemplate webServiceTemplate;

    @Autowired
    StatementRequestMessageBuilder requestMessageBuilder;

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
    ObjectFactory factory;

    private JAXBElement<SendRequests> buildRequest(AppUser user) {
        SendRequests request = factory.createSendRequests();
        request.setSessionId(user.getSessionId());
        request.getRequests().add("");

        return factory.createSendRequests(request);
    }

    private StatementRequestResult getResult(JAXBElement<SendRequestsResponse> response) throws
            SOAPServerStatementRequestException {
        StatementRequestResult result = new StatementRequestResult();

        String responseMessage = "";
        for (String responseLine : response.getValue().getReturn()) {
            responseMessage += responseLine;
        }

        checkErrors(responseMessage);

        result.setRequestId(responseMessage);
        return result;
    }

    private void checkErrors(String response) throws SOAPServerStatementRequestException {
        System.out.println("StatementRequest response: " + response);

        if ((response.contains("NONEXISTENT SESSION"))
                || (response.contains("Error"))) {
            handleError(response);
        }
    }


    @Autowired
    private List<String> soapMassageTrace;

    private void handleError(String response) throws SOAPServerStatementRequestException {
        String soapMessages = "";
        for (String message : soapMassageTrace) {
            soapMessages += ("\n" + message);
        }
        soapMassageTrace.clear();

        String exceptionMessage = "";
        exceptionMessage += response;
        exceptionMessage += "\n>>>>SAOP Messages:";
        exceptionMessage += soapMessages;

        SOAPServerStatementRequestException exception = new SOAPServerStatementRequestException(exceptionMessage);
        exception.setSoapMessages(soapMessages);
        exception.setSoapResponse(response);
        throw exception;
    }


}
