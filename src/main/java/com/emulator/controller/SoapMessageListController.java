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

    private static final String URI_GET_ALL_MESSAGE = "/soapMessage/list/all";
    private static final String URI_GET_LAST_REQUEST_MESSAGE = "/soapMessage/list/lastRequest";
    private static final String URI_REMOVE_ALL_MESSAGE = "/soapMessage/remove/all";

    private static Logger log;
    private ServiceController service;

    /*
        Constructor for tests
    */
    public SoapMessageListController(Logger logger, ServiceController serviceController) {
        this.log = logger;
        this.service = serviceController;
    }

    @Autowired
    public SoapMessageListController(ServiceController serviceController) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.service = serviceController;
    }

    @GetMapping(URI_GET_ALL_MESSAGE)
    @ResponseBody
    public ResponseBodyData getAllMessage(HttpSession httpSession) {
        log.info("Request uri='" + URI_GET_ALL_MESSAGE + "'");
        try {
            AppUser user = service.getUser(httpSession);
            return service.getSuccessResponseAllSoapMessage();
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
            return service.getSuccessResponseLastRequestSoapMessage();
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
            service.clearSoapMessageList();
            return service.getSuccessResponseRemoveAllSoapMessage();
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }
}
