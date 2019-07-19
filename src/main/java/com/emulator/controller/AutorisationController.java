package com.emulator.controller;

import com.emulator.domain.soap.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AutorisationController {

    @Autowired
    private Client client;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam(value = "userName", required = false) String userName,
                        @RequestParam(value = "password", required = false) String password) {
        return client.authorization(userName, password);
    }
}
