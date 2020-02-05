package com.emulator.service;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.authorization.AppUserData;
import com.emulator.exception.RequestParameterLengthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class AuthorizationServiceUnitTest {

    @Test
    void authorization_happyPath_successResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUserData data = Mockito.mock(AppUserData.class);
        AppUser user = Mockito.mock(AppUser.class);

        Mockito.when(soapClient.authorization(data)).thenReturn(user);

        // when
        ResponseBodyData result = new AuthorizationService(log, soapClient).authorization(session, data);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("OK", result.getStatus());
        Mockito.verify(soapClient).authorization(data);
    }

    @Test
    void authorization_invalidData_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUserData data = Mockito.mock(AppUserData.class);

        doThrow(new RequestParameterLengthException("")).when(data).check();

        // then
        Assertions.assertThrows(
                RuntimeException.class,
                () -> new AuthorizationService(log, soapClient).authorization(session, data));
    }

    @Test
    void authorization_userAlreadyAuthorized_successResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUserData data = Mockito.mock(AppUserData.class);
        AppUser user = Mockito.mock(AppUser.class);
        String attrName = "user";

        Mockito.when(session.getAttribute(eq(attrName))).thenReturn(user);

        // when
        ResponseBodyData result = new AuthorizationService(log, soapClient).authorization(session, data);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("OK", result.getStatus());
        Mockito.verify(soapClient, times(0)).authorization(data);
    }
}