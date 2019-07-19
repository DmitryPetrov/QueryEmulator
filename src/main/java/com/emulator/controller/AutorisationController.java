package com.emulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emulator.domain.SOAPClient;
import com.emulator.domain.entity.AppUser;
import com.emulator.domain.prelogin.PreLoginResult;

import java.io.IOException;

@Controller
public class AutorisationController {

    @Autowired
    private SOAPClient client;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam(value = "userName", required = false) String userName,
                        @RequestParam(value = "password", required = false) String password) {
        String result = null;
        try {
            result = client.authorization(userName, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
