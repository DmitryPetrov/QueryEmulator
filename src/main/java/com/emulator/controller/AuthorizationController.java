package com.emulator.controller;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.frontend.request.RequestBodyAppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AuthorizationController extends AbstractController{

    private static Logger log = LoggerFactory.getLogger(AuthorizationController.class);

    private static final String URI = "/login";

    @Autowired
    private SoapClient soapClient;

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
            return getParameterLengthErrorResponse(e);
        } catch (Exception e) {
            return getServerFailResponse(e);
        }
    }

    private ResponseBodyData getSoapRequestSuccessResponse(String sessionId) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Authorization succeed. Session id=" + sessionId);
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());

        log.info("Success request." + result.getLogInfo());
        return result;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception, RequestChain
            chain) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("LogIn to Soap server failed. Message: " + exception.getSoapResponse());
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());

        log.info("Failed request." + result.getLogInfo());
        return result;
    }

    @Override
    protected ResponseBodyData getSoapRequestSuccessResponse(RequestChain chain) {
        return null;
    }

}
