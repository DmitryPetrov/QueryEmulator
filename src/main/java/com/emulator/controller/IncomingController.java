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
public class IncomingController extends AbstractController {

    private static Logger log = LoggerFactory.getLogger(IncomingController.class);

    private static final String URI = "/request/nextStep";

    @Autowired
    private RequestChainPool chainPool;

    @PostMapping("/request/nextStep")
    @ResponseBody
    public ResponseBodyData runIncoming(HttpSession httpSession, @RequestBody IncomingData data) {
        log.info("Request uri='" + URI + "' data='" + data.toString() + "'");
        RequestChain chain = null;
        try {
            AppUser user = getUser(httpSession);
            data.check();

            chain = chainPool.getRequestChain(user, data.getAttrRequestId());
            chain.nextStep(data);

            return getSoapRequestSuccessResponse(chain);
        } catch (UserIsNotAuthorizedException e) {
            return getUserIsNotAuthorizedResponse();
        } catch (SoapServerBadResponseException e) {
            return getSoapRequestFailResponse(e, chain);
        } catch (RequestParameterLengthException e) {
            return getParameterLengthErrorResponse(e);
        } catch (Exception e) {
            return getServerFailResponse(e, chain);
        }
    }

    @Override
    protected ResponseBodyData getSoapRequestSuccessResponse(RequestChain chain) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Incoming request to Soap server is success. requestID=" + chain.getIncomingResponseId());
        result.setRequestChain(chain);
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());

        log.info("Success request." + result.getLogInfo());
        return result;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception, RequestChain
            chain) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("Incoming request to Soap server is fail. message=" + exception.getSoapResponse());
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        result.setRequestChain(chain);

        log.info("Failed request." + result.getLogInfo());
        return result;
    }

}
