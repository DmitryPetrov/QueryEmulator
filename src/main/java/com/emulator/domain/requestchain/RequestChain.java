package com.emulator.domain.requestchain;

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
import org.xml.sax.SAXException;

import java.io.IOException;

public class RequestChain {

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

    public RequestChain(AppUser user, SoapClient soapClient) {
        if (user == null) {
            throw new ParameterIsNullException("AppUser must not be 'null'");
        }
        if (user.getSessionId().equals("")) {
            throw new UserIsNotAuthorizedException("AppUser user must not be authorized");
        }
        this.user = user;
        this.soapClient = soapClient;
    }

    public void nextStep(StatementRequestData data) {
        checkPhase(Phase.STATEMENT_REQUEST);

        StatementRequestDto dto = soapClient.sendStatementRequest(user, data);

        this.requestId = dto.getRequestId();
        this.responseId = dto.getResponseId();
        this.statementRequest = dto;
        this.phase = Phase.STATEMENT_REQUEST;
    }

    public void nextStep() throws IOException, SAXException {
        if (phase == Phase.STATEMENT_REQUEST) {
            runGetRequestStatus();
        }
        if (phase == Phase.INCOMING) {
            runGetStatementDocument();
        }
    }

    public void runGetRequestStatus() throws IOException, SAXException {
        checkPhase(Phase.STATEMENT_REQUEST_STATUS);

        GetRequestStatusDto dto = soapClient.sendGetRequestStatus(user, responseId);

        this.getRequestStatus = dto;
        this.statementRequestStatus = dto.getStateResponseList().get(0).getState();
        if (this.statementRequestStatus.equals(STATEMENT_REQUEST_STATUS_DELIVERED)) {
            this.phase = Phase.STATEMENT_REQUEST_STATUS;
        }
    }

    public void nextStep(IncomingData data) {
        checkPhase(Phase.INCOMING);

        IncomingDto dto = soapClient.sendIncoming(user, data);

        this.incomingRequestId = dto.getRequestId();
        this.incomingResponseId = dto.getResponseId();
        this.incoming = dto;
        this.phase = Phase.INCOMING;
    }

    public void runGetStatementDocument() throws IOException, SAXException {
        checkPhase(Phase.STATEMENT_DOCUMENT);

        GetRequestStatusDto dto = soapClient.sendGetRequestStatus(user, incomingResponseId);

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

    public AppUser getUser() {
        return user;
    }

    public Phase getPhase() {
        return phase;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getResponseId() {
        return responseId;
    }

    public String getStatementRequestStatus() {
        return statementRequestStatus;
    }

    public String getIncomingRequestId() {
        return incomingRequestId;
    }

    public String getIncomingResponseId() {
        return incomingResponseId;
    }

    public String getStatementDocumentStatus() {
        return statementDocumentStatus;
    }

    public StatementRequestDto getStatementRequest() {
        return statementRequest;
    }

    public GetRequestStatusDto getGetRequestStatus() {
        return getRequestStatus;
    }

    public IncomingDto getIncoming() {
        return incoming;
    }

    public GetRequestStatusDto getStatementDocument() {
        return statementDocument;
    }

    public int getPhaseNum() {
        return this.phase.ordinal();
    }
}
