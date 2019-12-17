package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.domain.requestchain.StatementRequestChain;
import com.emulator.domain.soap.requests.statementrequest.StatementRequestData;
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

class StatementRequestControllerUnitTest {

    @Test
    void runStatementRequest_validData_callGetSuccessResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        StatementRequestChain chain = Mockito.mock(StatementRequestChain.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        StatementRequestData data = Mockito.mock(StatementRequestData.class);

        Mockito.when(chainPool.createStatementRequestChain(any())).thenReturn(chain);

        StatementRequestController controller = new StatementRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runStatementRequest(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).createStatementRequestChain(any());
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(chainPool).addToPool(chain);
        Mockito.verify(serviceController).getSoapRequestSuccessResponse(any(), any());
    }

    @Test
    void runStatementRequest_sessionHaveNotUser_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        StatementRequestData data = Mockito.mock(StatementRequestData.class);

        Mockito.when(serviceController.getUser(any())).thenThrow(UserIsNotAuthorizedException.class);

        StatementRequestController controller = new StatementRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runStatementRequest(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void runStatementRequest_badResponseFromSoapServer_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        StatementRequestChain chain = Mockito.mock(StatementRequestChain.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        StatementRequestData data = Mockito.mock(StatementRequestData.class);

        Mockito.when(chainPool.createStatementRequestChain(any())).thenReturn(chain);
        doThrow(new SoapServerBadResponseException("test")).when(chain).nextStep(data);

        StatementRequestController controller = new StatementRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runStatementRequest(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).createStatementRequestChain(any());
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(serviceController).getSoapRequestFailResponse(any(), any(), any());
    }

    @Test
    void runStatementRequest_invalidData_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        StatementRequestData data = Mockito.mock(StatementRequestData.class);

        doThrow(new RequestParameterLengthException("test")).when(data).check();

        StatementRequestController controller = new StatementRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runStatementRequest(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(serviceController).getParameterLengthErrorResponse(any());
    }

    @Test
    void runStatementRequest_serverError_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        StatementRequestChain chain = Mockito.mock(StatementRequestChain.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        StatementRequestData data = Mockito.mock(StatementRequestData.class);

        Mockito.when(chainPool.createStatementRequestChain(any())).thenReturn(chain);
        doThrow(new RuntimeException("test")).when(chainPool).addToPool(chain);

        StatementRequestController controller = new StatementRequestController(log, chainPool, serviceController);
        ResponseBodyData result = controller.runStatementRequest(session, data);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(data).check();
        Mockito.verify(chainPool).createStatementRequestChain(any());
        Mockito.verify(chain).nextStep(data);
        Mockito.verify(chainPool).addToPool(chain);
        Mockito.verify(serviceController).getServerFailResponse(any(), any());
    }
}