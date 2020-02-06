package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.soap.SoapMessageStorage;
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
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUser user = Mockito.mock(AppUser.class);

        Mockito.when(session.getAttribute(eq("user"))).thenReturn(user);

        ServiceController controller = new ServiceController(log, messageStorage);

        Assertions.assertEquals(user, controller.getUser(session));
    }

    @Test
    void getUser_validData_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(session.getAttribute(eq("user"))).thenThrow(UserIsNotAuthorizedException.class);

        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new ServiceController(log, messageStorage).getUser(session),
                eq("User is not authorized")
        );
    }

    @Test
    void getUserIsNotAuthorizedResponse_empty_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);

        ResponseBodyData data = new ServiceController(log, messageStorage).getUserIsNotAuthorizedResponse();

        Assertions.assertEquals("ERROR", data.getStatus());
        Assertions.assertEquals("User is not authorized.", data.getMessage());
    }

    @Test
    void getParameterLengthErrorResponse_validData_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        RequestParameterLengthException exception = Mockito.mock(RequestParameterLengthException.class);
        String parameterName = "test";
        int maxLength = 9;

        Mockito.when(exception.getParameterName()).thenReturn(parameterName);
        Mockito.when(exception.getMaxLength()).thenReturn(maxLength);

        ResponseBodyData data = new ServiceController(log, messageStorage).getParameterLengthErrorResponse(exception);

        Assertions.assertEquals("ERROR", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains(parameterName));
        Assertions.assertTrue(data.getMessage().contains(Integer.toString(maxLength)));
    }

    @Test
    void getServerFailResponse_validData_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        List<String> list = Mockito.mock(List.class);
        Exception exception = Mockito.mock(Exception.class);
        String errorMessage = "test";

        Mockito.when(exception.getMessage()).thenReturn(errorMessage);
        Mockito.when(messageStorage.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageStorage).getServerFailResponse(exception);

        Assertions.assertEquals("ERROR", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains(errorMessage));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void getServerFailResponse1_validData_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        List<String> list = Mockito.mock(List.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        Exception exception = Mockito.mock(Exception.class);
        String errorMessage = "test";

        Mockito.when(exception.getMessage()).thenReturn(errorMessage);
        Mockito.when(messageStorage.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageStorage).getServerFailResponse(exception, chain);

        Assertions.assertEquals("ERROR", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains(errorMessage));
        Assertions.assertEquals(list, data.getSoapMessageList());
        Assertions.assertEquals(chain, data.getRequestChain());
    }

    @Test
    void getSoapRequestFailResponse_validData_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        List<String> list = Mockito.mock(List.class);
        SoapServerBadResponseException exception = Mockito.mock(SoapServerBadResponseException.class);
        String requestName = "test";
        String soapResponse = "test 2";

        Mockito.when(exception.getSoapResponse()).thenReturn(soapResponse);
        Mockito.when(messageStorage.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageStorage).getSoapRequestFailResponse(exception,
                requestName);

        Assertions.assertEquals("ERROR", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains(requestName));
        Assertions.assertTrue(data.getMessage().contains(soapResponse));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void getSoapRequestFailResponse1_validData_errorResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        List<String> list = Mockito.mock(List.class);
        SoapServerBadResponseException exception = Mockito.mock(SoapServerBadResponseException.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        String requestName = "test";
        String soapResponse = "test 2";

        Mockito.when(exception.getSoapResponse()).thenReturn(soapResponse);
        Mockito.when(messageStorage.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageStorage).getSoapRequestFailResponse(exception, chain,
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
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        List<RequestChain> chainList = Mockito.mock(List.class);

        ResponseBodyData data = new ServiceController(log, messageStorage).getSuccessResponse(chainList);

        Assertions.assertEquals("OK", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains("Request list"));
        Assertions.assertEquals(chainList, data.getRequestChainList());
    }

    @Test
    void getSoapRequestSuccessResponse_validData_successResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        List<String> list = Mockito.mock(List.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        String requestName = "test";
        String responseId = "test 2";

        Mockito.when(chain.getResponseId()).thenReturn(responseId);
        Mockito.when(messageStorage.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageStorage).getSoapRequestSuccessResponse(chain,
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
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        List<String> list = Mockito.mock(List.class);
        AppUser user = Mockito.mock(AppUser.class);
        String sessionId = "test";

        Mockito.when(user.getSessionId()).thenReturn(sessionId);
        Mockito.when(messageStorage.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageStorage).getSoapRequestSuccessResponse(user);

        Assertions.assertEquals("OK", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains("Authorization"));
        Assertions.assertTrue(data.getMessage().contains(sessionId));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void getSuccessResponseLastRequestSoapMessage_empty_successResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        List<String> list = Mockito.mock(List.class);

        Mockito.when(messageStorage.getLastRequestMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageStorage).getSuccessResponseLastRequestSoapMessage();

        Assertions.assertEquals("OK", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains("Last Request"));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void getSuccessResponseAllSoapMessage_empty_successResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        List<String> list = Mockito.mock(List.class);

        Mockito.when(messageStorage.getMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageStorage).getSuccessResponseAllSoapMessage();

        Assertions.assertEquals("OK", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains("All soap message list"));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void getSuccessResponseRemoveAllSoapMessage_empty_successResponseObject() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        List<String> list = Mockito.mock(List.class);

        Mockito.when(messageStorage.getMessageList()).thenReturn(list);

        ResponseBodyData data = new ServiceController(log, messageStorage).getSuccessResponseRemoveAllSoapMessage();

        Assertions.assertEquals("OK", data.getStatus());
        Assertions.assertTrue(data.getMessage().contains("Remove soap message list"));
        Assertions.assertEquals(list, data.getSoapMessageList());
    }

    @Test
    void clearSoapMessageList_empty_methodHasBeenCalled() {
        Logger log = Mockito.mock(Logger.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);

        new ServiceController(log, messageStorage).clearSoapMessageList();

        Mockito.verify(messageStorage).clear();
    }
}