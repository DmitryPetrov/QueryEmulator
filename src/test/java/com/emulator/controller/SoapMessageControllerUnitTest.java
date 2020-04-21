package com.emulator.controller;

import com.emulator.domain.frontend.response.Response;
import com.emulator.domain.frontend.response.ResponseSoapMessageList;
import com.emulator.exception.UserIsNotAuthorizedException;
import com.emulator.service.SoapMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;

class SoapMessageControllerUnitTest {

    private ServiceController serviceController;
    private SoapMessageService messageService;
    private HttpSession session;
    private ResponseSoapMessageList response;

    @BeforeEach
    void before() {
        serviceController = Mockito.mock(ServiceController.class);
        messageService = Mockito.mock(SoapMessageService.class);
        session = Mockito.mock(HttpSession.class);
        response = Mockito.mock(ResponseSoapMessageList.class);
    }

    @Test
    void getAllMessages_validData_callGetSuccessResponse() throws Exception {
        // given
        Mockito.when(messageService.getAll(session)).thenReturn(response);

        // when
        Response result = new SoapMessageController(serviceController, messageService).getAllMessages(session);

        // then
        Assertions.assertSame(result, response);
    }

    @Test
    void getAllMessages_userNotAuthorized_callGetErrorResponse() throws Exception {
        // given
        Mockito.when(messageService.getAll(session)).thenThrow(UserIsNotAuthorizedException.class);

        // when
        new SoapMessageController(serviceController, messageService).getAllMessages(session);

        // then
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void getAllMessages_serverError_callGetErrorResponse() throws Exception {
        // given
        Mockito.when(messageService.getAll(session)).thenThrow(RuntimeException.class);

        // when
        new SoapMessageController(serviceController, messageService).getAllMessages(session);

        // then
        Mockito.verify(serviceController).getServerFailResponse(any());
    }

    @Test
    void getLastRequestMessages_validData_callGetSuccessResponse() throws Exception {
        // given
        Mockito.when(messageService.getLastRequest(session)).thenReturn(response);

        // when
        Response result = new SoapMessageController(serviceController, messageService).getLastRequestMessages(session);

        // then
        Assertions.assertSame(result, response);
    }

    @Test
    void getLastRequestMessages_userNotAuthorized_callGetErrorResponse() throws Exception {
        // given
        Mockito.when(messageService.getLastRequest(session)).thenThrow(UserIsNotAuthorizedException.class);

        // when
        new SoapMessageController(serviceController, messageService).getLastRequestMessages(session);

        // then
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void getLastRequestMessages_serverError_callGetErrorResponse() throws Exception {
        // given
        Mockito.when(messageService.getLastRequest(session)).thenThrow(RuntimeException.class);

        // when
        new SoapMessageController(serviceController, messageService).getLastRequestMessages(session);

        // then
        Mockito.verify(serviceController).getServerFailResponse(any());
    }



    @Test
    void removeAllMessages_validData_callGetSuccessResponse() throws Exception {
        // given
        Mockito.when(messageService.removeAll(session)).thenReturn(response);

        // when
        Response result = new SoapMessageController(serviceController, messageService).removeAllMessages(session);

        // then
        Assertions.assertSame(result, response);
    }

    @Test
    void removeAllMessages_sessionHaveNotUser_callGetErrorResponse() throws Exception {
        // given
        Mockito.when(messageService.removeAll(session)).thenThrow(UserIsNotAuthorizedException.class);

        // when
        new SoapMessageController(serviceController, messageService).removeAllMessages(session);

        // then
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void removeAllMessages_serverError_callGetErrorResponse() throws Exception {
        // given
        Mockito.when(messageService.removeAll(session)).thenThrow(RuntimeException.class);

        // when
        new SoapMessageController(serviceController, messageService).removeAllMessages(session);

        // then
        Mockito.verify(serviceController).getServerFailResponse(any());
    }
}