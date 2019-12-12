package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SoapMessageListController {

    private static Logger log;

    private static final String URI_GET_ALL_MESSAGE = "/soapMessage/list/all";
    private static final String URI_GET_LAST_REQUEST_MESSAGE = "/soapMessage/list/lastRequest";
    private static final String URI_REMOVE_ALL_MESSAGE = "/soapMessage/remove/all";

    private ServiceController service;
    private SoapMessageList messageList;

    /*
        Constructor for tests
    */
    public SoapMessageListController(Logger logger, ServiceController serviceController, SoapMessageList messageList) {
        this.log = logger;
        this.service = serviceController;
        this.messageList = messageList;
    }

    @Autowired
    public SoapMessageListController(ServiceController serviceController, SoapMessageList messageList) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.service = serviceController;
        this.messageList = messageList;
    }

    @GetMapping(URI_GET_ALL_MESSAGE)
    @ResponseBody
    public ResponseBodyData getAllMessage(HttpSession httpSession) {
        log.info("Request uri='" + URI_GET_ALL_MESSAGE + "'");
        try {
            AppUser user = service.getUser(httpSession);
            return getSuccessResponseList();
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }

    @GetMapping(URI_GET_LAST_REQUEST_MESSAGE)
    @ResponseBody
    public ResponseBodyData getLastRequestMessage(HttpSession httpSession) {
        log.info("Request uri='" + URI_GET_LAST_REQUEST_MESSAGE + "'");
        try {
            AppUser user = service.getUser(httpSession);
            return getSuccessResponseLastRequest();
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }

    @GetMapping(URI_REMOVE_ALL_MESSAGE)
    @ResponseBody
    public ResponseBodyData removeAllMessage(HttpSession httpSession) {
        log.info("Request uri='" + URI_REMOVE_ALL_MESSAGE + "'");
        try {
            AppUser user = service.getUser(httpSession);
            clearSoapMessageList();
            return getSuccessResponseRemoveAllMessage();
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }

    private void clearSoapMessageList() {
        messageList.clear();
    }

    private ResponseBodyData getSuccessResponseLastRequest() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Last Request soap message list");
        result.setSoapMessageList(messageList.getLastRequestMessageList());

        log.info("Success request." + result.getLogInfo());
        return result;
    }

    private ResponseBodyData getSuccessResponseList() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Soap message list");
        result.setSoapMessageList(messageList.getMessageList());

        log.info("Success request." + result.getLogInfo());
        return result;
    }

    private ResponseBodyData getSuccessResponseRemoveAllMessage() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Remove soap message list");
        result.setSoapMessageList(messageList.getMessageList());

        log.info("Success request." + result.getLogInfo());
        return result;
    }

}
