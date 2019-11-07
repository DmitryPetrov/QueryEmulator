package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public abstract class AbstractController {

    private static Logger log = LoggerFactory.getLogger(AbstractController.class);

    protected abstract ResponseBodyData getSoapRequestSuccessResponse(RequestChain chain);

    protected abstract ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception,
                                                                   RequestChain chain);

    protected AppUser getUser(HttpSession httpSession) {
        AppUser user = (AppUser) httpSession.getAttribute("user");
        if (user == null) {
            throw new UserIsNotAuthorizedException("User is not authorized");
        }
        return user;
    }

    @Autowired
    SoapMessageList soapMessageList;

    protected ResponseBodyData getServerFailResponse(Exception exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage(exception.getMessage());
        result.setSoapMessageList(soapMessageList.getMessageList());

        log.error("Server error." + result.getLogInfo());
        return result;
    }

    protected ResponseBodyData getServerFailResponse(Exception exception, RequestChain chain) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage(exception.getMessage());
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        result.setRequestChain(chain);

        log.error("Server error." + result.getLogInfo());
        exception.printStackTrace();
        return result;
    }

    protected ResponseBodyData getParameterLengthErrorResponse(RequestParameterLengthException exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("Request parameters is invalid. Parameter '" + exception.getParameterName()
                + "' must be shorter than " + exception.getMaxLength() + " characters!");

        log.error("Length error in request parameter." + result.getLogInfo());
        return result;
    }

    protected ResponseBodyData getUserIsNotAuthorizedResponse() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("User is not authorized");

        log.error("User is not authorized." + result.getLogInfo());
        return result;
    }
}
