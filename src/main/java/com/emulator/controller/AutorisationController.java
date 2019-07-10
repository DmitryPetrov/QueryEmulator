package com.emulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emulator.domain.SOAPClient;
import com.emulator.domain.entity.AppUser;
import com.emulator.domain.prelogin.PreLoginResult;

import java.io.IOException;

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
    public String login(
            // @RequestParam(value = "userName", required = true) String userName,
            // @RequestParam(value = "password", required = true) String password
    ) {
        AppUser user = new AppUser("testui", "L8UWRF");

        String result = null;
        try {
            result = client.authorization("testui", "L8UWRF");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
