package com.emulator.domain.soap.getRequestStatus;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.com.bssys.sbns.upg.*;
import com.emulator.exception.SOAPServerGetRequestStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;
import java.util.List;

@Component
public class GetRequestStatusManager {

    @Autowired
    ObjectFactory factory;

    @Autowired
    WebServiceTemplate webServiceTemplate;

    @Autowired
    private List<String> soapMassageTrace;

    public GetRequestStatusResult runGetRequestStatus(AppUser user, String requestId) throws
            SOAPServerGetRequestStatusException {

        GetRequestStatus getRequestStatus = factory.createGetRequestStatus();
        getRequestStatus.setSessionId(user.getSessionId());
        List<String> requestData = getRequestStatus.getRequests();
        requestData.add(requestId);

        JAXBElement<GetRequestStatus> getRequestStatusElement = factory.createGetRequestStatus(getRequestStatus);
        JAXBElement<GetRequestStatusResponse> getRequestStatusResponseElement;

        getRequestStatusResponseElement = (JAXBElement<GetRequestStatusResponse>) webServiceTemplate
                .marshalSendAndReceive(getRequestStatusElement);
        GetRequestStatusResponse getRequestStatusResponse = getRequestStatusResponseElement.getValue();

        return getGetRequestStatusResult(getRequestStatusResponse);
    }

    private GetRequestStatusResult getGetRequestStatusResult(GetRequestStatusResponse response) throws SOAPServerGetRequestStatusException {
        GetRequestStatusResult result = new GetRequestStatusResult();

        String responseStr = response.getReturn().get(0);

        if (true/*responseStr.contains("NONEXISTENT SESSION")*/)
        {
            String soapMessages = "";
            for (String message : soapMassageTrace) {
                message = message.replaceAll("&lt;", "<br/>&lt;");
                message = message.replaceAll("&gt;", ">");
                soapMessages += ("\n" + message);
            }
            soapMassageTrace.clear();

            String exceptionMessage = "";
            exceptionMessage += responseStr;
            exceptionMessage += "\n>>>>SAOP Messages:";
            exceptionMessage += soapMessages;

            SOAPServerGetRequestStatusException exception = new SOAPServerGetRequestStatusException(exceptionMessage);
            exception.setSoapMessages(soapMessages);
            exception.setSoapResponse(responseStr);
            throw exception;
        }

        result.setSoapResponse(responseStr);
        return result;
    }
}
