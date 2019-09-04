package com.emulator.controller;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.DOMException;

import javax.servlet.http.HttpSession;

@Controller
public class IncomingController extends AbstractController {

    @Autowired
    private RequestChainPool chainPool;

    @PostMapping("/request/nextStep")
    @ResponseBody
    public ResponseBodyData runIncoming(HttpSession httpSession, @RequestBody IncomingData data) {
        RequestChain chain = null;
        try {
            AppUser user = getUser(httpSession);
            data.check();

            chain = chainPool.getRequestChain(user, data.getAttrRequestId());
            chain.nextStep(data);

            return getSoapRequestSuccessResponse(chain);
        } catch (UserIsNotAuthorizedException e) {
            e.printStackTrace();
            return getUserIsNotAuthorizedResponse();
        } catch (SoapServerBadResponseException e) {
            e.printStackTrace();
            return getSoapRequestFailResponse(e);
        } catch (RequestParameterLengthException e) {
            e.printStackTrace();
            return getParameterLengthErrorResponse(e);
        } catch (Exception e) {
            e.printStackTrace();
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
        return result;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("Incoming request to Soap server is fail. message=" + exception.getSoapResponse());
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        return result;
    }

}
