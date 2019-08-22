package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.requestchain.RequestChain;
import com.emulator.domain.soap.requestchain.RequestChainPhase;
import com.emulator.domain.soap.requestchain.RequestChainPool;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
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

@Controller
public class StatementDocumentController extends AbstractController {

    private static final RequestChainPhase PHASE = RequestChainPhase.STATEMENT_DOCUMENT;

    @Autowired
    private SoapClient soapClient;

    @Autowired
    private RequestChainPool chainPool;

    @RequestMapping(value = "/sendRequests/getRequestStatus/getStatement", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBodyData runGetRequestStatus(HttpSession httpSession,
                                                @RequestParam(name = "responseId") String responseId) {
        try {
            AppUser user = (AppUser) httpSession.getAttribute("user");
            if (user == null) {
                return getUserIsNotAuthorizedResponse();
            }

            RequestChain chain = chainPool.getRequestChain(user, responseId);
            chain.checkPhase(PHASE);
            GetRequestStatusDto dto = soapClient.sendGetRequestStatus(user, chain.getIncomingResponseId());
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
        result.setMessage("StatementDocument to Soap server is success." + message);
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        soapMessageList.clearLastRequestMessageList();
        return result;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("StatementDocument to Soap server is fail. message=" + exception.getSoapResponse());
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
}
