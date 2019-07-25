package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.frontend.SOAPConnectionStatus;
import com.emulator.domain.soap.SOAPClient;
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
    public SOAPConnectionStatus login(HttpSession httpSession,
                                      @RequestParam(value = "userName", required = false) String userName,
                                      @RequestParam(value = "password", required = false) String password) {
        try {
            AppUser user = soapClient.authorization(userName, password);
            httpSession.setAttribute("user", user);
            return authorizationSuccessed(user.getSessionId());
        } catch (SOAPServerLoginException e) {
            e.printStackTrace();
            return authorizationFailed(e);
        }
    }

    private SOAPConnectionStatus authorizationSuccessed(String sessionId) {
        SOAPConnectionStatus result =  new SOAPConnectionStatus();
        result.setStatus("OK");
        result.setMessage("LogIn to SOAP server is success. sessionID=" + sessionId);
        return result;
    }

    private SOAPConnectionStatus authorizationFailed(SOAPServerLoginException exception) {
        SOAPConnectionStatus result =  new SOAPConnectionStatus();
        result.setStatus("LogIn ERROR");
        result.setMessage("LogIn to SOAP server is fail.");
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }
}
