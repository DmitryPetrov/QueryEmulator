package com.emulator.domain.requestchain;

import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.requests.RequestParameters;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.payrequest.PayRequestData;
import com.emulator.domain.soap.requests.payrequest.dto.PayRequestDto;
import com.emulator.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayRequestChain implements RequestChain {

    private static Logger log;
    private static final String REQUEST_STATUS_DELIVERED = "DELIVERED";
    private static final String REQUEST_STATUS_ACCEPTED = "ACCEPTED";
    private static final String TYPE = "PAY_REQUEST_CHAIN";

    /*
        START_PHASE = нет успешно отправленных запросов
        PAY_REQUEST_PHASE = успешно отправленный запрос PayRequest, получен payRequestResponseId
        GET_REQUEST_STATUS_1_PHASE = успешно отправленный запрос GetRequestStatus, получен положительный ответ 'DELIVERED'
        INCOMING_PHASE = успешно отправленный запрос Incoming, получен payRequestResponseId
        GET_REQUEST_STATUS_2_PHASE = успешно отправленный запрос GetRequestStatus, получен запрашиваемый документ
        DOCUMENT_PHASE = цепочка запросов отработала;
    */
    private static final int START_PHASE = 0;
    private static final int PAY_REQUEST_PHASE = 1;
    private static final int GET_REQUEST_STATUS_1_PHASE = 2;
    private static final int INCOMING_PHASE = 3;
    private static final int GET_REQUEST_STATUS_2_PHASE = 4;
    private static final int DOCUMENT_PHASE = 5;

    private final AppUser user;
    private final SoapClient soapClient;

    private int phase = START_PHASE;
    private List<String> phaseList;

    private PayRequestChainData chainData;

    {
        phaseList = new ArrayList<String>() {{
            add("START_PHASE");
            add("PAY_REQUEST_PHASE");
            add("GET_REQUEST_STATUS_1_PHASE");
            add("INCOMING_PHASE");
            add("GET_REQUEST_STATUS_2_PHASE");
            add("DOCUMENT_PHASE");
        }};
    }

    /*
        Constructor for tests
    */
    public PayRequestChain(AppUser user, SoapClient soapClient, Logger log, int currentPhase,
                           PayRequestChainData chainData) {
        this.user = userCheck(user);
        this.soapClient = soapClient;
        this.log = log;
        this.chainData = chainData;
        this.phase = currentPhase;
    }

    public PayRequestChain(AppUser user, SoapClient soapClient) {
        this.user = userCheck(user);
        this.soapClient = soapClient;
        this.log = LoggerFactory.getLogger(PayRequestChain.class);
        this.chainData = new PayRequestChainData();
    }

    private AppUser userCheck(AppUser user) {
        if (user == null) {
            throw new ParameterIsNullException("AppUser must not be 'null'");
        }
        if (user.getSessionId().equals("")) {
            throw new UserIsNotAuthorizedException("AppUser user must be authorized");
        }
        return user;
    }

    @Override
    public void nextStep(RequestParameters data) {
        checkNextPhase(PAY_REQUEST_PHASE);
        log.info("Run PayRequest for RequestChain(payRequestResponseId=" + getResponseId() + ")");
        PayRequestDto result = soapClient.doRequest(user, (PayRequestData) data);
        chainData.add(result);
        setPhase(PAY_REQUEST_PHASE);
    }

    @Override
    public void nextStep() throws IOException, SAXException {
        if (phase == PAY_REQUEST_PHASE) {
            runGetRequestStatus1();
        } else if (phase == INCOMING_PHASE) {
            runGetRequestStatus2();
        } else {
            throw new RequestChainPhaseNotReadyOrAlreadyPassedException("Phase not ready or already passed.");
        }
    }

    private void runGetRequestStatus1() throws IOException, SAXException {
        checkNextPhase(GET_REQUEST_STATUS_1_PHASE);
        log.info("Run GetRequestStatus for RequestChain(payRequestResponseId=" + getResponseId() + ")");
        GetRequestStatusDto result = soapClient.doRequest(user, chainData.getPayRequestResponseId());
        chainData.add(result);
        checkPhase(chainData.getPayRequestStatus(), GET_REQUEST_STATUS_1_PHASE);
    }

    private void runGetRequestStatus2() throws IOException, SAXException {
        checkNextPhase(GET_REQUEST_STATUS_2_PHASE);
        log.info("Run GetRequestStatus for RequestChain(incomingResponseId=" + getResponseId() + ")");
        GetRequestStatusDto result = soapClient.doRequest(user, chainData.getIncomingResponseId());
        chainData.add(result);
        checkPhase(chainData.getIncomingStatus(), GET_REQUEST_STATUS_2_PHASE);
    }

    @Override
    public void nextStep(IncomingData data) {
        checkNextPhase(INCOMING_PHASE);
        log.info("Run Incoming for RequestChain(responseId=" + getResponseId() + ")");
        IncomingDto result = soapClient.doRequest(user, data);
        chainData.add(result);
        setPhase(INCOMING_PHASE);
    }

    private void checkNextPhase(int newPhase) {
        if (phase > (newPhase - 1)) {
            throw new RequestChainPhaseAlreadyPassedException(newPhase + " phase already passed. Current phase: " +
                    this.phase);
        }
        if (phase < (newPhase - 1)) {
            throw new RequestChainPhaseNotReadyException(newPhase + " phase not ready. Current phase: " + this.phase);
        }
    }

    private void checkPhase(String status, int newPhase) {
        if ((status.equals(REQUEST_STATUS_DELIVERED) ||
                (status.equals(REQUEST_STATUS_ACCEPTED)))) {
            setPhase(newPhase);
        }
    }

    private void setPhase(int newPhase) {
        this.phase = newPhase;
    }

    @Override
    public AppUser getUser() {
        return user;
    }

    @Override
    public String getPhase() {
        return phaseList.get(phase);
    }

    @Override
    public int getPhaseNum() {
        return phase;
    }

    @Override
    public String getRequestId() {
        return chainData.getRequestId();
    }

    @Override
    public String getResponseId() {
        return chainData.getResponseId();
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public String getPayRequestStatus() {
        return chainData.getPayRequestStatus();
    }

    public String getIncomingStatus() {
        return chainData.getIncomingStatus();
    }

    public PayRequestDto getPayRequest() {
        return chainData.getPayRequest();
    }

    public GetRequestStatusDto getGetRequestStatus1() {
        return chainData.getGetRequestStatus1();
    }

    public IncomingDto getIncoming() {
        return chainData.getIncoming();
    }

    public GetRequestStatusDto getGetRequestStatus2() {
        return chainData.getGetRequestStatus2();
    }

}
