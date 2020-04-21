package com.emulator.domain.requestchain;

import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.statementrequest.StatementRequestData;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import com.emulator.exception.ParameterIsNullException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

class StatementRequestChainUnitTest {

    private Logger log;
    private SoapClient soapClient;
    private AppUser user;
    private StatementRequestChainData chainData;
    private StatementRequestData statementRequestData;
    private StatementRequestDto statementRequestDto;
    private IncomingData incomingData;
    private IncomingDto incomingDto;
    private GetRequestStatusDto getRequestStatusDto;

    @BeforeEach
    void before() {
        log = Mockito.mock(Logger.class);
        soapClient = Mockito.mock(SoapClient.class);
        user = Mockito.mock(AppUser.class);
        chainData = Mockito.mock(StatementRequestChainData.class);
        statementRequestData = Mockito.mock(StatementRequestData.class);
        statementRequestDto = Mockito.mock(StatementRequestDto.class);
        incomingData = Mockito.mock(IncomingData.class);
        incomingDto = Mockito.mock(IncomingDto.class);
        getRequestStatusDto = Mockito.mock(GetRequestStatusDto.class);
    }

    @Test
    void constructor_validData_validObject() throws Exception {
        //given
        String sessionId = "test";
        Mockito.when(user.getSessionId()).thenReturn(sessionId);

        //when
        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, 0, chainData);

        //then
        Assertions.assertNotNull(chain);
        Assertions.assertNotNull(chain.getUser());
        Assertions.assertEquals(user, chain.getUser());
        Assertions.assertEquals(sessionId, chain.getUser().getSessionId());
    }

    @Test
    void constructor_nullAppUser_exception() throws Exception {
        //then
        Assertions.assertThrows(
                ParameterIsNullException.class,
                () -> new StatementRequestChain(null, soapClient, log, 0, chainData),
                "must not be 'null'"
        );
    }

    @Test
    void constructor_notAuthorizedAppUser_exception() throws Exception {
        //given
        String sessionId = "";
        Mockito.when(user.getSessionId()).thenReturn(sessionId);

        //then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new StatementRequestChain(user, soapClient, log, 0, chainData),
                "user must be authorized"
        );
    }

    @Test
    void nextStep_validStatementRequestData_statementRequestDtoObject() throws Exception {
        //given
        int currentPhase = 0;
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, statementRequestData)).thenReturn(statementRequestDto);

        //when
        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep(statementRequestData);

        //then
        Mockito.verify(soapClient).doRequest(user, statementRequestData);
        Mockito.verify(chainData).add(statementRequestDto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_validStatementRequestData_exception() throws Exception {
        //given
        int currentPhase = 0;
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, statementRequestData)).thenThrow(RuntimeException.class);

        //when
        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);

        //then
        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep(statementRequestData));
        Mockito.verify(soapClient).doRequest(user, statementRequestData);
        Assertions.assertNull(chain.getStatementRequest());
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus1_getRequestStatusDtoObject() throws Exception {
        //given
        int currentPhase = 1;
        String statementRequestResponseId = "test2";
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getStatementRequestResponseId()).thenReturn(statementRequestResponseId);
        Mockito.when(chainData.getStatementRequestStatus()).thenReturn("DELIVERED");
        Mockito.when(soapClient.doRequest(user, statementRequestResponseId)).thenReturn(getRequestStatusDto);

        //when
        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep();

        //then
        Mockito.verify(chainData).getStatementRequestResponseId();
        Mockito.verify(soapClient).doRequest(user, statementRequestResponseId);
        Mockito.verify(chainData).add(getRequestStatusDto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus1_exception() throws Exception {
        //given
        int currentPhase = 1;
        String statementRequestResponseId = "test2";
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getStatementRequestResponseId()).thenReturn(statementRequestResponseId);
        Mockito.when(soapClient.doRequest(user, statementRequestResponseId)).thenThrow(RuntimeException.class);

        //when
        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);

        //then
        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep());
        Mockito.verify(chainData).getStatementRequestResponseId();
        Mockito.verify(soapClient).doRequest(user, statementRequestResponseId);
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }

    @Test
    void nextStep_validIncomingData_statementRequestDtoObject() throws Exception {
        //given
        int currentPhase = 2;
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, incomingData)).thenReturn(incomingDto);

        //when
        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep(incomingData);

        //when
        Mockito.verify(soapClient).doRequest(user, incomingData);
        Mockito.verify(chainData).add(incomingDto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_validIncomingData_exception() throws Exception {
        //given
        int currentPhase = 2;
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, incomingData)).thenThrow(RuntimeException.class);

        //when
        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);

        //then
        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep(incomingData));
        Mockito.verify(soapClient).doRequest(user, incomingData);
        Assertions.assertNull(chain.getStatementRequest());
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus2_getRequestStatusDtoObject() throws Exception {
        //given
        int currentPhase = 3;
        String incomingResponseId = "test2";
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getIncomingResponseId()).thenReturn(incomingResponseId);
        Mockito.when(chainData.getIncomingStatus()).thenReturn("DELIVERED");
        Mockito.when(soapClient.doRequest(user, incomingResponseId)).thenReturn(getRequestStatusDto);

        //when
        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep();

        //then
        Mockito.verify(chainData).getIncomingResponseId();
        Mockito.verify(soapClient).doRequest(user, incomingResponseId);
        Mockito.verify(chainData).add(getRequestStatusDto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus2_exception() throws Exception {
        //given
        int currentPhase = 3;
        String incomingResponseId = "test2";
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getIncomingResponseId()).thenReturn(incomingResponseId);
        Mockito.when(soapClient.doRequest(user, incomingResponseId)).thenThrow(RuntimeException.class);

        //when
        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);

        //then
        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep());
        Mockito.verify(chainData).getIncomingResponseId();
        Mockito.verify(soapClient).doRequest(user, incomingResponseId);
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }
}