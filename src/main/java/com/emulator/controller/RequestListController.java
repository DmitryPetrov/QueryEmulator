package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.RequestParameters;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.frontend.response.ResponseBodyRequestList;
import com.emulator.domain.frontend.response.ResponseBodySoapRequestStatus;
import com.emulator.exception.SoapServerBadResponseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RequestListController extends AbstractController{

    @RequestMapping(value = "/requestList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBodyData runGetRequestStatus(HttpSession httpSession) {
        try {
            AppUser user = (AppUser) httpSession.getAttribute("user");
            if ((user == null)) {
                return getUserIsNotAuthorizedResponse();
            }

            List<RequestParameters> requestList = (List<RequestParameters>) httpSession.getAttribute("requestList");

            return getSuccessResponse(requestList);
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e);
        }
    }

    private ResponseBodyRequestList getSuccessResponse(List<RequestParameters> requestList) {
        ResponseBodyRequestList response = new ResponseBodyRequestList();
        response.setStatus("OK");
        response.setMessage("Request list");
        response.setRequestList(requestList);
        return response;
    }

    @Override
    protected ResponseBodySoapRequestStatus getSoapRequestSuccessResponse(String sessionId) {
        return null;
    }

    @Override
    protected ResponseBodySoapRequestStatus getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        return null;
    }

}
