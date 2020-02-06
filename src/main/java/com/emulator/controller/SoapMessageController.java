package com.emulator.controller;

import com.emulator.domain.frontend.response.Response;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.exception.UserIsNotAuthorizedException;
import com.emulator.service.SoapMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/soapMessages")
public class SoapMessageController {

    private static final String URI_LAST_REQUEST = "lastRequest";

    private ServiceController service;
    private SoapMessageService messageService;

    @Autowired
    public SoapMessageController(ServiceController serviceController, SoapMessageService messageService) {
        this.service = serviceController;
        this.messageService = messageService;
    }

    @GetMapping
    @ResponseBody
    public Response getAllMessages(HttpSession session) {
        try {
            return messageService.getAll(session);
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }

    @GetMapping(URI_LAST_REQUEST)
    @ResponseBody
    public Response getLastRequestMessages(HttpSession session) {
        try {
            return messageService.getLastRequest(session);
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }

    @DeleteMapping
    @ResponseBody
    public Response removeAllMessages(HttpSession session) {
        try {
            return messageService.removeAll(session);
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }
}
