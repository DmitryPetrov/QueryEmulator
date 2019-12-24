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

    @Test
    void add_getRequestStatusDto1_savedObject() throws Exception {
        GetRequestStatusDto dto = Mockito.mock(GetRequestStatusDto.class);
        List<StateResponseDto> stateResponseDtoList = Mockito.mock(List.class);
        StateResponseDto stateResponseDto = Mockito.mock(StateResponseDto.class);
        String state = "test";

        Mockito.when(dto.isNotProcessedYet()).thenReturn(false);
        Mockito.when(dto.getStateResponseList()).thenReturn(stateResponseDtoList);
        Mockito.when(stateResponseDtoList.get(0)).thenReturn(stateResponseDto);
        Mockito.when(stateResponseDto.getState()).thenReturn(state);

        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(dto);

        Assertions.assertEquals(dto, chainData.getGetRequestStatus1());
        Assertions.assertEquals(state, chainData.getStatementRequestStatus());
    }

    @Test
    void add_getRequestStatusDto1_notProcessedYetStatus() throws Exception {
        GetRequestStatusDto dto = Mockito.mock(GetRequestStatusDto.class);

        Mockito.when(dto.isNotProcessedYet()).thenReturn(true);

        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(dto);

        Assertions.assertEquals(dto, chainData.getGetRequestStatus1());
        Assertions.assertEquals("NOT PROCESSED YET", chainData.getStatementRequestStatus());
    }

    @Test
    void add_getRequestStatusDto2_savedObject() throws Exception {
        StatementRequestDto statementRequestDto = Mockito.mock(StatementRequestDto.class);
        GetRequestStatusDto dto1 = Mockito.mock(GetRequestStatusDto.class);
        GetRequestStatusDto dto2 = Mockito.mock(GetRequestStatusDto.class);
        StateResponseDto stateResponseDto = Mockito.mock(StateResponseDto.class);
        List<StateResponseDto> stateResponseDtoList = new ArrayList<StateResponseDto>(){{add(stateResponseDto);}};
        String state = "test";
        String externalId = "externalId test";

        Mockito.when(statementRequestDto.getExternalId()).thenReturn(externalId);
        Mockito.when(dto1.isNotProcessedYet()).thenReturn(true);
        Mockito.when(dto2.isNotProcessedYet()).thenReturn(false);
        Mockito.when(dto2.getStateResponseList()).thenReturn(stateResponseDtoList);
        Mockito.when(stateResponseDto.getState()).thenReturn(state);
        Mockito.when(stateResponseDto.getExtId()).thenReturn(externalId);

        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(statementRequestDto);
        chainData.add(dto1);
        chainData.add(dto2);

        Assertions.assertEquals(dto2, chainData.getGetRequestStatus2());
        Assertions.assertEquals(state, chainData.getIncomingStatus());
    }

    @Test
    void add_getRequestStatusDto2_notProcessedYetStatus() throws Exception {
        GetRequestStatusDto dto = Mockito.mock(GetRequestStatusDto.class);

        Mockito.when(dto.isNotProcessedYet()).thenReturn(true);

        StatementRequestChainData chainData = new StatementRequestChainData();
        chainData.add(dto);
        chainData.add(dto);

        Assertions.assertEquals(dto, chainData.getGetRequestStatus2());
        Assertions.assertEquals("NOT PROCESSED YET", chainData.getIncomingStatus());
    }
}