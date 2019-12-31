package com.emulator.domain.requestchain;

import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.getrequeststatus.dto.stateresponse.StateResponseDto;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class StatementRequestChainDataUnitTest {

    @Test
    void add_statementRequestDto_savedObject() throws Exception {
        StatementRequestDto dto = Mockito.mock(StatementRequestDto.class);
        String requestId = "test";
        String responseId = "test2";

        Mockito.when(dto.getRequestId()).thenReturn(requestId);
        Mockito.when(dto.getResponseId()).thenReturn(responseId);

        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(dto);

        Assertions.assertEquals(dto, chainData.getStatementRequest());
        Assertions.assertEquals(requestId, chainData.getRequestId());
        Assertions.assertEquals(requestId, chainData.getStatementRequestRequestId());
        Assertions.assertEquals(responseId, chainData.getResponseId());
        Assertions.assertEquals(responseId, chainData.getStatementRequestResponseId());
    }

    @Test
    void add_incomingDto_savedObject() throws Exception {
        IncomingDto dto = Mockito.mock(IncomingDto.class);
        String requestId = "test";
        String responseId = "test2";

        Mockito.when(dto.getRequestId()).thenReturn(requestId);
        Mockito.when(dto.getResponseId()).thenReturn(responseId);

        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(dto);

        Assertions.assertEquals(dto, chainData.getIncoming());
        Assertions.assertEquals(requestId, chainData.getIncomingRequestId());
        Assertions.assertEquals(responseId, chainData.getIncomingResponseId());
    }

    private static final String STATE = "state";
    private static final String EXT_ID = "ext id";

    /*
    Не использовал Mock для листа т.к. для получения incomingStatus используется StreamAPI,
    коротый не работает с моками
     */
    private GetRequestStatusDto getMockGetRequestStatusDto() {
        StateResponseDto stateResponseDto = Mockito.mock(StateResponseDto.class);
        List<StateResponseDto> stateResponseDtoList = new ArrayList<StateResponseDto>(){{add(stateResponseDto);}};
        GetRequestStatusDto dto = Mockito.mock(GetRequestStatusDto.class);

        Mockito.when(dto.isNotProcessedYet()).thenReturn(false);
        Mockito.when(dto.getStateResponseList()).thenReturn(stateResponseDtoList);
        Mockito.when(stateResponseDto.getState()).thenReturn(STATE);
        Mockito.when(stateResponseDto.getExtId()).thenReturn(EXT_ID);

        return dto;
    }

    private GetRequestStatusDto getMockGetRequestStatusDtoNotProcessedYet() {
        GetRequestStatusDto dto = Mockito.mock(GetRequestStatusDto.class);
        Mockito.when(dto.isNotProcessedYet()).thenReturn(true);
        return dto;
    }

    @Test
    void add_getRequestStatusDto1_savedObject() throws Exception {
        GetRequestStatusDto dto = getMockGetRequestStatusDto();

        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(dto);

        Assertions.assertEquals(dto, chainData.getGetRequestStatus1());
        Assertions.assertEquals(STATE, chainData.getStatementRequestStatus());
    }

    @Test
    void add_getRequestStatusDto1_notProcessedYetStatus() throws Exception {
        GetRequestStatusDto dto = getMockGetRequestStatusDtoNotProcessedYet();

        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(dto);

        Assertions.assertEquals(dto, chainData.getGetRequestStatus1());
        Assertions.assertEquals("NOT PROCESSED YET", chainData.getStatementRequestStatus());
    }

    @Test
    void add_getReqStatDto1ThenGetReqStatDto2_savedGetReqStatDto2() throws Exception {
        StatementRequestDto payRequestDto = Mockito.mock(StatementRequestDto.class);
        GetRequestStatusDto dto1 = getMockGetRequestStatusDto();
        GetRequestStatusDto dto2 = getMockGetRequestStatusDto();

        Mockito.when(payRequestDto.getExternalId()).thenReturn(EXT_ID);

        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(payRequestDto);
        chainData.add(dto1);
        chainData.add(dto2);

        Assertions.assertEquals(dto2, chainData.getGetRequestStatus2());
        Assertions.assertEquals(STATE, chainData.getIncomingStatus());
    }

    @Test
    void add_getReqStatDto1ThenGetReqStatDto2_notProcessedYetStatus() throws Exception {
        GetRequestStatusDto dto1 = getMockGetRequestStatusDto();
        GetRequestStatusDto dto2 = getMockGetRequestStatusDtoNotProcessedYet();

        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(dto1);
        chainData.add(dto2);

        Assertions.assertEquals(dto1, chainData.getGetRequestStatus1());
        Assertions.assertEquals(dto2, chainData.getGetRequestStatus2());
        Assertions.assertEquals("NOT PROCESSED YET", chainData.getIncomingStatus());
    }

    @Test
    void add_getReqStatDto1NotProcessedYetThenGetReqStatDto2_savedGetReqStatDto1() throws Exception {
        StatementRequestDto payRequestDto = Mockito.mock(StatementRequestDto.class);
        GetRequestStatusDto dto1 = getMockGetRequestStatusDtoNotProcessedYet();
        GetRequestStatusDto dto2 = getMockGetRequestStatusDto();

        Mockito.when(payRequestDto.getExternalId()).thenReturn(EXT_ID);

        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(payRequestDto);
        chainData.add(dto1);
        chainData.add(dto2);

        Assertions.assertEquals(dto2, chainData.getGetRequestStatus1());
        Assertions.assertEquals(STATE, chainData.getStatementRequestStatus());
        Assertions.assertNull(chainData.getGetRequestStatus2());
        Assertions.assertNull(chainData.getIncomingStatus());
    }
}