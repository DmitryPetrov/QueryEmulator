package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

class SoapMessageListControllerUnitTest {

    @Test
    void getAllMessage_validData_callGetSuccessResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        SoapMessageListController controller = new SoapMessageListController(log, serviceController);
        ResponseBodyData result = controller.getAllMessage(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getSuccessResponseAllSoapMessage();
    }

    @Test
    void getAllMessage_sessionHaveNotUser_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(serviceController.getUser(session)).thenThrow(UserIsNotAuthorizedException.class);

        SoapMessageListController controller = new SoapMessageListController(log, serviceController);
        ResponseBodyData result = controller.getAllMessage(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void getAllMessage_serverError_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(serviceController.getUser(session)).thenThrow(RuntimeException.class);

        SoapMessageListController controller = new SoapMessageListController(log, serviceController);
        ResponseBodyData result = controller.getAllMessage(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getServerFailResponse(any());
    }



    @Test
    void getLastRequestMessage_validData_callGetSuccessResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        SoapMessageListController controller = new SoapMessageListController(log, serviceController);
        ResponseBodyData result = controller.getLastRequestMessage(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getSuccessResponseLastRequestSoapMessage();
    }

    @Test
    void getLastRequestMessage_sessionHaveNotUser_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(serviceController.getUser(session)).thenThrow(UserIsNotAuthorizedException.class);

        SoapMessageListController controller = new SoapMessageListController(log, serviceController);
        ResponseBodyData result = controller.getLastRequestMessage(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void getLastRequestMessage_serverError_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(serviceController.getUser(session)).thenThrow(RuntimeException.class);

        SoapMessageListController controller = new SoapMessageListController(log, serviceController);
        ResponseBodyData result = controller.getLastRequestMessage(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getServerFailResponse(any());
    }



    @Test
    void removeAllMessage_validData_callGetSuccessResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        SoapMessageListController controller = new SoapMessageListController(log, serviceController);
        ResponseBodyData result = controller.removeAllMessage(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).clearSoapMessageList();
        Mockito.verify(serviceController).getSuccessResponseRemoveAllSoapMessage();
    }

    @Test
    void removeAllMessage_sessionHaveNotUser_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(serviceController.getUser(session)).thenThrow(UserIsNotAuthorizedException.class);

        SoapMessageListController controller = new SoapMessageListController(log, serviceController);
        ResponseBodyData result = controller.removeAllMessage(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void removeAllMessage_serverError_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doThrow(new RuntimeException("test")).when(serviceController).clearSoapMessageList();

        SoapMessageListController controller = new SoapMessageListController(log, serviceController);
        ResponseBodyData result = controller.removeAllMessage(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).clearSoapMessageList();
        Mockito.verify(serviceController).getServerFailResponse(any());
    }
}