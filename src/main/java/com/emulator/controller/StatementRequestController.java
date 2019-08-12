package com.emulator.controller;


import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.RequestParameters;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.exception.SOAPServerBadResponseException;
import com.emulator.domain.soap.statementRequest.StatementRequestData;
import com.emulator.domain.frontend.response.ResponseBodySOAPRequestStatus;
import com.emulator.domain.soap.SOAPClient;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SOAPServerStatementRequestException;
import com.emulator.domain.soap.statementRequest.StatementRequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class StatementRequestController extends AbstractController {

    @Autowired
    private SOAPClient soapClient;

    @RequestMapping(value = "/sendRequests/statementRequest", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBodyData runStatementRequest(HttpSession httpSession, @RequestBody StatementRequestData data) {
        try {
            AppUser user = (AppUser) httpSession.getAttribute("user");
            if (user == null) {
                return getUserIsNotAuthorizedResponse();
            }

            data.check();
            StatementRequestResult result = soapClient.sendStatementRequest(user, data);

            data.setRequestId(result.getRequestId());
            List<RequestParameters> requestList = (List<RequestParameters>) httpSession.getAttribute("requestList");
            requestList.add(data);

            return getSOAPRequestSuccessResponse(result.getRequestId());
        } catch (SOAPServerStatementRequestException e) {
            e.printStackTrace();
            return getSOAPRequestFailResponse(e);
        } catch (RequestParameterLengthException e) {
            e.printStackTrace();
            return getParameterLengthErrorResponse(e);
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e);
        }
    }

    @Override
    protected ResponseBodySOAPRequestStatus getSOAPRequestSuccessResponse(String message) {
        ResponseBodySOAPRequestStatus result = new ResponseBodySOAPRequestStatus();
        result.setStatus("OK");
        result.setMessage("StatementRequest to SOAP server is success. requestID=" + message);
        return result;
    }

    @Override
    protected ResponseBodySOAPRequestStatus getSOAPRequestFailResponse(SOAPServerBadResponseException exception) {
        ResponseBodySOAPRequestStatus result = new ResponseBodySOAPRequestStatus();
        result.setStatus("ERROR");
        result.setMessage("StatementRequest to SOAP server is fail. message=" + exception.getSoapResponse());
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }

}
