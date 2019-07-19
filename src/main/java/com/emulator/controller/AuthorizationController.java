package com.emulator.controller;

import com.emulator.domain.soap.SOAPClient;
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
    public String login(HttpSession httpSession,
                        @RequestParam(value = "userName", required = false) String userName,
                        @RequestParam(value = "password", required = false) String password) {
        httpSession.setAttribute("user", soapClient.authorization(userName, password));

        return "redirect:/home";
    }
}
