package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.frontend.SOAPConnectionStatus;
import com.emulator.domain.frontend.requestBody.RequestBodyAppUser;
import com.emulator.domain.soap.SOAPClient;
import com.emulator.domain.soap.exception.RequestParameterLengthException;
import com.emulator.domain.soap.exception.SOAPServerBadResponseException;
import com.emulator.domain.soap.exception.SOAPServerLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
public class AuthorizationController extends AbstractController{

    @Autowired
    private SOAPClient soapClient;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public SOAPConnectionStatus login(HttpSession httpSession, @RequestBody RequestBodyAppUser requestData) {
        try {
            requestData.check();
            AppUser user = requestData.getAppUser();

            AppUser authorizedUser = soapClient.authorization(user);

            httpSession.setAttribute("user", authorizedUser);
            return getRequestSuccessResponse(authorizedUser.getSessionId());
        } catch (SOAPServerLoginException e) {
            e.printStackTrace();
            return getRequestFailResponse(e);
        } catch (RequestParameterLengthException e) {
            e.printStackTrace();
            return getParameterLengthErrorResponse(e);
        }
    }

    @Override
    protected SOAPConnectionStatus getRequestSuccessResponse(String sessionId) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("OK");
        result.setMessage("LogIn to SOAP server is success. sessionID=" + sessionId);
        return result;
    }

    @Override
    protected SOAPConnectionStatus getRequestFailResponse(SOAPServerBadResponseException exception) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("ERROR");
        result.setMessage("LogIn to SOAP server is fail. Message=" + exception.getSoapResponse());
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }
}
