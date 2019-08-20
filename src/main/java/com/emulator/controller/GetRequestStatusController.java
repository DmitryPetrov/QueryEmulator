package com.emulator.controller;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.frontend.response.DataTransferObject;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.frontend.response.getrequeststatus.GetRequestStatusResultDto;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.SoapMessageList;
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

    private static final String REQUEST_NAME = "Get Request Status";
    private static final String NOT_PROCESSED_YET_STATUS = "NOT PROCESSED YET";


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
            GetRequestStatusResultDto dto = result.getDto();
            dto.setRequestId(result.getAttrRequestId());
            dto.setResponseId(result.getAttrResponseId());
            dto.setRequestName(REQUEST_NAME);
            if (result.isNotProcessedYet()) {
                dto.getStateResponse().setState(NOT_PROCESSED_YET_STATUS);
            }

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

    protected ResponseBodyData getSoapRequestSuccessResponse(GetRequestStatusResultDto requestResult) {
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

    private void checkResponseId(HttpSession httpSession, String responseId) {
        boolean requestFound = false;

        List<DataTransferObject> requestList = (List<DataTransferObject>) httpSession.getAttribute("requestList");
        for (DataTransferObject request: requestList) {
            if (request.getResponseId().equals(responseId)){
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
