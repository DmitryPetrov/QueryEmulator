package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.getrequeststatus.GetRequestStatusData;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class GetRequestStatusController {

    private static Logger log;

    private static final String URI = "/requests/getRequestStatus";
    private static final String REQUEST_NAME= "GetRequestStatus";

    private ServiceController service;
    private RequestChainPool chainPool;

    /*
        Constructor for tests
    */
    public GetRequestStatusController(Logger logger, RequestChainPool chainPool, ServiceController serviceController) {
        this.log = logger;
        this.chainPool = chainPool;
        this.service = serviceController;
    }

    @Autowired
    public GetRequestStatusController(RequestChainPool chainPool, ServiceController serviceController) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.chainPool = chainPool;
        this.service = serviceController;
    }

    @PostMapping(URI)
    @ResponseBody
    public ResponseBodyData runGetRequestStatus(HttpSession httpSession, @RequestBody GetRequestStatusData data) {
        log.info("Request uri='" + URI + "' RequestParam: responseId='" + data.getResponseId() + "'");
        RequestChain chain = null;
        try {
            AppUser user = service.getUser(httpSession);

            chain = chainPool.getRequestChain(user, data);
            chain.nextStep();

            return service.getSoapRequestSuccessResponse(chain, REQUEST_NAME);
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (SoapServerBadResponseException e) {
            return service.getSoapRequestFailResponse(e, chain, REQUEST_NAME);
        } catch (Exception e) {
            return service.getServerFailResponse(e, chain);
        }
    }
}
