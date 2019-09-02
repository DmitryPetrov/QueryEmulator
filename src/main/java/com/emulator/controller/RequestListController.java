package com.emulator.controller;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.exception.SoapServerBadResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RequestListController extends AbstractController{

    @Autowired
    private RequestChainPool chainPool;

    @GetMapping("/request/list")
    @ResponseBody
    public ResponseBodyData runGetRequestStatus(HttpSession httpSession) {
        try {
            AppUser user = (AppUser) httpSession.getAttribute("user");
            if (user == null) {
                return getUserIsNotAuthorizedResponse();
            }

            List<RequestChain> chainList = chainPool.getChainList(user);

            return getSuccessResponse(chainList);
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e);
        }
    }

    private ResponseBodyData getSuccessResponse(List<RequestChain> chainList) {
        ResponseBodyData response = new ResponseBodyData();
        response.setStatus("OK");
        response.setMessage("Request list");
        response.setRequestChainList(chainList);
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
