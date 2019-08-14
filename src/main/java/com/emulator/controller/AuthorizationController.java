package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.RequestParameters;
import com.emulator.domain.frontend.request.RequestBodyAppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.frontend.response.ResponseBodySoapRequestStatus;
import com.emulator.domain.soap.SoapClient;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.SoapServerLoginException;
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
    private SoapClient soapClient;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBodyData login(HttpSession httpSession, @RequestBody RequestBodyAppUser requestData) {
        try {
            requestData.check();

            AppUser user = requestData.getAppUser();
            AppUser authorizedUser = soapClient.authorization(user);

            httpSession.setAttribute("user", authorizedUser);
            httpSession.setAttribute("requestList", new ArrayList<RequestParameters>());

            return getSoapRequestSuccessResponse(authorizedUser.getSessionId());
        } catch (SoapServerLoginException e) {
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
    protected ResponseBodySoapRequestStatus getSoapRequestSuccessResponse(String sessionId) {
        ResponseBodySoapRequestStatus result = new ResponseBodySoapRequestStatus();
        result.setStatus("OK");
        result.setMessage("LogIn to Soap server is success. sessionID=" + sessionId);
        return result;
    }

    @Override
    protected ResponseBodySoapRequestStatus getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        ResponseBodySoapRequestStatus result = new ResponseBodySoapRequestStatus();
        result.setStatus("ERROR");
        result.setMessage("LogIn to Soap server is fail. Message=" + exception.getSoapResponse());
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }

}
