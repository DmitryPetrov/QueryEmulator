package com.emulator.controller;

import com.emulator.domain.frontend.SOAPConnectionStatus;
import com.emulator.domain.soap.exception.RequestParameterLengthException;
import com.emulator.domain.soap.exception.SOAPServerBadResponseException;

public abstract class AbstractController {

    protected abstract SOAPConnectionStatus getRequestSuccessResponse(String message);

    protected abstract SOAPConnectionStatus getRequestFailResponse(SOAPServerBadResponseException exception);

    protected SOAPConnectionStatus getParameterLengthErrorResponse(RequestParameterLengthException exception) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("ERROR");
        result.setMessage("StatementRequest request parameters is invalid. Parameter '" + exception.getParameterName()
                + "' must be shorter than " + exception.getMaxLength() + " characters!");
        return result;
    }

    protected SOAPConnectionStatus getUserIsNotAuthorizedResponse() {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("ERROR");
        result.setMessage("User is not authorized");
        return result;
    }
}
