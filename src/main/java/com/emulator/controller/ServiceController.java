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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ServiceController {

    private static Logger log;
    private SoapMessageList messageList;

    /*
        Constructor for tests
    */
    public ServiceController(Logger logger, SoapMessageList messageList) {
        this.log = logger;
        this.messageList = messageList;
    }

    @Autowired
    public ServiceController(SoapMessageList messageList) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.messageList = messageList;
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
        result.setSoapMessageList(messageList.getMessageList());
        log.error("Server error." + result.getLogInfo());
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
        result.setSoapMessageList(messageList.getLastRequestMessageList());
        log.info("Failed request." + result.getLogInfo());
        return result;
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
        result.setSoapMessageList(messageList.getLastRequestMessageList());
        log.info("Success request." + result.getLogInfo());
        return result;
    }

    ResponseBodyData getSoapRequestSuccessResponse(AppUser user) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Authorization succeed. Session id=" + user.getSessionId());
        result.setSoapMessageList(messageList.getLastRequestMessageList());

        log.info("Success request." + result.getLogInfo());
        return result;
    }

    ResponseBodyData getSuccessResponseLastRequestSoapMessage() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Last Request soap message list");
        result.setSoapMessageList(messageList.getLastRequestMessageList());
        log.info("Success request." + result.getLogInfo());
        return result;
    }

    ResponseBodyData getSuccessResponseAllSoapMessage() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("All soap message list");
        result.setSoapMessageList(messageList.getMessageList());
        log.info("Success request." + result.getLogInfo());
        return result;
    }

    ResponseBodyData getSuccessResponseRemoveAllSoapMessage() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Remove soap message list");
        result.setSoapMessageList(messageList.getMessageList());
        log.info("Success request." + result.getLogInfo());
        return result;
    }

    void clearSoapMessageList() {
        messageList.clear();
    }
}
