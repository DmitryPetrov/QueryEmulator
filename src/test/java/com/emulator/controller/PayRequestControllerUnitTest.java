package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.PayRequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.domain.soap.requests.payrequest.PayRequestData;
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

class PayRequestControllerUnitTest {

    @Test
    void runPayRequest_validData_callGetSuccessResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        PayRequestChain chain = Mockito.mock(PayRequestChain.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        PayRequestData data = Mockito.mock(PayRequestData.class);

        Mockito.when(chainPool.createPayRequestChain(any())).thenReturn(chain);

        PayRequestController controller = new PayRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runPayRequest(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).createPayRequestChain(any());
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(chainPool).addToPool(chain);
        Mockito.verify(serviceController).getSoapRequestSuccessResponse(any(), any());
    }

    @Test
    void runPayRequest_sessionHaveNotUser_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        PayRequestData data = Mockito.mock(PayRequestData.class);

        Mockito.when(serviceController.getUser(any())).thenThrow(UserIsNotAuthorizedException.class);

        PayRequestController controller = new PayRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runPayRequest(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void runPayRequest_badResponseFromSoapServer_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        PayRequestChain chain = Mockito.mock(PayRequestChain.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        PayRequestData data = Mockito.mock(PayRequestData.class);

        Mockito.when(chainPool.createPayRequestChain(any())).thenReturn(chain);
        doThrow(new SoapServerBadResponseException("test")).when(chain).nextStep(data);

        PayRequestController controller = new PayRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runPayRequest(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).createPayRequestChain(any());
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(serviceController).getSoapRequestFailResponse(any(), any(), any());
    }

    @Test
    void runPayRequest_invalidData_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        PayRequestData data = Mockito.mock(PayRequestData.class);

        doThrow(new RequestParameterLengthException("test")).when(data).check();

        PayRequestController controller = new PayRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runPayRequest(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(serviceController).getParameterLengthErrorResponse(any());
    }

    @Test
    void runPayRequest_serverError_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        PayRequestChain chain = Mockito.mock(PayRequestChain.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        PayRequestData data = Mockito.mock(PayRequestData.class);

        Mockito.when(chainPool.createPayRequestChain(any())).thenReturn(chain);
        doThrow(new RuntimeException("test")).when(chainPool).addToPool(any());

        PayRequestController controller = new PayRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runPayRequest(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).createPayRequestChain(any());
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(chainPool).addToPool(chain);
        Mockito.verify(serviceController).getServerFailResponse(any(), any());
    }
}