package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SoapMessageListController  extends AbstractController{

    private static Logger log = LoggerFactory.getLogger(SoapMessageListController.class);

    private static final String URI_GET_ALL_MESSAGE = "/soapMessage/list/all";
    private static final String URI_GET_LAST_REQUEST_MESSAGE = "/soapMessage/list/lastRequest";
    private static final String URI_REMOVE_ALL_MESSAGE = "/soapMessage/remove/all";

    @GetMapping(URI_GET_ALL_MESSAGE)
    @ResponseBody
    public ResponseBodyData getAllMessage(HttpSession httpSession) {
        log.info("Request uri='" + URI_GET_ALL_MESSAGE + "'");
        try {
            AppUser user = getUser(httpSession);
            return getSuccessResponseList();
        } catch (UserIsNotAuthorizedException e) {
            return getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return getServerFailResponse(e);
        }
    }

    @GetMapping(URI_GET_LAST_REQUEST_MESSAGE)
    @ResponseBody
    public ResponseBodyData getLastRequestMessage(HttpSession httpSession) {
        log.info("Request uri='" + URI_GET_LAST_REQUEST_MESSAGE + "'");
        try {
            AppUser user = getUser(httpSession);
            return getSuccessResponseLastRequest();
        } catch (UserIsNotAuthorizedException e) {
            return getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return getServerFailResponse(e);
        }
    }

    @GetMapping(URI_REMOVE_ALL_MESSAGE)
    @ResponseBody
    public ResponseBodyData removeAllMessage(HttpSession httpSession) {
        log.info("Request uri='" + URI_REMOVE_ALL_MESSAGE + "'");
        try {
            AppUser user = getUser(httpSession);
            clearSoapMessageList();
            return getSuccessResponseRemoveAllMessage();
        } catch (UserIsNotAuthorizedException e) {
            return getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return getServerFailResponse(e);
        }
    }

    private void clearSoapMessageList() {
        soapMessageList.clear();
    }

    private ResponseBodyData getSuccessResponseLastRequest() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Last Request soap message list");
        result.setSoapMessageList(soapMessageList.getLastRequestMessageList());

        log.info("Success request." + result.getLogInfo());
        return result;
    }

    private ResponseBodyData getSuccessResponseList() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Soap message list");
        result.setSoapMessageList(soapMessageList.getMessageList());

        log.info("Success request." + result.getLogInfo());
        return result;
    }

    private ResponseBodyData getSuccessResponseRemoveAllMessage() {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Remove soap message list");
        result.setSoapMessageList(soapMessageList.getMessageList());

        log.info("Success request." + result.getLogInfo());
        return result;
    }

    @Override
    protected ResponseBodyData getSoapRequestSuccessResponse(RequestChain chain) {
        return null;
    }

    @Override
    protected ResponseBodyData getSoapRequestFailResponse(SoapServerBadResponseException exception, RequestChain
            chain) {
        return null;
    }

}
