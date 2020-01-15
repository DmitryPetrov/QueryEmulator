package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.domain.soap.requests.getrequeststatus.GetRequestStatusData;
import com.emulator.exception.SoapServerBadResponseException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;

class GetRequestStatusControllerUnitTest {

    @Test
    void runGetRequestStatus_validData_callGetSuccessResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        GetRequestStatusData data = Mockito.mock(GetRequestStatusData.class);

        Mockito.when(chainPool.getRequestChain(any(), eq(data))).thenReturn(chain);

        GetRequestStatusController controller = new GetRequestStatusController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runGetRequestStatus(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(chainPool).getRequestChain(any(), eq(data));
        Mockito.verify(chain).nextStep();
        Mockito.verify(serviceController).getSoapRequestSuccessResponse(any(), any());
    }

    @Test
    void runGetRequestStatus_sessionHaveNotUser_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        GetRequestStatusData data = Mockito.mock(GetRequestStatusData.class);

        Mockito.when(serviceController.getUser(session)).thenThrow(UserIsNotAuthorizedException.class);

        GetRequestStatusController controller = new GetRequestStatusController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runGetRequestStatus(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void runGetRequestStatus_badResponseFromSoapServer_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        GetRequestStatusData data = Mockito.mock(GetRequestStatusData.class);

        Mockito.when(chainPool.getRequestChain(any(), eq(data))).thenReturn(chain);
        doThrow(new SoapServerBadResponseException("test")).when(chain).nextStep();

        GetRequestStatusController controller = new GetRequestStatusController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runGetRequestStatus(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(chainPool).getRequestChain(any(), eq(data));
        Mockito.verify(chain).nextStep();
        Mockito.verify(serviceController).getSoapRequestFailResponse(any(), any(), any());
    }

    @Test
    void runGetRequestStatus_serverError_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        RequestChain chain = Mockito.mock(RequestChain.class);
        GetRequestStatusData data = Mockito.mock(GetRequestStatusData.class);

        Mockito.when(chainPool.getRequestChain(any(), eq(data))).thenReturn(chain);
        doThrow(new RuntimeException("test")).when(chain).nextStep();

        GetRequestStatusController controller = new GetRequestStatusController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runGetRequestStatus(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(chainPool).getRequestChain(any(), eq(data));
        Mockito.verify(chain).nextStep();
        Mockito.verify(serviceController).getServerFailResponse(any(), any());
    }
}