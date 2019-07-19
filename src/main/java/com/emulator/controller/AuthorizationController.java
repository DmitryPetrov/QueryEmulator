package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.frontend.response.SOAPServerConnectionResponse;
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public SOAPServerConnectionResponse login(HttpSession httpSession,
                        @RequestParam(value = "userName", required = false) String userName,
                        @RequestParam(value = "password", required = false) String password) {
        SOAPServerConnectionResponse resp =  new SOAPServerConnectionResponse();

        try {
            AppUser user = soapClient.authorization(userName, password);
            httpSession.setAttribute("user", user);
            resp.setStatus("OK");
            resp.setMessage("LogIn to SOAP server is success. sessionID=" + user.getSessionId());
        } catch (SOAPServerLoginException e) {
            resp.setStatus("ERROR");
            resp.setMessage("LogIn to SOAP server is fail. Error message =" + e.getMessage());
            e.printStackTrace();
        }

        return resp;
    }
}
