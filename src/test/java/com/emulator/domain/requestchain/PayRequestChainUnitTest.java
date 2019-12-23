package com.emulator.domain.requestchain;

import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.payrequest.PayRequestData;
import com.emulator.domain.soap.requests.payrequest.dto.PayRequestDto;
import com.emulator.exception.ParameterIsNullException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

class PayRequestChainUnitTest {

    @Test
    void constructor_validData_validObject() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        PayRequestChainData chainData = Mockito.mock(PayRequestChainData.class);
        String sessionId = "test";

        Mockito.when(user.getSessionId()).thenReturn(sessionId);

        PayRequestChain chain = new PayRequestChain(user, soapClient, log, 0, chainData);

        Assertions.assertNotNull(chain);
        Assertions.assertNotNull(chain.getUser());
        Assertions.assertEquals(user, chain.getUser());
        Assertions.assertEquals(sessionId, chain.getUser().getSessionId());
    }

    @Test
    void constructor_nullAppUser_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        PayRequestChainData chainData = Mockito.mock(PayRequestChainData.class);

        Assertions.assertThrows(
                ParameterIsNullException.class,
                () -> new PayRequestChain(null, soapClient, log, 0, chainData),
                "must not be 'null'"
        );
    }

    @Test
    void constructor_notAuthorizedAppUser_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        PayRequestChainData chainData = Mockito.mock(PayRequestChainData.class);
        String sessionId = "";

        Mockito.when(user.getSessionId()).thenReturn(sessionId);

        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new PayRequestChain(user, soapClient, log, 0, chainData),
                "user must be authorized"
        );
    }

    @Test
    void nextStep_validPayRequestData_payRequestDtoObject() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        PayRequestChainData chainData = Mockito.mock(PayRequestChainData.class);
        PayRequestData data = Mockito.mock(PayRequestData.class);
        PayRequestDto dto = Mockito.mock(PayRequestDto.class);
        int currentPhase = 0;

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, data)).thenReturn(dto);

        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep(data);

        Mockito.verify(soapClient).doRequest(user, data);
        Mockito.verify(chainData).add(dto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_validPayRequestData_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        PayRequestChainData chainData = Mockito.mock(PayRequestChainData.class);
        PayRequestData data = Mockito.mock(PayRequestData.class);
        int currentPhase = 0;

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, data)).thenThrow(RuntimeException.class);

        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);

        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep(data));
        Mockito.verify(soapClient).doRequest(user, data);
        Assertions.assertNull(chain.getPayRequest());
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus1_getRequestStatusDtoObject() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        PayRequestChainData chainData = Mockito.mock(PayRequestChainData.class);
        GetRequestStatusDto dto = Mockito.mock(GetRequestStatusDto.class);
        int currentPhase = 1;
        String payRequestResponseId = "test2";

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getPayRequestResponseId()).thenReturn(payRequestResponseId);
        Mockito.when(chainData.getPayRequestStatus()).thenReturn("DELIVERED");
        Mockito.when(soapClient.doRequest(user, payRequestResponseId)).thenReturn(dto);

        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep();

        Mockito.verify(chainData).getPayRequestResponseId();
        Mockito.verify(soapClient).doRequest(user, payRequestResponseId);
        Mockito.verify(chainData).add(dto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus1_exception() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        PayRequestChainData chainData = Mockito.mock(PayRequestChainData.class);
        int currentPhase = 1;
        String payRequestResponseId = "test2";

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getPayRequestResponseId()).thenReturn(payRequestResponseId);
        Mockito.when(soapClient.doRequest(user, payRequestResponseId)).thenThrow(RuntimeException.class);

        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);

        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep());
        Mockito.verify(chainData).getPayRequestResponseId();
        Mockito.verify(soapClient).doRequest(user, payRequestResponseId);
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }

    @Test
    void nextStep_validIncomingData_payRequestDtoObject() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        PayRequestChainData chainData = Mockito.mock(PayRequestChainData.class);
        IncomingData data = Mockito.mock(IncomingData.class);
        IncomingDto dto = Mockito.mock(IncomingDto.class);
        int currentPhase = 2;

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, data)).thenReturn(dto);

        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);
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
        PayRequestChainData chainData = Mockito.mock(PayRequestChainData.class);
        IncomingData data = Mockito.mock(IncomingData.class);
        int currentPhase = 2;

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, data)).thenThrow(RuntimeException.class);

        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);

        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep(data));
        Mockito.verify(soapClient).doRequest(user, data);
        Assertions.assertNull(chain.getPayRequest());
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus2_getRequestStatusDtoObject() throws Exception {
        Logger log = Mockito.mock(Logger.class);
        SoapClient soapClient = Mockito.mock(SoapClient.class);
        AppUser user = Mockito.mock(AppUser.class);
        PayRequestChainData chainData = Mockito.mock(PayRequestChainData.class);
        GetRequestStatusDto dto = Mockito.mock(GetRequestStatusDto.class);
        int currentPhase = 3;
        String incomingResponseId = "test2";

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getIncomingResponseId()).thenReturn(incomingResponseId);
        Mockito.when(chainData.getIncomingStatus()).thenReturn("DELIVERED");
        Mockito.when(soapClient.doRequest(user, incomingResponseId)).thenReturn(dto);

        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);
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
        PayRequestChainData chainData = Mockito.mock(PayRequestChainData.class);
        int currentPhase = 3;
        String incomingResponseId = "test2";

        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getIncomingResponseId()).thenReturn(incomingResponseId);
        Mockito.when(soapClient.doRequest(user, incomingResponseId)).thenThrow(RuntimeException.class);

        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);

        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep());
        Mockito.verify(chainData).getIncomingResponseId();
        Mockito.verify(soapClient).doRequest(user, incomingResponseId);
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }
}