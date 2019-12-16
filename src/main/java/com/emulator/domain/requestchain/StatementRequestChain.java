package com.emulator.domain.requestchain;

import com.emulator.domain.soap.requests.RequestParameters;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.statementrequest.StatementRequestData;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import com.emulator.exception.ParameterIsNullException;
import com.emulator.exception.RequestChainPhaseNotReadyOrAlreadyPassedException;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.IOException;

public class StatementRequestChain implements RequestChain {

    private static Logger log = LoggerFactory.getLogger(StatementRequestChain.class);

    private static final String STATEMENT_REQUEST_STATUS_DELIVERED = "DELIVERED";

    private final AppUser user;
    private final SoapClient soapClient;

    private Phase phase = Phase.START;

    private String requestId = "";
    private String responseId = "";
    private String statementRequestStatus = "";
    private String incomingRequestId = "";
    private String incomingResponseId = "";
    private String statementDocumentStatus = "";

    private StatementRequestDto statementRequest;
    private GetRequestStatusDto getRequestStatus;
    private IncomingDto incoming;
    private GetRequestStatusDto statementDocument;

    public StatementRequestChain(AppUser user, SoapClient soapClient) {
        if (user == null) {
            throw new ParameterIsNullException("AppUser must not be 'null'");
        }
        if (user.getSessionId().equals("")) {
            throw new UserIsNotAuthorizedException("AppUser user must not be authorized");
        }
        this.user = user;
        this.soapClient = soapClient;
    }

    public void nextStep(RequestParameters data) {
        checkPhase(Phase.STATEMENT_REQUEST);

        log.info("Run StatementRequest for RequestChain(responseId=" + getResponseId() + ")");
        StatementRequestDto dto = soapClient.doRequest(user, (StatementRequestData) data);

        this.requestId = dto.getRequestId();
        this.responseId = dto.getResponseId();
        this.statementRequest = dto;
        this.phase = Phase.STATEMENT_REQUEST;
    }

    @Override
    public void nextStep() throws IOException, SAXException {
        if (phase == Phase.STATEMENT_REQUEST) {
            runGetRequestStatus();
        }
        if (phase == Phase.INCOMING) {
            runGetStatementDocument();
        }
    }

    private void runGetRequestStatus() throws IOException, SAXException {
        checkPhase(Phase.STATEMENT_REQUEST_STATUS);

        log.info("Run GetRequestStatus for RequestChain(responseId=" + getResponseId() + ")");
        GetRequestStatusDto dto = soapClient.doRequest(user, responseId);

        this.getRequestStatus = dto;
        if (dto.isNotProcessedYet()) {
            this.statementRequestStatus = "NOT PROCESSED YET";
        } else {
            this.statementRequestStatus = dto.getStateResponseList().get(0).getState();
        }
        if (this.statementRequestStatus.equals(STATEMENT_REQUEST_STATUS_DELIVERED)) {
            this.phase = Phase.STATEMENT_REQUEST_STATUS;
        }
    }

    @Override
    public void nextStep(IncomingData data) {
        checkPhase(Phase.INCOMING);

        log.info("Run Incoming for RequestChain(responseId=" + getResponseId() + ")");
        IncomingDto dto = soapClient.doRequest(user, data);

        this.incomingRequestId = dto.getRequestId();
        this.incomingResponseId = dto.getResponseId();
        this.incoming = dto;
        this.phase = Phase.INCOMING;
    }

    private void runGetStatementDocument() throws IOException, SAXException {
        checkPhase(Phase.STATEMENT_DOCUMENT);

        log.info("Run GetStatementDocument for RequestChain(responseId=" + getResponseId() + ")");
        GetRequestStatusDto dto = soapClient.doRequest(user, incomingResponseId);

        this.statementDocument = dto;
        if (dto.isNotProcessedYet()) {
            this.statementDocumentStatus = "NOT PROCESSED YET";
        } else {
            String extId = statementRequest.getExternalId();
            this.statementDocumentStatus = dto.getStateResponseList().stream()
                    .filter(stateResponseDto -> stateResponseDto.getExtId().equals(extId))
                    .map(stateResponseDto -> stateResponseDto.getState())
                    .findFirst()
                    .orElse("Statement Request not found");

            this.statementDocumentStatus = "ACCEPTED";
            this.phase = Phase.STATEMENT_DOCUMENT;
        }
    }

    @Override
    public void checkPhase(Phase newPhase) {
        if (this.phase.ordinal() < (newPhase.ordinal() - 1)) {
            throw new RequestChainPhaseNotReadyOrAlreadyPassedException(newPhase + " phase not ready. Current phase: " +
                    "" + this.phase);
        }
        if (this.phase.ordinal() > newPhase.ordinal()) {
            throw new RequestChainPhaseNotReadyOrAlreadyPassedException(newPhase + " phase already passed. Current " +
                    "phase: " + this.phase);
        }
    }

    @Override
    public AppUser getUser() {
        return user;
    }

    @Override
    public Phase getPhase() {
        return phase;
    }

    @Override
    public String getRequestId() {
        return requestId;
    }

    @Override
    public String getResponseId() {
        return responseId;
    }

    public String getStatementRequestStatus() {
        return statementRequestStatus;
    }

    @Override
    public String getIncomingRequestId() {
        return incomingRequestId;
    }

    @Override
    public String getIncomingResponseId() {
        return incomingResponseId;
    }

    public String getStatementDocumentStatus() {
        return statementDocumentStatus;
    }

    public StatementRequestDto getStatementRequest() {
        return statementRequest;
    }

    @Override
    public GetRequestStatusDto getGetRequestStatus() {
        return getRequestStatus;
    }

    @Override
    public IncomingDto getIncoming() {
        return incoming;
    }

    @Override
    public GetRequestStatusDto getStatementDocument() {
        return statementDocument;
    }

    @Override
    public int getPhaseNum() {
        return this.phase.ordinal();
    }
}
