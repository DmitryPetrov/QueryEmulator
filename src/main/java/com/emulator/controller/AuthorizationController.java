package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.authorization.AppUserData;
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

    private static final String URI = "/authorization";
    private static final String REQUEST_NAME= "Authorization";

    private static Logger log;
    private ServiceController service;
    private SoapClient soapClient;

    /*
        Constructor for tests
    */
    public AuthorizationController(Logger logger, SoapClient soapClient, ServiceController serviceController) {
        this.log = logger;
        this.soapClient = soapClient;
        this.service = serviceController;
    }

    @Autowired
    public AuthorizationController(SoapClient soapClient, ServiceController serviceController) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.soapClient = soapClient;
        this.service = serviceController;
    }

    @PostMapping(URI)
    @ResponseBody
    public ResponseBodyData login(HttpSession session, @RequestBody AppUserData data) {
        log.info("Request uri='" + URI + "' data='" + data.toString() + "'");
        try {
            data.check();

            AppUser user = (AppUser) session.getAttribute("user");
            if (user == null) {
                user = soapClient.authorization(data.getAppUser());

                log.debug("User was authorized. " + user);

                session.setAttribute("user", user);
            }

            return service.getSoapRequestSuccessResponse(user);
        } catch (SoapServerBadResponseException e) {
            return service.getSoapRequestFailResponse(e, REQUEST_NAME);
        } catch (RequestParameterLengthException e) {
            return service.getParameterLengthErrorResponse(e);
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }
}
