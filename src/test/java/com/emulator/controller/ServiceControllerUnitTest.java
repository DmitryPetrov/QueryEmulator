package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

class ServiceControllerUnitTest {

    @Test
    void getUser_validData_appUser() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUser user = Mockito.mock(AppUser.class);

        Mockito.when(session.getAttribute(eq("user"))).thenReturn(user);

        ServiceController controller = new ServiceController(log, messageList);

        Assertions.assertEquals(user, controller.getUser(session));
    }

    @Test
    void getUser_validData_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(session.getAttribute(eq("user"))).thenThrow(UserIsNotAuthorizedException.class);

        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new ServiceController(log, messageList).getUser(session),
                eq("User is not authorized")
        );
    }

    @Test
    void getUserIsNotAuthorizedResponse_empty_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);

        ResponseBodyData data = new ServiceController(log, messageList).getUserIsNotAuthorizedResponse();

        Assertions.assertEquals("ERROR", data.getStatus());
        Assertions.assertEquals("User is not authorized.", data.getMessage());
    }

    @Test
    void getParameterLengthErrorResponse_validData_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        RequestParameterLengthException exception = Mockito.mock(RequestParameterLengthException.class);
        String parameterName = "test";
        int maxLength = 9;

        Mockito.when(exception.getParameterName()).thenReturn(parameterName);
        Mockito.when(exception.getMaxLength()).thenReturn(maxLength);

        ResponseBodyData data = new ServiceController(log, messageList).getParameterLengthErrorResponse(exception);

        Assertions.assertEquals("ERROR", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains(parameterName));
        Assertions.assertTrue(data.getMessage().contains(Integer.toString(maxLength)));
    }

    @Test
    void getServerFailResponse_validData_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        List<String> list = Mockito.mock(List.class);
        Exception exception = Mockito.mock(Exception.class);
        String errorMessage = "test";

        Mockito.when(exception.getMessage()).thenReturn(errorMessage);
        Mockito.when(messageList.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageList).getServerFailResponse(exception);

        Assertions.assertEquals("ERROR", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains(errorMessage));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void getServerFailResponse1_validData_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        List<String> list = Mockito.mock(List.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        Exception exception = Mockito.mock(Exception.class);
        String errorMessage = "test";

        Mockito.when(exception.getMessage()).thenReturn(errorMessage);
        Mockito.when(messageList.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageList).getServerFailResponse(exception, chain);

        Assertions.assertEquals("ERROR", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains(errorMessage));
        Assertions.assertEquals(list, data.getSoapMessageList());
        Assertions.assertEquals(chain, data.getRequestChain());
    }

    @Test
    void getSoapRequestFailResponse_validData_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        List<String> list = Mockito.mock(List.class);
        SoapServerBadResponseException exception = Mockito.mock(SoapServerBadResponseException.class);
        String requestName = "test";
        String soapResponse = "test 2";

        Mockito.when(exception.getSoapResponse()).thenReturn(soapResponse);
        Mockito.when(messageList.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageList).getSoapRequestFailResponse(exception,
                requestName);

        Assertions.assertEquals("ERROR", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains(requestName));
        Assertions.assertTrue(data.getMessage().contains(soapResponse));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void getSoapRequestFailResponse1_validData_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        List<String> list = Mockito.mock(List.class);
        SoapServerBadResponseException exception = Mockito.mock(SoapServerBadResponseException.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        String requestName = "test";
        String soapResponse = "test 2";

        Mockito.when(exception.getSoapResponse()).thenReturn(soapResponse);
        Mockito.when(messageList.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageList).getSoapRequestFailResponse(exception, chain,
                requestName);

        Assertions.assertEquals("ERROR", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains(requestName));
        Assertions.assertTrue(data.getMessage().contains(soapResponse));
        Assertions.assertEquals(list, data.getSoapMessageList());
        Assertions.assertEquals(chain, data.getRequestChain());
    }

    @Test
    void getSuccessResponse_validData_successResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        List<RequestChain> chainList = Mockito.mock(List.class);

        ResponseBodyData data = new ServiceController(log, messageList).getSuccessResponse(chainList);

        Assertions.assertEquals("OK", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains("Request list"));
        Assertions.assertEquals(chainList, data.getRequestChainList());
    }

    @Test
    void getSoapRequestSuccessResponse_validData_successResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        List<String> list = Mockito.mock(List.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        String requestName = "test";
        String responseId = "test 2";

        Mockito.when(chain.getResponseId()).thenReturn(responseId);
        Mockito.when(messageList.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageList).getSoapRequestSuccessResponse(chain,
                requestName);

        Assertions.assertEquals("OK", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains(requestName));
        Assertions.assertTrue(data.getMessage().contains(responseId));
        Assertions.assertEquals(chain, data.getRequestChain());
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void getSoapRequestSuccessResponse1_validData_successResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        List<String> list = Mockito.mock(List.class);
        AppUser user = Mockito.mock(AppUser.class);
        String sessionId = "test";

        Mockito.when(user.getSessionId()).thenReturn(sessionId);
        Mockito.when(messageList.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageList).getSoapRequestSuccessResponse(user);

        Assertions.assertEquals("OK", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains("Authorization"));
        Assertions.assertTrue(data.getMessage().contains(sessionId));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void getSuccessResponseLastRequestSoapMessage_empty_successResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        List<String> list = Mockito.mock(List.class);

        Mockito.when(messageList.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageList).getSuccessResponseLastRequestSoapMessage();

        Assertions.assertEquals("OK", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains("Last Request"));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void getSuccessResponseAllSoapMessage_empty_successResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        List<String> list = Mockito.mock(List.class);

        Mockito.when(messageList.getMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageList).getSuccessResponseAllSoapMessage();

        Assertions.assertEquals("OK", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains("All soap message list"));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void getSuccessResponseRemoveAllSoapMessage_empty_successResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);
        List<String> list = Mockito.mock(List.class);

        Mockito.when(messageList.getMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageList).getSuccessResponseRemoveAllSoapMessage();

        Assertions.assertEquals("OK", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains("Remove soap message list"));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void clearSoapMessageList_empty_methodHasBeenCalled() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageList messageList = Mockito.mock(SoapMessageList.class);

        new ServiceController(log, messageList).clearSoapMessageList();

        Mockito.verify(messageList).clear();
    }
}