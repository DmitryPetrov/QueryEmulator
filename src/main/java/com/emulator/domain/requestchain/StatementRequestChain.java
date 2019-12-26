package com.emulator.domain.requestchain;

import com.emulator.domain.soap.requests.RequestParameters;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.statementrequest.StatementRequestData;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import com.emulator.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatementRequestChain implements RequestChain {

    private static Logger log;
    private static final String REQUEST_STATUS_DELIVERED = "DELIVERED";
    private static final String REQUEST_STATUS_ACCEPTED = "ACCEPTED";
    private static final String REQUEST_STATUS_NOT_PROCESSED_YET = "NOT PROCESSED YET";
    private static final String TYPE = "STATEMENT_REQUEST_CHAIN";


    /*
        START_PHASE = нет успешно отправленных запросов
        STATEMENT_REQUEST_PHASE = успешно отправленный запрос StatementRequest, получен statementRequestResponseId
        GET_REQUEST_STATUS_1_PHASE = успешно отправленный запрос GetRequestStatus, получен положительный ответ 'DELIVERED'
        INCOMING_PHASE = успешно отправленный запрос Incoming, получен statementRequestResponseId
        GET_REQUEST_STATUS_2_PHASE = успешно отправленный запрос GetRequestStatus, получен запрашиваемый документ
        DOCUMENT_PHASE = цепочка запросов отработала;
    */
    private static final int START_PHASE = 0;
    private static final int STATEMENT_REQUEST_PHASE = 1;
    private static final int GET_REQUEST_STATUS_1_PHASE = 2;
    private static final int INCOMING_PHASE = 3;
    private static final int GET_REQUEST_STATUS_2_PHASE = 4;
    private static final int DOCUMENT_PHASE = 5;

    private final AppUser user;
    private final SoapClient soapClient;

    private int phase = START_PHASE;
    private List<String> phaseList;

    private StatementRequestChainData chainData;

    {
        phaseList = new ArrayList<String>() {{
            add("START_PHASE");
            add("STATEMENT_REQUEST_PHASE");
            add("GET_REQUEST_STATUS_1_PHASE");
            add("INCOMING_PHASE");
            add("GET_REQUEST_STATUS_2_PHASE");
            add("DOCUMENT_PHASE");
        }};
    }

    /*
        Constructor for tests
    */
    public StatementRequestChain(AppUser user, SoapClient soapClient, Logger log, int currentPhase,
                           StatementRequestChainData chainData) {
        this.user = userCheck(user);
        this.soapClient = soapClient;
        this.log = log;
        this.chainData = chainData;
        this.phase = currentPhase;
    }

    public StatementRequestChain(AppUser user, SoapClient soapClient) {
        this.user = userCheck(user);
        this.soapClient = soapClient;
        this.log = LoggerFactory.getLogger(StatementRequestChain.class);
        this.chainData = new StatementRequestChainData();
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
        checkNextPhase(STATEMENT_REQUEST_PHASE);
        log.info("Run StatementRequest for RequestChain(statementRequestResponseId=" + getResponseId() + ")");
        StatementRequestDto result = soapClient.doRequest(user, (StatementRequestData) data);
        chainData.add(result);
        setPhase(STATEMENT_REQUEST_PHASE);
    }

    @Override
    public void nextStep() throws IOException, SAXException {
        if (phase == STATEMENT_REQUEST_PHASE) {
            runGetRequestStatus1();
        } else if (phase == INCOMING_PHASE) {
            runGetRequestStatus2();
        } else {
            throw new RequestChainPhaseNotReadyOrAlreadyPassedException("Phase not ready or already passed.");
        }
    }

    private void runGetRequestStatus1() throws IOException, SAXException {
        checkNextPhase(GET_REQUEST_STATUS_1_PHASE);
        log.info("Run GetRequestStatus for RequestChain(statementRequestResponseId=" + getResponseId() + ")");
        GetRequestStatusDto result = soapClient.doRequest(user, chainData.getStatementRequestResponseId());
        chainData.add(result);
        checkPhase(chainData.getStatementRequestStatus(), GET_REQUEST_STATUS_1_PHASE);
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

    public String getStatementRequestStatus() {
        return chainData.getStatementRequestStatus();
    }

    public String getIncomingStatus() {
        return chainData.getIncomingStatus();
    }

    public StatementRequestDto getStatementRequest() {
        return chainData.getStatementRequest();
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
