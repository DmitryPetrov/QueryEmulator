package com.emulator.domain.requestchain;

import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.getrequeststatus.dto.stateresponse.StateResponseDto;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class StatementRequestChainDataUnitTest {

    private StatementRequestDto statementRequestDto;
    private IncomingDto incomingDto;
    private StatementRequestDto payRequestDto;

    @BeforeEach
    void before() {
        statementRequestDto = Mockito.mock(StatementRequestDto.class);
        incomingDto = Mockito.mock(IncomingDto.class);
        payRequestDto = Mockito.mock(StatementRequestDto.class);
    }

    @Test
    void add_statementRequestDto_savedObject() throws Exception {
        //given
        String requestId = "test";
        String responseId = "test2";
        Mockito.when(statementRequestDto.getRequestId()).thenReturn(requestId);
        Mockito.when(statementRequestDto.getResponseId()).thenReturn(responseId);

        //when
        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(statementRequestDto);

        //then
        Assertions.assertEquals(statementRequestDto, chainData.getStatementRequest());
        Assertions.assertEquals(requestId, chainData.getRequestId());
        Assertions.assertEquals(requestId, chainData.getStatementRequestRequestId());
        Assertions.assertEquals(responseId, chainData.getResponseId());
        Assertions.assertEquals(responseId, chainData.getStatementRequestResponseId());
    }

    @Test
    void add_incomingDto_savedObject() throws Exception {
        //given
        String requestId = "test";
        String responseId = "test2";
        Mockito.when(incomingDto.getRequestId()).thenReturn(requestId);
        Mockito.when(incomingDto.getResponseId()).thenReturn(responseId);

        //when
        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(incomingDto);

        //then
        Assertions.assertEquals(incomingDto, chainData.getIncoming());
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
        //given
        GetRequestStatusDto dto = getMockGetRequestStatusDto();

        //when
        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(dto);

        //then
        Assertions.assertEquals(dto, chainData.getGetRequestStatus1());
        Assertions.assertEquals(STATE, chainData.getStatementRequestStatus());
    }

    @Test
    void add_getRequestStatusDto1_notProcessedYetStatus() throws Exception {
        //given
        GetRequestStatusDto dto = getMockGetRequestStatusDtoNotProcessedYet();

        //when
        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(dto);

        //then
        Assertions.assertEquals(dto, chainData.getGetRequestStatus1());
        Assertions.assertEquals("NOT PROCESSED YET", chainData.getStatementRequestStatus());
    }

    @Test
    void add_getReqStatDto1ThenGetReqStatDto2_savedGetReqStatDto2() throws Exception {
        //given
        GetRequestStatusDto dto1 = getMockGetRequestStatusDto();
        GetRequestStatusDto dto2 = getMockGetRequestStatusDto();
        Mockito.when(payRequestDto.getExternalId()).thenReturn(EXT_ID);

        //when
        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(payRequestDto);
        chainData.add(dto1);
        chainData.add(dto2);

        //then
        Assertions.assertEquals(dto2, chainData.getGetRequestStatus2());
        Assertions.assertEquals(STATE, chainData.getIncomingStatus());
    }

    @Test
    void add_getReqStatDto1ThenGetReqStatDto2_notProcessedYetStatus() throws Exception {
        //given
        GetRequestStatusDto dto1 = getMockGetRequestStatusDto();
        GetRequestStatusDto dto2 = getMockGetRequestStatusDtoNotProcessedYet();

        //when
        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(dto1);
        chainData.add(dto2);

        //then
        Assertions.assertEquals(dto1, chainData.getGetRequestStatus1());
        Assertions.assertEquals(dto2, chainData.getGetRequestStatus2());
        Assertions.assertEquals("NOT PROCESSED YET", chainData.getIncomingStatus());
    }

    @Test
    void add_getReqStatDto1NotProcessedYetThenGetReqStatDto2_savedGetReqStatDto1() throws Exception {
        //given
        GetRequestStatusDto dto1 = getMockGetRequestStatusDtoNotProcessedYet();
        GetRequestStatusDto dto2 = getMockGetRequestStatusDto();
        Mockito.when(payRequestDto.getExternalId()).thenReturn(EXT_ID);

        //when
        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(payRequestDto);
        chainData.add(dto1);
        chainData.add(dto2);

        //then
        Assertions.assertEquals(dto2, chainData.getGetRequestStatus1());
        Assertions.assertEquals(STATE, chainData.getStatementRequestStatus());
        Assertions.assertNull(chainData.getGetRequestStatus2());
        Assertions.assertNull(chainData.getIncomingStatus());
    }
}