package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.frontend.response.DataTransferObject;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.frontend.response.statementrequest.StatementRequestDataDto;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.statementrequest.StatementRequestData;
import com.emulator.domain.soap.statementrequest.StatementRequestResult;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.SoapServerStatementRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class StatementRequestController extends AbstractController {

    private static final String REQUEST_NAME = "Statement Request";

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

            StatementRequestDataDto dto = data.getDto();
            dto.setRequestId(dto.getAttrRequestId());
            dto.setResponseId(result.getResponseId());
            dto.setRequestName(REQUEST_NAME);
            List<DataTransferObject> requestList = (List<DataTransferObject>) httpSession.getAttribute("requestList");
            requestList.add(dto);

            return getSoapRequestSuccessResponse(result.getResponseId());
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

    @Autowired
    SoapMessageList soapMessageList;

    @Override
    protected ResponseBodyData getSoapRequestSuccessResponse(String message) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("StatementRequest to Soap server is success. requestID=" + message);
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        soapMessageList.clearLastRequestMessageList();
        return result;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("ERROR");
        result.setMessage("StatementRequest to Soap server is fail. message=" + exception.getSoapResponse());
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());
        soapMessageList.clearLastRequestMessageList();
        return result;
    }

}
