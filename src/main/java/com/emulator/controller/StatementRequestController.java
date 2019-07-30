package com.emulator.controller;


import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.statementRequest.StatementRequestData;
import com.emulator.domain.frontend.SOAPConnectionStatus;
import com.emulator.domain.soap.SOAPClient;
import com.emulator.domain.soap.exception.RequestParameterLengthException;
import com.emulator.domain.soap.exception.SOAPServerStatementRequestException;
import com.emulator.domain.soap.statementRequest.StatementRequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class StatementRequestController {

    @Autowired
    private SOAPClient soapClient;

    @RequestMapping(value = "/sendRequests/statementRequest", method = RequestMethod.POST)
    @ResponseBody
    public SOAPConnectionStatus runStatementRequest(HttpSession httpSession, @RequestBody StatementRequestData data) {
        AppUser user = (AppUser) httpSession.getAttribute("user");
        if ((user == null) || user.getSessionId().equals("")) {
            return userIsNotAuthorized();
        }

        try {
            data.check();
            StatementRequestResult result = soapClient.sendStatementRequest(user, data);
            httpSession.setAttribute("StatementRequestResult", result);
            return statementRequestSucceeded(result.getRequestId());
        } catch (SOAPServerStatementRequestException e) {
            e.printStackTrace();
            return statementRequestFailed(e);
        } catch (RequestParameterLengthException e) {
            e.printStackTrace();
            return requestParametersIsInvalid(e);
        }
    }

    private SOAPConnectionStatus statementRequestSucceeded(String message) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("OK");
        result.setMessage("StatementRequest to SOAP server is success. requestID=" + message);
        return result;
    }

    private SOAPConnectionStatus statementRequestFailed(SOAPServerStatementRequestException exception) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("ERROR");
        result.setMessage("StatementRequest to SOAP server is fail. message=" + exception.getSoapResponse());
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }

    private SOAPConnectionStatus requestParametersIsInvalid(RequestParameterLengthException exception) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("ERROR");
        result.setMessage("StatementRequest request parameters is invalid. Parameter " + exception.getParameterName()
                + " must be shorter than " + exception.getMaxLength() + " characters!");
        return result;
    }

    private SOAPConnectionStatus userIsNotAuthorized() {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("ERROR");
        result.setMessage("User is not authorized");
        return result;
    }

}
