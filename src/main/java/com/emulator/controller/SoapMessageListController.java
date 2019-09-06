package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SoapMessageListController  extends AbstractController{

    @GetMapping("/soapMessage/list/all")
    @ResponseBody
    public ResponseBodyData getAllMessage(HttpSession httpSession) {
        try {
            AppUser user = getUser(httpSession);
            return getSuccessResponseList();
        } catch (UserIsNotAuthorizedException e) {
            e.printStackTrace();
            return getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e);
        }
    }

    @GetMapping("/soapMessage/list/lastRequest")
    @ResponseBody
    public ResponseBodyData getLastRequestMessage(HttpSession httpSession) {
        try {
            AppUser user = getUser(httpSession);
            return getSuccessResponseLastRequest();
        } catch (UserIsNotAuthorizedException e) {
            e.printStackTrace();
            return getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e);
        }
    }

    @GetMapping("/soapMessage/remove/all")
    @ResponseBody
    public ResponseBodyData removeAllMessage(HttpSession httpSession) {
        try {
            AppUser user = getUser(httpSession);
            clearSoapMessageList();
            return getSuccessResponseList();
        } catch (UserIsNotAuthorizedException e) {
            e.printStackTrace();
            return getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e);
        }
    }

    private void clearSoapMessageList() {
        soapMessageList.clear();
    }

    private ResponseBodyData getSuccessResponseLastRequest() {
        ResponseBodyData response = new ResponseBodyData();
        response.setStatus("OK");
        response.setMessage("Last Request soap message list");
        response.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        return response;
    }

    private ResponseBodyData getSuccessResponseList() {
        ResponseBodyData response = new ResponseBodyData();
        response.setStatus("OK");
        response.setMessage("Soap message list");
        response.setSoapMessageList(soapMessageList.getMessageList());
        return response;
    }

    @Override
    protected ResponseBodyData getSoapRequestSuccessResponse(RequestChain chain) {
        return null;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception, RequestChain
            chain) {
        return null;
    }

}
