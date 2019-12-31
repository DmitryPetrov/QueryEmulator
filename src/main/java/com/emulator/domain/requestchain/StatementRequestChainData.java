package com.emulator.domain.requestchain;

import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import org.xml.sax.SAXException;

import java.io.IOException;

public class StatementRequestChainData {

    private static final String NOT_PROCESSED_YET_STATUS = "NOT PROCESSED YET";

    private String requestId;
    private String responseId;

    private String statementRequestRequestId;
    private String statementRequestResponseId;
    private String statementRequestStatus;
    private String incomingRequestId;
    private String incomingResponseId;
    private String incomingStatus;

    private StatementRequestDto statementRequest;
    private GetRequestStatusDto getRequestStatus1;
    private IncomingDto incoming;
    private GetRequestStatusDto getRequestStatus2;

    public void add(StatementRequestDto statementRequestDto) {
        this.statementRequest = statementRequestDto;
        statementRequestRequestId = statementRequestDto.getRequestId();
        statementRequestResponseId = statementRequestDto.getResponseId();
        requestId = statementRequestRequestId;
        responseId = statementRequestResponseId;
    }

    public void add(GetRequestStatusDto getRequestStatusDto) throws IOException, SAXException {
        if ((getRequestStatus1 == null) ||
                (statementRequestStatus.equals(NOT_PROCESSED_YET_STATUS))) {
            addGetRequestStatus1(getRequestStatusDto);
        } else {
            addGetRequestStatus2(getRequestStatusDto);
        }
    }

    public void add(IncomingDto incomingDto) {
        this.incoming = incomingDto;
        incomingRequestId = incomingDto.getRequestId();
        incomingResponseId = incomingDto.getResponseId();
    }

    private void addGetRequestStatus1(GetRequestStatusDto getRequestStatusDto) throws IOException, SAXException {
        this.getRequestStatus1 = getRequestStatusDto;

        if (getRequestStatus1.isNotProcessedYet()) {
            statementRequestStatus = NOT_PROCESSED_YET_STATUS;
        } else {
            statementRequestStatus = getRequestStatus1.getStateResponseList().get(0).getState();
        }
    }

    private void addGetRequestStatus2(GetRequestStatusDto getRequestStatusDto) throws IOException, SAXException {
        this.getRequestStatus2 = getRequestStatusDto;

        if (getRequestStatus2.isNotProcessedYet()) {
            this.incomingStatus = NOT_PROCESSED_YET_STATUS;
        } else {
            String extId = statementRequest.getExternalId();
            this.incomingStatus = getRequestStatus2.getStateResponseList().stream()
                    .filter(stateResponseDto -> stateResponseDto.getExtId().equals(extId))
                    .map(stateResponseDto -> stateResponseDto.getState())
                    .findFirst()
                    .orElse("Statement Request not found");
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public String getResponseId() {
        return responseId;
    }

    public String getStatementRequestRequestId() {
        return statementRequestRequestId;
    }

    public String getStatementRequestResponseId() {
        return statementRequestResponseId;
    }

    public String getIncomingRequestId() {
        return incomingRequestId;
    }

    public String getIncomingResponseId() {
        return incomingResponseId;
    }

    public String getStatementRequestStatus() {
        return statementRequestStatus;
    }

    public String getIncomingStatus() {
        return incomingStatus;
    }

    public StatementRequestDto getStatementRequest() {
        return statementRequest;
    }

    public GetRequestStatusDto getGetRequestStatus1() {
        return getRequestStatus1;
    }

    public IncomingDto getIncoming() {
        return incoming;
    }

    public GetRequestStatusDto getGetRequestStatus2() {
        return getRequestStatus2;
    }
}
