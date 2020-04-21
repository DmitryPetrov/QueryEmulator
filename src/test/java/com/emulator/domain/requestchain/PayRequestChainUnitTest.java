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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

class PayRequestChainUnitTest {

    private Logger log;
    private SoapClient soapClient;
    private AppUser user;
    private PayRequestChainData chainData;
    private PayRequestData payRequestData;
    private PayRequestDto payRequestDto;
    private IncomingData incomingData;
    private IncomingDto incomingDto;
    private GetRequestStatusDto getRequestStatusDto;

    @BeforeEach
    void before() {
        log = Mockito.mock(Logger.class);
        soapClient = Mockito.mock(SoapClient.class);
        user = Mockito.mock(AppUser.class);
        chainData = Mockito.mock(PayRequestChainData.class);
        payRequestData = Mockito.mock(PayRequestData.class);
        payRequestDto = Mockito.mock(PayRequestDto.class);
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
        PayRequestChain chain = new PayRequestChain(user, soapClient, log, 0, chainData);

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
                () -> new PayRequestChain(null, soapClient, log, 0, chainData),
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
                () -> new PayRequestChain(user, soapClient, log, 0, chainData),
                "user must be authorized"
        );
    }

    @Test
    void nextStep_validPayRequestData_payRequestDtoObject() throws Exception {
        //given
        int currentPhase = 0;
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, payRequestData)).thenReturn(payRequestDto);

        //when
        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep(payRequestData);

        //then
        Mockito.verify(soapClient).doRequest(user, payRequestData);
        Mockito.verify(chainData).add(payRequestDto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_validPayRequestData_exception() throws Exception {
        //given
        int currentPhase = 0;
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, payRequestData)).thenThrow(RuntimeException.class);

        //when
        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);

        //then
        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep(payRequestData));
        Mockito.verify(soapClient).doRequest(user, payRequestData);
        Assertions.assertNull(chain.getPayRequest());
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus1_getRequestStatusDtoObject() throws Exception {
        //given
        int currentPhase = 1;
        String payRequestResponseId = "test2";
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getPayRequestResponseId()).thenReturn(payRequestResponseId);
        Mockito.when(chainData.getPayRequestStatus()).thenReturn("DELIVERED");
        Mockito.when(soapClient.doRequest(user, payRequestResponseId)).thenReturn(getRequestStatusDto);

        //when
        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep();

        //then
        Mockito.verify(chainData).getPayRequestResponseId();
        Mockito.verify(soapClient).doRequest(user, payRequestResponseId);
        Mockito.verify(chainData).add(getRequestStatusDto);
        Assertions.assertEquals((currentPhase + 1), chain.getPhaseNum());
    }

    @Test
    void nextStep_runGetRequestStatus1_exception() throws Exception {
        //given
        int currentPhase = 1;
        String payRequestResponseId = "test2";
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(chainData.getPayRequestResponseId()).thenReturn(payRequestResponseId);
        Mockito.when(soapClient.doRequest(user, payRequestResponseId)).thenThrow(RuntimeException.class);

        //when
        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);

        //then
        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep());
        Mockito.verify(chainData).getPayRequestResponseId();
        Mockito.verify(soapClient).doRequest(user, payRequestResponseId);
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }

    @Test
    void nextStep_validIncomingData_payRequestDtoObject() throws Exception {
        //given
        int currentPhase = 2;
        Mockito.when(user.getSessionId()).thenReturn("test");
        Mockito.when(soapClient.doRequest(user, incomingData)).thenReturn(incomingDto);

        //when
        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);
        chain.nextStep(incomingData);

        //then
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
        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);

        //then
        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep(incomingData));
        Mockito.verify(soapClient).doRequest(user, incomingData);
        Assertions.assertNull(chain.getPayRequest());
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
        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);
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
        PayRequestChain chain = new PayRequestChain(user, soapClient, log, currentPhase, chainData);

        //then
        Assertions.assertThrows(RuntimeException.class, () -> chain.nextStep());
        Mockito.verify(chainData).getIncomingResponseId();
        Mockito.verify(soapClient).doRequest(user, incomingResponseId);
        Assertions.assertEquals(currentPhase, chain.getPhaseNum());
    }
}