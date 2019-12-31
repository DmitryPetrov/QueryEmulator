package com.emulator.domain.requestchain;

import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.payrequest.dto.PayRequestDto;
import org.xml.sax.SAXException;

import java.io.IOException;

public class PayRequestChainData {

    private static final String NOT_PROCESSED_YET_STATUS = "NOT PROCESSED YET";

    private String requestId;
    private String responseId;

    private String payRequestRequestId;
    private String payRequestResponseId;
    private String payRequestStatus;
    private String incomingRequestId;
    private String incomingResponseId;
    private String incomingStatus;

    private PayRequestDto payRequest;
    private GetRequestStatusDto getRequestStatus1;
    private IncomingDto incoming;
    private GetRequestStatusDto getRequestStatus2;

    public void add(PayRequestDto payRequestDto) {
        this.payRequest = payRequestDto;
        payRequestRequestId = payRequestDto.getRequestId();
        payRequestResponseId = payRequestDto.getResponseId();
        requestId = payRequestRequestId;
        responseId = payRequestResponseId;
    }

    public void add(IncomingDto incomingDto) {
        this.incoming = incomingDto;
        incomingRequestId = incomingDto.getRequestId();
        incomingResponseId = incomingDto.getResponseId();
    }

    public void add(GetRequestStatusDto getRequestStatusDto) throws IOException, SAXException {
        if ((getRequestStatus1 == null) ||
                (payRequestStatus.equals(NOT_PROCESSED_YET_STATUS))) {
            addGetRequestStatus1(getRequestStatusDto);
        } else {
            addGetRequestStatus2(getRequestStatusDto);
        }
    }

    private void addGetRequestStatus1(GetRequestStatusDto getRequestStatusDto) throws IOException, SAXException {
        this.getRequestStatus1 = getRequestStatusDto;

        if (getRequestStatus1.isNotProcessedYet()) {
            payRequestStatus = NOT_PROCESSED_YET_STATUS;
        } else {
            payRequestStatus = getRequestStatus1.getStateResponseList().get(0).getState();
        }
    }

    private void addGetRequestStatus2(GetRequestStatusDto getRequestStatusDto) throws IOException, SAXException {
        this.getRequestStatus2 = getRequestStatusDto;

        if (getRequestStatus2.isNotProcessedYet()) {
            this.incomingStatus = NOT_PROCESSED_YET_STATUS;
        } else {
            String extId = payRequest.getExternalId();
            this.incomingStatus = getRequestStatus2.getStateResponseList().stream()
                    .filter(stateResponseDto -> stateResponseDto.getExtId().equals(extId))
                    .map(stateResponseDto -> stateResponseDto.getState())
                    .findFirst()
                    .orElse("Pay Request not found");
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public String getResponseId() {
        return responseId;
    }

    public String getPayRequestRequestId() {
        return payRequestRequestId;
    }

    public String getPayRequestResponseId() {
        return payRequestResponseId;
    }

    public String getIncomingRequestId() {
        return incomingRequestId;
    }

    public String getIncomingResponseId() {
        return incomingResponseId;
    }

    public String getPayRequestStatus() {
        return payRequestStatus;
    }

    public String getIncomingStatus() {
        return incomingStatus;
    }

    public PayRequestDto getPayRequest() {
        return payRequest;
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
