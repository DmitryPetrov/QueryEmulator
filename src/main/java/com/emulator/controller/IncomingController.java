package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class IncomingController {

    private static Logger log;

    private static final String URI = "/request/nextStep";
    private static final String REQUEST_NAME= "Incoming";

    private ServiceController service;
    private RequestChainPool chainPool;

    /*
        Constructor for tests
    */
    public IncomingController(Logger logger, RequestChainPool chainPool, ServiceController serviceController) {
        this.log = logger;
        this.chainPool = chainPool;
        this.service = serviceController;
    }

    @Autowired
    public IncomingController(RequestChainPool chainPool, ServiceController serviceController) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.chainPool = chainPool;
        this.service = serviceController;
    }

    @PostMapping("/request/nextStep")
    @ResponseBody
    public ResponseBodyData runIncoming(HttpSession httpSession, @RequestBody IncomingData data) {
        log.info("Request uri='" + URI + "' data='" + data.toString() + "'");
        RequestChain chain = null;
        try {
            AppUser user = service.getUser(httpSession);
            data.check();

            chain = chainPool.getRequestChain(user, data.getAttrRequestId());
            chain.nextStep(data);

            return service.getSoapRequestSuccessResponse(chain, REQUEST_NAME);
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (SoapServerBadResponseException e) {
            return service.getSoapRequestFailResponse(e, chain, REQUEST_NAME);
        } catch (RequestParameterLengthException e) {
            return service.getParameterLengthErrorResponse(e);
        } catch (Exception e) {
            return service.getServerFailResponse(e, chain);
        }
    }
}
