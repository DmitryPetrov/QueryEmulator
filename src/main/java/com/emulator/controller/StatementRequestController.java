package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.domain.requestchain.StatementRequestChain;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.statementrequest.StatementRequestData;
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
public class StatementRequestController {

    private static Logger log;

    private static final String URI = "/request/new/statementRequest";

    private ServiceController service;
    private RequestChainPool chainPool;
    private SoapMessageList messageList;

    /*
        Constructor for tests
    */
    public StatementRequestController(Logger logger, RequestChainPool chainPool, ServiceController serviceController,
                                      SoapMessageList messageList) {
        this.log = logger;
        this.chainPool = chainPool;
        this.service = serviceController;
        this.messageList = messageList;
    }

    @Autowired
    public StatementRequestController(RequestChainPool chainPool, ServiceController serviceController,
                                      SoapMessageList messageList) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.chainPool = chainPool;
        this.service = serviceController;
        this.messageList = messageList;
    }

    @PostMapping(URI)
    @ResponseBody
    public ResponseBodyData runStatementRequest(HttpSession httpSession, @RequestBody StatementRequestData data) {
        log.info("Request uri='" + URI + "' data='" + data.toString() + "'");
        StatementRequestChain chain = null;
        try {
            AppUser user = service.getUser(httpSession);
            data.check();

            chain = chainPool.createStatementRequestChain(user);
            chain.nextStep(data);
            chainPool.addToPool(chain);

            return getSoapRequestSuccessResponse(chain);
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (SoapServerBadResponseException e) {
            return getSoapRequestFailResponse(e, chain);
        } catch (RequestParameterLengthException e) {
            return service.getParameterLengthErrorResponse(e);
        } catch (Exception e) {
            return service.getServerFailResponse(e, chain);
        }
    }

    private ResponseBodyData getSoapRequestSuccessResponse(RequestChain chain) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("StatementRequest to Soap server succeed. Request id=" + chain.getResponseId());
        result.setSoapMessageList(messageList.getLastRequestMessageList());

        log.info("Success request." + result.getLogInfo());
        return result;
    }

    private ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception, RequestChain chain) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("StatementRequest to Soap server failed. Message: " + exception.getSoapResponse());
        result.setSoapMessageList(messageList.getLastRequestMessageList());
        result.setRequestChain(chain);

        log.info("Failed request." + result.getLogInfo());
        return result;
    }

}
