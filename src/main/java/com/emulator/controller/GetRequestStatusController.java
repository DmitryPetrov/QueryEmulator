package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.frontend.SOAPConnectionStatus;
import com.emulator.domain.soap.SOAPClient;
import com.emulator.domain.soap.exception.SOAPServerBadResponseException;
import com.emulator.domain.soap.exception.SOAPServerGetRequestStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

public class GetRequestStatusController extends AbstractController {

    @Autowired
    private SOAPClient soapClient;

    @RequestMapping(value = "/sendRequests/getRequestStatus", method = RequestMethod.POST)
    @ResponseBody
    public SOAPConnectionStatus runGetRequestStatus(HttpSession httpSession) {
        AppUser user = (AppUser) httpSession.getAttribute("user");
        if ((user == null) || user.getSessionId().equals("")) {
            return getUserIsNotAuthorizedResponse();
        }

        String requestId = (String) httpSession.getAttribute("requestId");
        if((requestId == null) || (requestId.equals(""))) {
            return getNotFoundRequestIdResponse();
        }

        try {
            soapClient.sendGetRequestStatus(user, requestId);
            return getRequestSuccessResponse("requestId");
        } catch (SOAPServerGetRequestStatusException e) {
            e.printStackTrace();
            return getRequestFailResponse(e);
        }
    }

    @Override
    protected SOAPConnectionStatus getRequestSuccessResponse(String message) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("OK");
        result.setMessage("GetRequestStatus to SOAP server is success. requestID=" + message);
        return result;
    }

    @Override
    protected SOAPConnectionStatus getRequestFailResponse(SOAPServerBadResponseException exception) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("ERROR");
        result.setMessage("GetRequestStatus to SOAP server is fail. message=" + exception.getSoapResponse());
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }

    private SOAPConnectionStatus getNotFoundRequestIdResponse() {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("ERROR");
        result.setMessage("Not found request id.");
        return result;
    }

}
