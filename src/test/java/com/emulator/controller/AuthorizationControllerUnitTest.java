package com.emulator.controller;

import com.emulator.domain.soap.requests.authorization.AppUserData;
import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

class AuthorizationControllerUnitTest {

    @Test
    void login_validData_callGetSuccessResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUserData data = Mockito.mock(AppUserData.class);
        AppUser user = Mockito.mock(AppUser.class);
        AppUser authorizedUser = Mockito.mock(AppUser.class);

        Mockito.when(data.getAppUser()).thenReturn(user);
        Mockito.when(soapClient.authorization(user)).thenReturn(authorizedUser);

        AuthorizationController controller = new AuthorizationController(log, soapClient, serviceController);
        ResponseBodyData result = controller.login(session, data);

        Assertions.assertNull(result);
        Mockito.verify(data).check();
        Mockito.verify(data).getAppUser();
        Mockito.verify(soapClient).authorization(user);
        Mockito.verify(session).setAttribute(eq("user"), eq(authorizedUser));
        Mockito.verify(serviceController).getSoapRequestSuccessResponse(authorizedUser);
    }

    @Test
    void login_badResponseFromSoapServer_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUserData data = Mockito.mock(AppUserData.class);
        AppUser user = Mockito.mock(AppUser.class);

        Mockito.when(data.getAppUser()).thenReturn(user);
        Mockito.when(soapClient.authorization(user)).thenThrow(SoapServerBadResponseException.class);

        AuthorizationController controller = new AuthorizationController(log, soapClient, serviceController);
        ResponseBodyData result = controller.login(session, data);

        Assertions.assertNull(result);
        Mockito.verify(data).check();
        Mockito.verify(data).getAppUser();
        Mockito.verify(soapClient).authorization(user);
        Mockito.verify(serviceController).getSoapRequestFailResponse(any(), any());
    }

    @Test
    void login_invalidData_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUserData data = Mockito.mock(AppUserData.class);

        doThrow(new RequestParameterLengthException("test")).when(data).check();

        AuthorizationController controller = new AuthorizationController(log, soapClient, serviceController);
        ResponseBodyData result = controller.login(session, data);

        Assertions.assertNull(result);
        Mockito.verify(data).check();
        Mockito.verify(serviceController).getParameterLengthErrorResponse(any());
    }

    @Test
    void login_serverError_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUserData data = Mockito.mock(AppUserData.class);
        AppUser user = Mockito.mock(AppUser.class);
        AppUser authorizedUser = Mockito.mock(AppUser.class);
        String attrName = "user";

        Mockito.when(data.getAppUser()).thenReturn(user);
        Mockito.when(soapClient.authorization(user)).thenReturn(authorizedUser);
        doThrow(new RuntimeException("test")).when(session).setAttribute(eq(attrName), eq(authorizedUser));

        AuthorizationController controller = new AuthorizationController(log, soapClient, serviceController);
        ResponseBodyData result = controller.login(session, data);

        Assertions.assertNull(result);
        Mockito.verify(data).check();
        Mockito.verify(data).getAppUser();
        Mockito.verify(soapClient).authorization(user);
        Mockito.verify(session).setAttribute(eq(attrName), eq(authorizedUser));
        Mockito.verify(serviceController).getServerFailResponse(any());
    }

    @Test
    void login_secondAuthorizationRequest_getAppUserFromSession() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUserData data = Mockito.mock(AppUserData.class);
        AppUser user = Mockito.mock(AppUser.class);
        AppUser authorizedUser = Mockito.mock(AppUser.class);
        String attrName = "user";

        Mockito.when(data.getAppUser()).thenReturn(user);
        Mockito.when(soapClient.authorization(user)).thenReturn(authorizedUser);
        Mockito.when(session.getAttribute(eq(attrName))).thenReturn(authorizedUser);

        AuthorizationController controller = new AuthorizationController(log, soapClient, serviceController);
        controller.login(session, data);

        Mockito.verify(data).check();
        Mockito.verify(session).getAttribute(eq(attrName));
        Mockito.verify(serviceController).getSoapRequestSuccessResponse(authorizedUser);
    }
}