package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.requestchain.RequestChain;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

    protected abstract ResponseBodyData getSoapRequestSuccessResponse(RequestChain chain);

    protected abstract ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception);

    @Autowired
    SoapMessageList soapMessageList;

    protected ResponseBodyData getServerFailResponse(Exception exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage(exception.getMessage());
        result.setSoapMessageList(soapMessageList.getMessageList());
        return result;
    }

    protected ResponseBodyData getParameterLengthErrorResponse(RequestParameterLengthException exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("Request parameters is invalid. Parameter '" + exception.getParameterName()
                + "' must be shorter than " + exception.getMaxLength() + " characters!");
        return result;
    }

    protected ResponseBodyData getUserIsNotAuthorizedResponse() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("User is not authorized");
        return result;
    }
}
