package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.requests.authorization.AppUserData;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/authorization")
public class AuthorizationController {

    private static final String REQUEST_NAME= "Authorization";

    private ServiceController service;
    private AuthorizationService authService;

    @Autowired
    public AuthorizationController(AuthorizationService authService, ServiceController serviceController) {
        this.authService = authService;
        this.service = serviceController;
    }

    @PostMapping
    @ResponseBody
    public ResponseBodyData login(HttpSession session, @RequestBody AppUserData data) {
        try {
            return authService.authorization(session, data);
        } catch (SoapServerBadResponseException e) {
            return service.getSoapRequestFailResponse(e, REQUEST_NAME);
        } catch (RequestParameterLengthException e) {
            return service.getParameterLengthErrorResponse(e);
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }
}
