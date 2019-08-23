package com.emulator.domain.soap.requestchain;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import com.emulator.exception.ParameterIsNullException;
import com.emulator.exception.RequestChainPhaseNotReadyOrAlreadyPassedException;
import com.emulator.exception.UserIsNotAuthorizedException;

public class RequestChain {

    private final AppUser user;

    private RequestChainPhase phase = RequestChainPhase.START;

    private String requestId = "";
    private String responseId = "";
    private String statementRequestStatus = "";
    private String incomingRequestId = "";
    private String incomingResponseId = "";
    private String statementDocumentStatus = "";

    private StatementRequestDto statementRequest;
    private GetRequestStatusDto getRequestStatus;
    private IncomingDto incoming;

    public RequestChain(AppUser user) {
        checkOnNull(user);
        if (user.getSessionId().equals("")) {
            throw new UserIsNotAuthorizedException("AppUser user must not be authorized");
        }
        this.user = user;
    }

    public void setStatementRequest(StatementRequestDto dto) {
        checkOnNull(dto);

        this.requestId = dto.getRequestId();
        this.responseId = dto.getResponseId();
        this.statementRequest = dto;
        this.phase = RequestChainPhase.STATEMENT_REQUEST;
    }

    public void setGetRequestStatus(GetRequestStatusDto dto) {
        checkPhase(RequestChainPhase.STATEMENT_REQUEST_STATUS);
        checkOnNull(dto);

        this.getRequestStatus = dto;
        this.statementRequestStatus = dto.getStateResponseList().get(0).getState();
        this.phase = RequestChainPhase.STATEMENT_REQUEST_STATUS;
    }

    public void setIncoming(IncomingDto dto) {
        checkPhase(RequestChainPhase.INCOMING);
        checkOnNull(dto);

        this.incomingRequestId = dto.getRequestId();
        this.incomingResponseId = dto.getResponseId();
        this.incoming = dto;
        this.phase = RequestChainPhase.INCOMING;
    }


    private void checkOnNull(Object object) {
        if (object == null) {
            throw new ParameterIsNullException(object.getClass() + " must not be 'null'");
        }
    }

    public void checkPhase(RequestChainPhase newPhase) {
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
