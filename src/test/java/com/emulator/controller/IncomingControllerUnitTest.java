package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

class IncomingControllerUnitTest {

    private Logger log;
    private RequestChainPool chainPool;
    private ServiceController serviceController;
    private HttpSession session;
    private AppUser user;
    private RequestChain chain;
    private IncomingData data;

    @BeforeEach
    void before() {
        log = Mockito.mock(Logger.class);
        chainPool = Mockito.mock(RequestChainPool.class);
        serviceController = Mockito.mock(ServiceController.class);
        session = Mockito.mock(HttpSession.class);
        user = Mockito.mock(AppUser.class);
        chain = Mockito.mock(RequestChain.class);
        data = Mockito.mock(IncomingData.class);
    }

    @Test
    void runIncoming_validData_callGetSuccessResponse() throws Exception {
        //given
        Mockito.when(serviceController.getUser(session)).thenReturn(user);
        Mockito.when(chainPool.getRequestChain(user, data)).thenReturn(chain);

        //when
        IncomingController controller = new IncomingController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runIncoming(session, data);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).getRequestChain(user, data);
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(serviceController).getSoapRequestSuccessResponse(any(), any());
    }

    @Test
    void runIncoming_sessionHaveNotUser_callGetErrorResponse() throws Exception {
        //given
        Mockito.when(serviceController.getUser(session)).thenThrow(UserIsNotAuthorizedException.class);

        //then
        IncomingController controller = new IncomingController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runIncoming(session, data);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void runIncoming_badResponseFromSoapServer_callGetErrorResponse() throws Exception {
        //given
        Mockito.when(serviceController.getUser(session)).thenReturn(user);
        Mockito.when(chainPool.getRequestChain(user, data)).thenReturn(chain);
        doThrow(new SoapServerBadResponseException("test")).when(chain).nextStep(data);

        //when
        IncomingController controller = new IncomingController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runIncoming(session, data);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(chainPool).getRequestChain(user, data);
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(serviceController).getSoapRequestFailResponse(any(), any(), any());
    }

    @Test
    void runPayRequest_invalidData_callGetErrorResponse() throws Exception {
        //given
        doThrow(new RequestParameterLengthException("test")).when(data).check();

        //when
        IncomingController controller = new IncomingController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runIncoming(session, data);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(serviceController).getParameterLengthErrorResponse(any());
    }

    @Test
    void runIncoming_serverError_callGetErrorResponse() throws Exception {
        //given
        Mockito.when(serviceController.getUser(session)).thenReturn(user);
        Mockito.when(chainPool.getRequestChain(user, data)).thenReturn(chain);
        doThrow(new RuntimeException("test")).when(chain).nextStep(data);

        //when
        IncomingController controller = new IncomingController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runIncoming(session, data);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).getRequestChain(user, data);
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(serviceController).getServerFailResponse(any(), any());
    }
}