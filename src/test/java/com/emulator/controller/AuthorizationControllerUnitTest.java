package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.requests.authorization.AppUserData;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.service.AuthorizationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;

class AuthorizationControllerUnitTest {

    private ServiceController serviceController;
    private AuthorizationService authService;
    private HttpSession session;
    private AppUserData data;
    private ResponseBodyData response;

    @BeforeEach
    void before() {
        serviceController = Mockito.mock(ServiceController.class);
        authService = Mockito.mock(AuthorizationService.class);
        session = Mockito.mock(HttpSession.class);
        data = Mockito.mock(AppUserData.class);
        response = Mockito.mock(ResponseBodyData.class);
    }

    @Test
    void login_happyPath_callGetSuccessResponse() throws Exception {
        // given
        Mockito.when(authService.authorization(session, data)).thenReturn(response);

        // when
        ResponseBodyData result = new AuthorizationController(authService, serviceController).login(session, data);

        // then
        Assertions.assertSame(response, result);
        Mockito.verify(authService).authorization(session, data);
    }

    @Test
    void login_badResponseFromSoapServer_callGetErrorResponse() throws Exception {
        // given
        Mockito.when(authService.authorization(session, data)).thenThrow(SoapServerBadResponseException.class);

        // when
        new AuthorizationController(authService, serviceController).login(session, data);

        // then
        Mockito.verify(authService).authorization(session, data);
        Mockito.verify(serviceController).getSoapRequestFailResponse(any(), any());
    }

    @Test
    void login_invalidData_callGetErrorResponse() throws Exception {
        // given
        Mockito.when(authService.authorization(session, data)).thenThrow(RequestParameterLengthException.class);

        // when
        new AuthorizationController(authService, serviceController).login(session, data);

        // then
        Mockito.verify(authService).authorization(session, data);
        Mockito.verify(serviceController).getParameterLengthErrorResponse(any());
    }

    @Test
    void login_serverError_callGetErrorResponse() throws Exception {
        // given
        Mockito.when(authService.authorization(session, data)).thenThrow(RuntimeException.class);

        // when
        new AuthorizationController(authService, serviceController).login(session, data);

        // then
        Mockito.verify(authService).authorization(session, data);
        Mockito.verify(serviceController).getServerFailResponse(any());
    }

}