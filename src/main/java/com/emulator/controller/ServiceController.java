package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.soap.SoapMessageStorage;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Service
public class ServiceController {

    private static Logger log;
    private SoapMessageStorage messageStorage;

    /*
        Constructor for tests
    */
    public ServiceController(Logger logger, SoapMessageStorage messageStorage) {
        this.log = logger;
        this.messageStorage = messageStorage;
    }

    @Autowired
    public ServiceController(SoapMessageStorage messageStorage) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.messageStorage = messageStorage;
    }

    AppUser getUser(HttpSession httpSession) {
        AppUser user = (AppUser) httpSession.getAttribute("user");
        if (user == null) {
            throw new UserIsNotAuthorizedException("User is not authorized");
        }
        return user;
    }

    ResponseBodyData getUserIsNotAuthorizedResponse() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("User is not authorized.");
        log.error("User is not authorized." + result.getLogInfo());
        return result;
    }

    ResponseBodyData getParameterLengthErrorResponse(RequestParameterLengthException exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("Request parameters is invalid. Parameter '" + exception.getParameterName()
                + "' must be shorter than " + exception.getMaxLength() + " characters!");
        log.error("Length error in request parameter." + result.getLogInfo());
        return result;
    }

    ResponseBodyData getServerFailResponse(Exception exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage(exception.getMessage());
        result.setSoapMessageList(messageStorage.getLastRequestMessageList());
        log.error("Server error." + result.getLogInfo());
        log.error(getStackTrace(exception));
        return result;
    }

    ResponseBodyData getServerFailResponse(Exception exception, RequestChain chain) {
        ResponseBodyData result = getServerFailResponse(exception);
        result.setRequestChain(chain);
        return result;
    }

    ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception, String requestName) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage(requestName + " to Soap server failed. Message: " + exception.getSoapResponse());
        result.setSoapMessageList(messageStorage.getLastRequestMessageList());
        log.info("Failed request." + result.getLogInfo());
        log.info(getStackTrace(exception));
        return result;
    }

    private String getStackTrace(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception, RequestChain chain,
                                                String requestName) {
        ResponseBodyData result = getSoapRequestFailResponse(exception, requestName);
        result.setRequestChain(chain);
        return result;
    }

    ResponseBodyData getSuccessResponse(List<RequestChain> chainList) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Request list");
        result.setRequestChainList(chainList);
        log.info("Success request." + result.getLogInfo());
        return result;
    }

    ResponseBodyData getSoapRequestSuccessResponse(RequestChain chain, String requestName) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage(requestName + " to Soap server succeed. Request id=" + chain.getResponseId());
        result.setSoapMessageList(messageStorage.getLastRequestMessageList());
        result.setRequestChain(chain);
        log.info("Success request." + result.getLogInfo());
        return result;
    }

}
