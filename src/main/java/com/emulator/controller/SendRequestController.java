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
public class SendRequestController {

    @Autowired
    private SOAPClient soapClient;

    @RequestMapping(value = "/sendRequests/statementRequest", method = RequestMethod.POST)
    @ResponseBody
    public SOAPConnectionStatus runStatementRequest(HttpSession httpSession, @RequestBody StatementRequestData data) {
        AppUser user = (AppUser) httpSession.getAttribute("user");
        try {
            data.check();
            StatementRequestResult result = soapClient.sendStatementRequest(user, data);
            return statementRequestSucceeded(user.getSessionId());
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
        result.setMessage("StatementRequest to SOAP server is success. message=" + message);
        return result;
    }

    private SOAPConnectionStatus statementRequestFailed(SOAPServerStatementRequestException exception) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("StatementRequest ERROR");
        result.setMessage("StatementRequest to SOAP server is fail.");
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }

    private SOAPConnectionStatus requestParametersIsInvalid(RequestParameterLengthException exception) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("ERROR: StatementRequest request parameters is invalid");
        result.setMessage("Parameter " + exception.getParameterName() + " must be shorter than " + exception
                .getMaxLength() + " characters!");
        return result;
    }

}
