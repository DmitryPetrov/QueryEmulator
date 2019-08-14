package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.RequestParameters;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.frontend.response.ResponseBodySoapRequestStatus;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.getrequeststatus.GetRequestStatusResult;
import com.emulator.exception.BadRequestParameterException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.SoapServerGetRequestStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GetRequestStatusController extends AbstractController {

    @Autowired
    private SoapClient soapClient;

    @RequestMapping(value = "/sendRequests/getRequestStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBodyData runGetRequestStatus(HttpSession httpSession,
                                                @RequestParam(name = "requestId") String requestId) {
        try {
            AppUser user = (AppUser) httpSession.getAttribute("user");
            if (user == null) {
                return getUserIsNotAuthorizedResponse();
            }

            checkRequestId(httpSession, requestId);

            GetRequestStatusResult result = soapClient.sendGetRequestStatus(user, requestId);
            return getSoapRequestSuccessResponse(result);
        } catch (SoapServerGetRequestStatusException e) {
            e.printStackTrace();
            return getSoapRequestFailResponse(e);
        } catch (BadRequestParameterException e) {
            e.printStackTrace();
            return getBadRequestParameterResponse(e);
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e);
        }
    }

    protected ResponseBodySoapRequestStatus getSoapRequestSuccessResponse(GetRequestStatusResult requestResult) {
        ResponseBodySoapRequestStatus result = getSoapRequestSuccessResponse("");
        result.setObject(requestResult);
        return result;
    }

    @Override
    protected ResponseBodySoapRequestStatus getSoapRequestSuccessResponse(String message) {
        ResponseBodySoapRequestStatus result = new ResponseBodySoapRequestStatus();
        result.setStatus("OK");
        result.setMessage("GetRequestStatus to Soap server is success." + message);
        return result;
    }

    @Override
    protected ResponseBodySoapRequestStatus getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        ResponseBodySoapRequestStatus result = new ResponseBodySoapRequestStatus();
        result.setStatus("ERROR");
        result.setMessage("GetRequestStatus to Soap server is fail. message=" + exception.getSoapResponse());
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }

    private ResponseBodyData getBadRequestParameterResponse(BadRequestParameterException e) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("Parameter " + e.getParameterName() + " not found on server");
        return result;
    }

    private void checkRequestId(HttpSession httpSession, String requestId) {
        boolean requestFound = false;

        List<RequestParameters> requestList = (List<RequestParameters>) httpSession.getAttribute("requestList");
        for (RequestParameters request: requestList) {
            if (request.getRequestId().equals(requestId)){
                requestFound = true;
            }
        }

        if (!requestFound) {
            String message = "Parameter not found on server";
            BadRequestParameterException exception = new BadRequestParameterException(message);
            exception.setParameterName("requestId");
            throw exception;
        }
    }

}
