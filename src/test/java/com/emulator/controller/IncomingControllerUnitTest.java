package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

class IncomingControllerUnitTest {

    @Test
    void runIncoming_validData_callGetSuccessResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        IncomingData data = Mockito.mock(IncomingData.class);

        Mockito.when(chainPool.getRequestChain(any(), any())).thenReturn(chain);

        IncomingController controller = new IncomingController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runIncoming(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).getRequestChain(any(), any());
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(serviceController).getSoapRequestSuccessResponse(any(), any());
    }

    @Test
    void runIncoming_sessionHaveNotUser_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        IncomingData data = Mockito.mock(IncomingData.class);

        Mockito.when(serviceController.getUser(session)).thenThrow(UserIsNotAuthorizedException.class);

        IncomingController controller = new IncomingController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runIncoming(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void runIncoming_badResponseFromSoapServer_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        IncomingData data = Mockito.mock(IncomingData.class);

        Mockito.when(chainPool.getRequestChain(any(), any())).thenReturn(chain);
        doThrow(new SoapServerBadResponseException("test")).when(chain).nextStep(data);

        IncomingController controller = new IncomingController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runIncoming(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(chainPool).getRequestChain(any(), any());
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(serviceController).getSoapRequestFailResponse(any(), any(), any());
    }

    @Test
    void runPayRequest_invalidData_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        IncomingData data = Mockito.mock(IncomingData.class);

        doThrow(new RequestParameterLengthException("test")).when(data).check();

        IncomingController controller = new IncomingController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runIncoming(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(serviceController).getParameterLengthErrorResponse(any());
    }

    @Test
    void runIncoming_serverError_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        IncomingData data = Mockito.mock(IncomingData.class);

        Mockito.when(chainPool.getRequestChain(any(), any())).thenReturn(chain);
        doThrow(new RuntimeException("test")).when(chain).nextStep(data);

        IncomingController controller = new IncomingController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runIncoming(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).getRequestChain(any(), any());
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(serviceController).getServerFailResponse(any(), any());
    }
}