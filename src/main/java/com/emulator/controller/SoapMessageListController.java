package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.requestchain.RequestChain;
import com.emulator.exception.SoapServerBadResponseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SoapMessageListController  extends AbstractController{

    @GetMapping("/soapMessage/list")
    @ResponseBody
    public ResponseBodyData runGetRequestStatus(HttpSession httpSession,
                                                @RequestParam(name = "clear", required = false) String clear) {
        try {
            AppUser user = (AppUser) httpSession.getAttribute("user");
            if (user == null) {
                return getUserIsNotAuthorizedResponse();
            }
            if ((clear != null) && (clear.equals("true"))) {
                clearSoapMessageList();
            }
            return getSuccessResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e);
        }
    }

    private void clearSoapMessageList() {
        soapMessageList.clear();
    }

    private ResponseBodyData getSuccessResponse() {
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
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        return null;
    }

}
