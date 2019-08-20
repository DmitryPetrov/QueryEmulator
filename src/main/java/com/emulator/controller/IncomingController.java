package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.domain.soap.requests.incoming.IncomingResult;
import com.emulator.domain.soap.requestchain.RequestChain;
import com.emulator.exception.RequestChainIsNotExistException;
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

            IncomingDto dto = getDto(data, result);

            RequestChain chain = getRequestChain(httpSession, dto.getRequestId());
            chain.setIncoming(dto);

            return getSoapRequestSuccessResponse(result.getResponseId());
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

    private RequestChain getRequestChain(HttpSession httpSession, String responseId) {
        List<RequestChain> requestList = (List<RequestChain>) httpSession.getAttribute("requestList");
        for (RequestChain requestChain: requestList) {
            if (requestChain.getResponseId().equals(responseId)){
                return requestChain;
            }
        }

        throw new RequestChainIsNotExistException("Incoming request RequestChainIsNotExistException");
    }

}
