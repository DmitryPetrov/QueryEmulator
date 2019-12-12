package com.emulator.controller;

import com.emulator.domain.frontend.request.RequestBodyAppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class AuthorizationController {

    private static Logger log;

    private static final String URI = "/login";

    private ServiceController service;
    private SoapMessageList messageList;
    private SoapClient soapClient;

    /*
        Constructor for tests
    */
    public AuthorizationController(Logger logger, SoapClient soapClient, ServiceController serviceController,
                                   SoapMessageList messageList) {
        this.log = logger;
        this.soapClient = soapClient;
        this.service = serviceController;
        this.messageList = messageList;
    }

    @Autowired
    public AuthorizationController(SoapClient soapClient, ServiceController serviceController,
                                   SoapMessageList messageList) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.soapClient = soapClient;
        this.service = serviceController;
        this.messageList = messageList;
    }

    @PostMapping(URI)
    @ResponseBody
    public ResponseBodyData login(HttpSession httpSession, @RequestBody RequestBodyAppUser requestData) {
        log.info("Request uri='" + URI + "' data='" + requestData.toString() + "'");
        try {
            requestData.check();

            AppUser user = requestData.getAppUser();
            AppUser authorizedUser = soapClient.authorization(user);

            log.debug("User was authorized. " + authorizedUser);

            httpSession.setAttribute("user", authorizedUser);

            return getSoapRequestSuccessResponse(authorizedUser.getSessionId());
        } catch (SoapServerBadResponseException e) {
            return getSoapRequestFailResponse(e, null);
        } catch (RequestParameterLengthException e) {
            return service.getParameterLengthErrorResponse(e);
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }

    private ResponseBodyData getSoapRequestSuccessResponse(String sessionId) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Authorization succeed. Session id=" + sessionId);
        result.setSoapMessageList(messageList.getLastRequestMessageList());

        log.info("Success request." + result.getLogInfo());
        return result;
    }

    private ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception, RequestChain chain) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("LogIn to Soap server failed. Message: " + exception.getSoapResponse());
        result.setSoapMessageList(messageList.getLastRequestMessageList());

        log.info("Failed request." + result.getLogInfo());
        return result;
    }

}
