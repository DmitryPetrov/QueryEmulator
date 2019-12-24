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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

class StatementRequestChainUnitTest {

    @Test
    void constructor_validData_validObject() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        StatementRequestChainData chainData = Mockito.mock(StatementRequestChainData.class);
        String sessionId = "test";

        Mockito.when(user.getSessionId()).thenReturn(sessionId);

        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, 0, chainData);

        Assertions.assertNotNull(chain);
        Assertions.assertNotNull(chain.getUser());
        Assertions.assertEquals(user, chain.getUser());
        Assertions.assertEquals(sessionId, chain.getUser().getSessionId());
    }

    @Test
    void constructor_nullAppUser_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        StatementRequestChainData chainData = Mockito.mock(StatementRequestChainData.class);

        Assertions.assertThrows(
                ParameterIsNullException.class,
                () -> new StatementRequestChain(null, soapClient, log, 0, chainData),
                "must not be 'null'"
        );
    }

    @Test
    void constructor_notAuthorizedAppUser_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        StatementRequestChainData chainData = Mockito.mock(StatementRequestChainData.class);
        String sessionId = "";

        Mockito.when(user.getSessionId()).thenReturn(sessionId);

        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new StatementRequestChain(user, soapClient, log, 0, chainData),
                "user must be authorized"
        );
    }

    @Test
    void nextStep_validStatementRequestData_statementRequestDtoObject() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        StatementRequestChainData chainData = Mockito.mock(StatementRequestChainData.class);
        StatementRequestData data = Mockito.mock(StatementRequestData.class);
        StatementRequestDto dto = Mockito.mock(StatementRequestDto.class);
        int currentPhase = 0;

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, data)).thenReturn(dto);

        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep(data);

        Mockito.verify(soapClient).doRequest(user, data);
        Mockito.verify(chainData).add(dto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_validStatementRequestData_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        StatementRequestChainData chainData = Mockito.mock(StatementRequestChainData.class);
        StatementRequestData data = Mockito.mock(StatementRequestData.class);
        int currentPhase = 0;

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, data)).thenThrow(RuntimeException.class);

        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);

        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep(data));
        Mockito.verify(soapClient).doRequest(user, data);
        Assertions.assertNull(chain.getStatementRequest());
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus1_getRequestStatusDtoObject() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        StatementRequestChainData chainData = Mockito.mock(StatementRequestChainData.class);
        GetRequestStatusDto dto = Mockito.mock(GetRequestStatusDto.class);
        int currentPhase = 1;
        String statementRequestResponseId = "test2";

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getStatementRequestResponseId()).thenReturn(statementRequestResponseId);
        Mockito.when(chainData.getStatementRequestStatus()).thenReturn("DELIVERED");
        Mockito.when(soapClient.doRequest(user, statementRequestResponseId)).thenReturn(dto);

        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep();

        Mockito.verify(chainData).getStatementRequestResponseId();
        Mockito.verify(soapClient).doRequest(user, statementRequestResponseId);
        Mockito.verify(chainData).add(dto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus1_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        StatementRequestChainData chainData = Mockito.mock(StatementRequestChainData.class);
        int currentPhase = 1;
        String statementRequestResponseId = "test2";

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getStatementRequestResponseId()).thenReturn(statementRequestResponseId);
        Mockito.when(soapClient.doRequest(user, statementRequestResponseId)).thenThrow(RuntimeException.class);

        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);

        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep());
        Mockito.verify(chainData).getStatementRequestResponseId();
        Mockito.verify(soapClient).doRequest(user, statementRequestResponseId);
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }

    @Test
    void nextStep_validIncomingData_statementRequestDtoObject() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        StatementRequestChainData chainData = Mockito.mock(StatementRequestChainData.class);
        IncomingData data = Mockito.mock(IncomingData.class);
        IncomingDto dto = Mockito.mock(IncomingDto.class);
        int currentPhase = 2;

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, data)).thenReturn(dto);

        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep(data);

        Mockito.verify(soapClient).doRequest(user, data);
        Mockito.verify(chainData).add(dto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_validIncomingData_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        StatementRequestChainData chainData = Mockito.mock(StatementRequestChainData.class);
        IncomingData data = Mockito.mock(IncomingData.class);
        int currentPhase = 2;

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, data)).thenThrow(RuntimeException.class);

        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);

        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep(data));
        Mockito.verify(soapClient).doRequest(user, data);
        Assertions.assertNull(chain.getStatementRequest());
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus2_getRequestStatusDtoObject() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        StatementRequestChainData chainData = Mockito.mock(StatementRequestChainData.class);
        GetRequestStatusDto dto = Mockito.mock(GetRequestStatusDto.class);
        int currentPhase = 3;
        String incomingResponseId = "test2";

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getIncomingResponseId()).thenReturn(incomingResponseId);
        Mockito.when(chainData.getIncomingStatus()).thenReturn("DELIVERED");
        Mockito.when(soapClient.doRequest(user, incomingResponseId)).thenReturn(dto);

        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep();

        Mockito.verify(chainData).getIncomingResponseId();
        Mockito.verify(soapClient).doRequest(user, incomingResponseId);
        Mockito.verify(chainData).add(dto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus2_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        StatementRequestChainData chainData = Mockito.mock(StatementRequestChainData.class);
        int currentPhase = 3;
        String incomingResponseId = "test2";

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getIncomingResponseId()).thenReturn(incomingResponseId);
        Mockito.when(soapClient.doRequest(user, incomingResponseId)).thenThrow(RuntimeException.class);

        StatementRequestChain chain = new StatementRequestChain(user, soapClient, log, currentPhase, chainData);

        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep());
        Mockito.verify(chainData).getIncomingResponseId();
        Mockito.verify(soapClient).doRequest(user, incomingResponseId);
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }
}