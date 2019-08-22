package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.requestchain.RequestChain;
import com.emulator.domain.soap.requestchain.RequestChainPool;
import com.emulator.exception.SoapServerBadResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RequestListController extends AbstractController{

    @Autowired
    private RequestChainPool chainPool;

    @RequestMapping(value = "/requestList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBodyData runGetRequestStatus(HttpSession httpSession) {
        try {
            AppUser user = (AppUser) httpSession.getAttribute("user");
            if (user == null) {
                return getUserIsNotAuthorizedResponse();
            }

            List<RequestChain> requestList = chainPool.getChainList(user);

            return getSuccessResponse(requestList);
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e);
        }
    }

    private ResponseBodyData getSuccessResponse(List<RequestChain> requestList) {
        ResponseBodyData response = new ResponseBodyData();
        response.setStatus("OK");
        response.setMessage("Request list");
        response.setRequestList(requestList);
        return response;
    }

    @Override
    protected ResponseBodyData getSoapRequestSuccessResponse(String sessionId) {
        return null;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        return null;
    }

}
