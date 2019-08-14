package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.RequestParameters;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.domain.soap.statementrequest.StatementRequestData;
import com.emulator.domain.frontend.response.ResponseBodySoapRequestStatus;
import com.emulator.domain.soap.SoapClient;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerStatementRequestException;
import com.emulator.domain.soap.statementrequest.StatementRequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class StatementRequestController extends AbstractController {

    @Autowired
    private SoapClient soapClient;

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

            return getSoapRequestSuccessResponse(result.getRequestId());
        } catch (SoapServerStatementRequestException e) {
            e.printStackTrace();
            return getSoapRequestFailResponse(e);
        } catch (RequestParameterLengthException e) {
            e.printStackTrace();
            return getParameterLengthErrorResponse(e);
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e);
        }
    }

    @Override
    protected ResponseBodySoapRequestStatus getSoapRequestSuccessResponse(String message) {
        ResponseBodySoapRequestStatus result = new ResponseBodySoapRequestStatus();
        result.setStatus("OK");
        result.setMessage("StatementRequest to Soap server is success. requestID=" + message);
        return result;
    }

    @Override
    protected ResponseBodySoapRequestStatus getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        ResponseBodySoapRequestStatus result = new ResponseBodySoapRequestStatus();
        result.setStatus("ERROR");
        result.setMessage("StatementRequest to Soap server is fail. message=" + exception.getSoapResponse());
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }

}
