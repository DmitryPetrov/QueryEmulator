package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.PayRequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.payrequest.PayRequestData;
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
public class PayRequestController {

    private static Logger log;

    private static final String URI = "/requests/payRequest";
    private static final String REQUEST_NAME= "PayRequest";

    private ServiceController service;
    private RequestChainPool chainPool;

    /*
        Constructor for tests
    */
    public PayRequestController(Logger logger, RequestChainPool chainPool, ServiceController serviceController) {
        this.log = logger;
        this.chainPool = chainPool;
        this.service = serviceController;
    }

    @Autowired
    public PayRequestController(RequestChainPool chainPool, ServiceController serviceController) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.chainPool = chainPool;
        this.service = serviceController;
    }

    @PostMapping(URI)
    @ResponseBody
    public ResponseBodyData runPayRequest(HttpSession httpSession, @RequestBody PayRequestData data) {
        log.info("Request uri='" + URI + "' data='" + data.toString() + "'");
        PayRequestChain chain = null;
        try {
            AppUser user = service.getUser(httpSession);
            data.check();

            chain = chainPool.createPayRequestChain(user);
            chain.nextStep(data);
            chainPool.addToPool(chain);

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
