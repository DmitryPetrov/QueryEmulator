package com.emulator.service;

import com.emulator.domain.frontend.response.ResponseSoapMessageList;
import com.emulator.domain.soap.SoapMessageStorage;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.doThrow;

class SoapMessageServiceUnitTest {

    @Test
    void getAll_happyPath_successResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService userService = Mockito.mock(UserService.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        // when
        ResponseSoapMessageList result = new SoapMessageService(log, userService, messageStorage).getAll(session);

        // then
        Assertions.assertEquals("OK", result.getStatus());
        Mockito.verify(messageStorage).getMessageList();
    }

    @Test
    void getAll_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService userService = Mockito.mock(UserService.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doThrow(new UserIsNotAuthorizedException("")).when(userService).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new SoapMessageService(log, userService, messageStorage).getAll(session));
    }


    @Test
    void getLastRequest_happyPath_successResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService userService = Mockito.mock(UserService.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        // when
        ResponseSoapMessageList result = new SoapMessageService(log, userService, messageStorage).getLastRequest(session);

        // then
        Assertions.assertEquals("OK", result.getStatus());
        Mockito.verify(messageStorage).getLastRequestMessageList();
    }

    @Test
    void getLastRequest_sessionHaveNotUser_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService userService = Mockito.mock(UserService.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doThrow(new UserIsNotAuthorizedException("")).when(userService).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new SoapMessageService(log, userService, messageStorage).getLastRequest(session));
    }


    @Test
    void removeAll_happyPath_successResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService userService = Mockito.mock(UserService.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        // when
        ResponseSoapMessageList result = new SoapMessageService(log, userService, messageStorage).removeAll(session);

        // then
        Assertions.assertEquals("OK", result.getStatus());
        Mockito.verify(messageStorage).clear();
    }

    @Test
    void removeAllMessage_sessionHaveNotUser_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService userService = Mockito.mock(UserService.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doThrow(new UserIsNotAuthorizedException("")).when(userService).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new SoapMessageService(log, userService, messageStorage).removeAll(session));
    }

}