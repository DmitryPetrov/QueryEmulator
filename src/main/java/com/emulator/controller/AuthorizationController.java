package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.frontend.SOAPConnectionStatus;
import com.emulator.domain.soap.SOAPClient;
import com.emulator.domain.soap.exception.RequestParameterLengthException;
import com.emulator.domain.soap.exception.SOAPServerLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@SessionAttributes("user")
public class AuthorizationController {

    @Autowired
    private SOAPClient soapClient;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public SOAPConnectionStatus login(HttpSession httpSession, @RequestBody AppUser user) {
        try {
            user.check();
            soapClient.authorization(user);
            httpSession.setAttribute("user", user);
            return authorizationSucceeded(user.getSessionId());
        } catch (SOAPServerLoginException e) {
            e.printStackTrace();
            return authorizationFailed(e);
        } catch (RequestParameterLengthException e) {
            e.printStackTrace();
            return requestParametersIsInvalid(e);
        }
    }

    private SOAPConnectionStatus authorizationSucceeded(String sessionId) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("OK");
        result.setMessage("LogIn to SOAP server is success. sessionID=" + sessionId);
        return result;
    }

    private SOAPConnectionStatus authorizationFailed(SOAPServerLoginException exception) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("ERROR");
        result.setMessage("LogIn to SOAP server is fail. Message=" + exception.getSoapResponse());
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }

    private SOAPConnectionStatus requestParametersIsInvalid(RequestParameterLengthException exception) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("ERROR");
        result.setMessage("LogIn parameters is invalid. Parameter " + exception.getParameterName() + " must be " +
                "shorter than " + exception.getMaxLength() + " characters!");
        return result;
    }
}
