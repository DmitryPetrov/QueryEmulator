package com.emulator.domain.soap.requests;

import com.emulator.domain.soap.SoapMessageList;
import com.emulator.exception.SoapServerBadResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseHandler {

    public void check(String response) {
        if ((response.contains("!--BAD_CREDENTIALS--"))
                || (response.contains("!--UNKNOWN REQUEST--"))
                || (response.contains("!--NONEXISTENT SESSION--"))
                || (response.contains("</upg:Error>"))) {
            handleError(response);
        }
    }

    @Autowired
    private SoapMessageList soapMessageList;

    private void handleError(String response) {
        String exceptionMessage = response;
        exceptionMessage += "\n>>>>SAOP Messages:";
        exceptionMessage += soapMessageList.getMessageListAsString();

        SoapServerBadResponseException exception = new SoapServerBadResponseException(exceptionMessage);
        exception.setSoapMessageList(soapMessageList.getMessageList());
        exception.setSoapResponse(response);
        throw exception;
    }
}
