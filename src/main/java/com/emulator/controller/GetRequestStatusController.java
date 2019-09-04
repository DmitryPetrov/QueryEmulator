package com.emulator.controller;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.exception.BadRequestParameterException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class GetRequestStatusController extends AbstractController {

    @Autowired
    private RequestChainPool chainPool;

    @GetMapping("/request/nextStep")
    @ResponseBody
    public ResponseBodyData runGetRequestStatus(HttpSession httpSession,
                                                @RequestParam(name = "responseId") String responseId) {
        RequestChain chain = null;
        try {
            AppUser user = getUser(httpSession);

            chain = chainPool.getRequestChain(user, responseId);
            chain.nextStep();

            return getSoapRequestSuccessResponse(chain);
        } catch (UserIsNotAuthorizedException e) {
            e.printStackTrace();
            return getUserIsNotAuthorizedResponse();
        } catch (SoapServerBadResponseException e) {
            e.printStackTrace();
            return getSoapRequestFailResponse(e);
        } catch (BadRequestParameterException e) {
            e.printStackTrace();
            return getBadRequestParameterResponse(e);
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e, chain);
        }
    }

    @Override
    protected ResponseBodyData getSoapRequestSuccessResponse(RequestChain chain) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("GetRequestStatus to Soap server is success.");
        result.setRequestChain(chain);
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        return result;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("GetRequestStatus to Soap server is fail. message=" + exception.getSoapResponse());
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        return result;
    }

    private ResponseBodyData getBadRequestParameterResponse(BadRequestParameterException e) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("Parameter " + e.getParameterName() + " not found on server");
        return result;
    }

}
