package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.RequestParameters;
import com.emulator.domain.frontend.request.RequestBodyAppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.frontend.response.ResponseBodySOAPRequestStatus;
import com.emulator.domain.soap.SOAPClient;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SOAPServerBadResponseException;
import com.emulator.exception.SOAPServerLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class AuthorizationController extends AbstractController{

    @Autowired
    private SOAPClient soapClient;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBodyData login(HttpSession httpSession, @RequestBody RequestBodyAppUser requestData) {
        try {
            requestData.check();

            AppUser user = requestData.getAppUser();
            AppUser authorizedUser = soapClient.authorization(user);

            httpSession.setAttribute("user", authorizedUser);
            httpSession.setAttribute("requestList", new ArrayList<RequestParameters>());

            return getSOAPRequestSuccessResponse(authorizedUser.getSessionId());
        } catch (SOAPServerLoginException e) {
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
    protected ResponseBodySOAPRequestStatus getSOAPRequestSuccessResponse(String sessionId) {
        ResponseBodySOAPRequestStatus result = new ResponseBodySOAPRequestStatus();
        result.setStatus("OK");
        result.setMessage("LogIn to SOAP server is success. sessionID=" + sessionId);
        return result;
    }

    @Override
    protected ResponseBodySOAPRequestStatus getSOAPRequestFailResponse(SOAPServerBadResponseException exception) {
        ResponseBodySOAPRequestStatus result = new ResponseBodySOAPRequestStatus();
        result.setStatus("ERROR");
        result.setMessage("LogIn to SOAP server is fail. Message=" + exception.getSoapResponse());
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }

}
