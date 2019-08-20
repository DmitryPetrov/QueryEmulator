package com.emulator.domain.soap.requestchain;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import com.emulator.exception.ParameterIsNullException;
import com.emulator.exception.PhaseAlreadyPassedException;
import com.emulator.exception.UserIsNotAuthorizedException;

class RequestChain {

    private static final int START = 0;
    private static final int STATEMENT_REQUEST = 1;
    private static final int STATEMENT_REQUEST_STATUS = 2;
    private static final int INCOMING = 3;
    private static final int STATEMENT_DOCUMENT = 4;

    private static final String INCOMING_NAME = "incoming";

    private static final String NOT_PROCESSED_YET_STATUS = "NOT PROCESSED YET";

    private final AppUser user;

    private int phase = START;

    private String requestId = "";
    private String responseId = "";
    private String statementRequestStatus = "";
    private String incomingRequestId = "";
    private String incomingResponseId = "";
    private String statementDocumentStatus = "";

    private StatementRequestDto statementRequest;
    private GetRequestStatusDto getRequestStatus;
    private IncomingDto incoming;

    public RequestChain(AppUser user, StatementRequestDto dto) {
        checkOnNull(user);
        if (user.getSessionId().equals("")) {
            throw new UserIsNotAuthorizedException("AppUser user must not be authorized");
        }
        this.user = user;

        setStatementRequest(dto);
    }

    private void setStatementRequest(StatementRequestDto dto) {
        checkOnNull(dto);

        this.requestId = dto.getRequestId();
        this.responseId = dto.getResponseId();
        this.statementRequest = dto;
        this.phase = STATEMENT_REQUEST;
    }

    public void setGetRequestStatus(GetRequestStatusDto dto) {
        checkPhase(STATEMENT_REQUEST_STATUS);
        checkOnNull(dto);

        this.getRequestStatus = dto;
        this.statementRequestStatus = dto.getStateResponse().getState();
        this.phase = STATEMENT_REQUEST_STATUS;
    }

    public void setIncoming(IncomingDto dto) {
        checkPhase(INCOMING);
        checkOnNull(dto);

        this.incomingRequestId = dto.getRequestId();
        this.incomingResponseId = dto.getResponseId();
        this.incoming = dto;
        this.phase = INCOMING;
    }


    private void checkOnNull(Object object){
        if (object == null) {
            throw new ParameterIsNullException(object.getClass() + " must not be 'null'");
        }
    }

    private void checkPhase(int phase) {
        if (this.phase > phase) {
            throw new PhaseAlreadyPassedException(phase +" phase already passed. Current phase: " + this.phase);
        }
    }

    public StatementRequestDto getStatementRequestDataDto() {
        return statementRequest;
    }

    public GetRequestStatusDto getGetRequestStatusResultDto() {
        return getRequestStatus;
    }

    public IncomingDto getIncomingDataDto() {
        return incoming;
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

}
