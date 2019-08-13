package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.RequestParameters;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.frontend.response.ResponseBodySOAPRequestStatus;
import com.emulator.domain.soap.SOAPClient;
import com.emulator.domain.soap.getRequestStatus.GetRequestStatusResult;
import com.emulator.exception.BadRequestParameterException;
import com.emulator.exception.SOAPServerBadResponseException;
import com.emulator.exception.SOAPServerGetRequestStatusException;
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
    private SOAPClient soapClient;

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
            return getSOAPRequestSuccessResponse(result);
        } catch (SOAPServerGetRequestStatusException e) {
            e.printStackTrace();
            return getSOAPRequestFailResponse(e);
        } catch (BadRequestParameterException e) {
            e.printStackTrace();
            return getBadRequestParameterResponse(e);
        } catch (Exception e) {
            e.printStackTrace();
            return getServerFailResponse(e);
        }
    }

    protected ResponseBodySOAPRequestStatus getSOAPRequestSuccessResponse(GetRequestStatusResult requestResult) {
        ResponseBodySOAPRequestStatus result = getSOAPRequestSuccessResponse("");
        result.setObject(requestResult);
        return result;
    }

    @Override
    protected ResponseBodySOAPRequestStatus getSOAPRequestSuccessResponse(String message) {
        ResponseBodySOAPRequestStatus result = new ResponseBodySOAPRequestStatus();
        result.setStatus("OK");
        result.setMessage("GetRequestStatus to SOAP server is success." + message);
        return result;
    }

    @Override
    protected ResponseBodySOAPRequestStatus getSOAPRequestFailResponse(SOAPServerBadResponseException exception) {
        ResponseBodySOAPRequestStatus result = new ResponseBodySOAPRequestStatus();
        result.setStatus("ERROR");
        result.setMessage("GetRequestStatus to SOAP server is fail. message=" + exception.getSoapResponse());
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
