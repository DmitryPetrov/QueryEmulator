package com.emulator.controller;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.requestchain.RequestChain;
import com.emulator.domain.requestchain.RequestChainPool;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class RequestListControllerUnitTest {

    private Logger log;
    private RequestChainPool chainPool;
    private ServiceController serviceController;
    private HttpSession session;
    private List<RequestChain> chainList;

    @BeforeEach
    void before() {
        log = Mockito.mock(Logger.class);
        chainPool = Mockito.mock(RequestChainPool.class);
        serviceController = Mockito.mock(ServiceController.class);
        session = Mockito.mock(HttpSession.class);
        chainList = Mockito.mock(List.class);
    }

    @Test
    void getRequestChainList_validData_callGetSuccessResponse() throws Exception {
        //given
        Mockito.when(chainPool.getChainList(any())).thenReturn(chainList);

        //when
        RequestListController controller = new RequestListController(log, chainPool, serviceController);
        ResponseBodyData result = controller.getRequestChainList(session);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(chainPool).getChainList(any());
        Mockito.verify(serviceController).getSuccessResponse(chainList);
    }

    @Test
    void getRequestChainList_sessionHaveNotUser_callGetErrorResponse() throws Exception {
        //given
        Mockito.when(serviceController.getUser(session)).thenThrow(UserIsNotAuthorizedException.class);

        //when
        RequestListController controller = new RequestListController(log, chainPool, serviceController);
        ResponseBodyData result = controller.getRequestChainList(session);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void getRequestChainList_serverError_callGetErrorResponse() throws Exception {
        //given
        Mockito.when(chainPool.getChainList(any())).thenThrow(RuntimeException.class);

        //when
        RequestListController controller = new RequestListController(log, chainPool, serviceController);
        ResponseBodyData result = controller.getRequestChainList(session);

        //then
        Assertions.assertNull(result);
        Mockito.verify(serviceController).getUser(session);
        Mockito.verify(chainPool).getChainList(any());
        Mockito.verify(serviceController).getServerFailResponse(any());
    }
}