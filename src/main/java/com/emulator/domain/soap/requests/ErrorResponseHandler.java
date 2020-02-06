package com.emulator.domain.soap.requests;

import com.emulator.domain.soap.SoapMessageStorage;
import com.emulator.exception.SoapServerBadResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseHandler {

    private static Logger log = LoggerFactory.getLogger(ErrorResponseHandler.class);

    public void check(String response) {
        log.debug("Check response on error");

        if ((response.contains("!--BAD_CREDENTIALS--"))
                || (response.contains("!--UNKNOWN REQUEST--"))
                || (response.contains("!--NONEXISTENT SESSION--"))
                || (response.contains("SYSTEM_ERROR"))
                || (response.contains("</upg:Error>"))) {
            handleError(response);
        }
    }

    @Autowired
    private SoapMessageStorage storage;

    private void handleError(String response) {
        String exceptionMessage = response;
        exceptionMessage += "\n>SAOP Messages:";
        exceptionMessage += storage.getMessageListAsString();

        SoapServerBadResponseException exception = new SoapServerBadResponseException(exceptionMessage);
        exception.setSoapMessageList(storage.getMessageList());
        exception.setSoapResponse(response);
        throw exception;
    }
}
