package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.PayRequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.domain.soap.requests.payrequest.PayRequestData;
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
import static org.mockito.Mockito.doThrow;

class PayRequestControllerUnitTest {

    private Logger log;
    private RequestChainPool chainPool;
    private ServiceController serviceController;
    private PayRequestChain chain;
    private HttpSession session;
    private PayRequestData data;

    @BeforeEach
    void before() {
        log = Mockito.mock(Logger.class);
        chainPool = Mockito.mock(RequestChainPool.class);
        serviceController = Mockito.mock(ServiceController.class);
        chain = Mockito.mock(PayRequestChain.class);
        session = Mockito.mock(HttpSession.class);
        data = Mockito.mock(PayRequestData.class);
    }

    @Test
    void runPayRequest_validData_callGetSuccessResponse() throws Exception {
        //given
        Mockito.when(chainPool.createPayRequestChain(any())).thenReturn(chain);

        //when
        PayRequestController controller = new PayRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runPayRequest(session, data);

        //then
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
        //given
        Mockito.when(serviceController.getUser(any())).thenThrow(UserIsNotAuthorizedException.class);

        //when
        PayRequestController controller = new PayRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runPayRequest(session, data);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void runPayRequest_badResponseFromSoapServer_callGetErrorResponse() throws Exception {
        //given
        Mockito.when(chainPool.createPayRequestChain(any())).thenReturn(chain);
        doThrow(new SoapServerBadResponseException("test")).when(chain).nextStep(data);

        //when
        PayRequestController controller = new PayRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runPayRequest(session, data);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).createPayRequestChain(any());
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(serviceController).getSoapRequestFailResponse(any(), any(), any());
    }

    @Test
    void runPayRequest_invalidData_callGetErrorResponse() throws Exception {
        //given
        doThrow(new RequestParameterLengthException("test")).when(data).check();

        //then
        PayRequestController controller = new PayRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runPayRequest(session, data);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(serviceController).getParameterLengthErrorResponse(any());
    }

    @Test
    void runPayRequest_serverError_callGetErrorResponse() throws Exception {
        //given
        Mockito.when(chainPool.createPayRequestChain(any())).thenReturn(chain);
        doThrow(new RuntimeException("test")).when(chainPool).addToPool(any());

        //when
        PayRequestController controller = new PayRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runPayRequest(session, data);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).createPayRequestChain(any());
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(chainPool).addToPool(chain);
        Mockito.verify(serviceController).getServerFailResponse(any(), any());
    }
}