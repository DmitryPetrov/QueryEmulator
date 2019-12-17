package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class RequestListControllerUnitTest {

    @Test
    void getRequestChainList_validData_callGetSuccessResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        List<RequestChain> chainList = Mockito.mock(List.class);

        Mockito.when(chainPool.getChainList(any())).thenReturn(chainList);

        RequestListController controller = new RequestListController(log, chainPool, serviceController);
        ResponseBodyData result = controller.getRequestChainList(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(chainPool).getChainList(any());
        Mockito.verify(serviceController).getSuccessResponse(chainList);
    }

    @Test
    void getRequestChainList_sessionHaveNotUser_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(serviceController.getUser(session)).thenThrow(UserIsNotAuthorizedException.class);

        RequestListController controller = new RequestListController(log, chainPool, serviceController);
        ResponseBodyData result = controller.getRequestChainList(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void getRequestChainList_serverError_callGetErrorResponse() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        RequestChainPool chainPool = Mockito.mock(RequestChainPool.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(chainPool.getChainList(any())).thenThrow(RuntimeException.class);

        RequestListController controller = new RequestListController(log, chainPool, serviceController);
        ResponseBodyData result = controller.getRequestChainList(session);

        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(chainPool).getChainList(any());
        Mockito.verify(serviceController).getServerFailResponse(any());
    }
}