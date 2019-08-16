package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.RequestParameters;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.incoming.IncomingData;
import com.emulator.domain.soap.incoming.IncomingResult;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.SoapServerIncomingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IncomingController extends AbstractController {

    private static final String REQUEST_NAME = "Incoming";


    @Autowired
    private SoapClient soapClient;

    @RequestMapping(value = "/sendRequests/incoming", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBodyData runIncoming(HttpSession httpSession, @RequestBody IncomingData data) {
        try {
            AppUser user = (AppUser) httpSession.getAttribute("user");
            if (user == null) {
                return getUserIsNotAuthorizedResponse();
            }

            data.check();
            IncomingResult result = soapClient.sendIncoming(user, data);

            data.setRequestId(result.getRequestId());
            data.setRequestName(REQUEST_NAME);
            List<RequestParameters> requestList = (List<RequestParameters>) httpSession.getAttribute("requestList");
            requestList.add(data);

            return getSoapRequestSuccessResponse(result.getRequestId());
        } catch (SoapServerIncomingException e) {
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

    @Autowired
    SoapMessageList soapMessageList;

    @Override
    protected ResponseBodyData getSoapRequestSuccessResponse(String message) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Incoming request to Soap server is success. requestID=" + message);
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        soapMessageList.clearLastRequestMessageList();
        return result;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("Incoming request to Soap server is fail. message=" + exception.getSoapResponse());
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        soapMessageList.clearLastRequestMessageList();
        return result;
    }
}
