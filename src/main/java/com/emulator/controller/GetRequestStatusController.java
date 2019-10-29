package com.emulator.controller;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.exception.BadRequestParameterException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class GetRequestStatusController extends AbstractController {

    private static Logger log = LoggerFactory.getLogger(GetRequestStatusController.class);

    private static final String URI = "/request/nextStep";

    @Autowired
    private RequestChainPool chainPool;

    @GetMapping(URI)
    @ResponseBody
    public ResponseBodyData runGetRequestStatus(HttpSession httpSession,
                                                @RequestParam(name = "responseId") String responseId) {
        log.info("Request uri='" + URI + "' RequestParam: responseId='" + responseId + "'");
        RequestChain chain = null;
        try {
            AppUser user = getUser(httpSession);

            chain = chainPool.getRequestChain(user, responseId);
            chain.nextStep();

            return getSoapRequestSuccessResponse(chain);
        } catch (UserIsNotAuthorizedException e) {
            return getUserIsNotAuthorizedResponse();
        } catch (SoapServerBadResponseException e) {
            return getSoapRequestFailResponse(e, chain);
        } catch (BadRequestParameterException e) {
            return getBadRequestParameterResponse(e);
        } catch (Exception e) {
            return getServerFailResponse(e, chain);
        }
    }

    @Override
    protected ResponseBodyData getSoapRequestSuccessResponse(RequestChain chain) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("GetRequestStatus to Soap server succeed.");
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
        result.setMessage("GetRequestStatus to Soap server failed. Message: " + exception.getSoapResponse());
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        result.setRequestChain(chain);

        log.info("Failed request." + result.getLogInfo());
        return result;
    }

    private ResponseBodyData getBadRequestParameterResponse(BadRequestParameterException e) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("Parameter " + e.getParameterName() + " not found on server");

        log.info("Failed request." + result.getLogInfo());
        return result;
    }

}
