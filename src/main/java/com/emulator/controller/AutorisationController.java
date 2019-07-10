package com.emulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emulator.domain.SOAPClient;
import com.emulator.domain.entity.AppUser;
import com.emulator.domain.prelogin.PreLoginResult;

@Controller
public class AutorisationController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String index() {

        return "hello";
    }

    @Autowired
    private SOAPClient client;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String logIn(
            // @RequestParam(value = "userName", required = true) String userName,
            // @RequestParam(value = "password", required = true) String password
    ) {
        AppUser user = new AppUser("testui", "L8UWRF");

        PreLoginResult result = client.callPreLogin(user);

        return result.toString();
    }
}
