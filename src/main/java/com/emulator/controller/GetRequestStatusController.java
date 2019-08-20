package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.requests.getrequeststatus.GetRequestStatusResult;
import com.emulator.domain.soap.requestchain.RequestChain;
import com.emulator.exception.BadRequestParameterException;
import com.emulator.exception.RequestChainIsNotExistException;
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
                                                @RequestParam(name = "responseId") String responseId) {
        try {
            AppUser user = (AppUser) httpSession.getAttribute("user");
            if (user == null) {
                return getUserIsNotAuthorizedResponse();
            }

            checkResponseId(httpSession, responseId);

            GetRequestStatusResult result = soapClient.sendGetRequestStatus(user, responseId);
            GetRequestStatusDto dto = getDto(result);
            RequestChain chain = getRequestChain(httpSession, responseId);
            chain.setGetRequestStatus(dto);

            return getSoapRequestSuccessResponse(dto);
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

    protected ResponseBodyData getSoapRequestSuccessResponse(GetRequestStatusDto requestResult) {
        ResponseBodyData result = getSoapRequestSuccessResponse("");
        result.setObject(requestResult);
        return result;
    }

    @Autowired
    SoapMessageList soapMessageList;

    @Override
    protected ResponseBodyData getSoapRequestSuccessResponse(String message) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("GetRequestStatus to Soap server is success." + message);
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        soapMessageList.clearLastRequestMessageList();
        return result;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("GetRequestStatus to Soap server is fail. message=" + exception.getSoapResponse());
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        soapMessageList.clearLastRequestMessageList();
        return result;
    }

    private ResponseBodyData getBadRequestParameterResponse(BadRequestParameterException e) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("Parameter " + e.getParameterName() + " not found on server");
        return result;
    }



    private RequestChain getRequestChain(HttpSession httpSession, String responseId) {
        List<RequestChain> requestList = (List<RequestChain>) httpSession.getAttribute("requestList");
        for (RequestChain requestChain: requestList) {
            if (requestChain.getResponseId().equals(responseId)){
                return requestChain;
            }
        }

        throw new RequestChainIsNotExistException("GetRequestStatus RequestChainIsNotExistException");
    }

    private void checkResponseId(HttpSession httpSession, String responseId) {
        boolean requestFound = false;

        List<RequestChain> requestList = (List<RequestChain>) httpSession.getAttribute("requestList");
        for (RequestChain requestChain: requestList) {
            if (requestChain.getResponseId().equals(responseId)){
                requestFound = true;
            }
        }

        if (!requestFound) {
            String message = "Parameter not found on server";
            BadRequestParameterException exception = new BadRequestParameterException(message);
            exception.setParameterName("responseId");
            throw exception;
        }
    }

}
