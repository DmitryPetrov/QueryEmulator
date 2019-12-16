package com.emulator.domain.requestchain;

import com.emulator.domain.soap.requests.RequestParameters;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import org.xml.sax.SAXException;

import java.io.IOException;

public class PayRequestChain implements RequestChain{


    public void nextStep(RequestParameters data) {

    }

    @Override
    public void nextStep() throws IOException, SAXException {

    }

    @Override
    public void nextStep(IncomingData data) {

    }

    @Override
    public void checkPhase(Phase newPhase) {

    }

    @Override
    public AppUser getUser() {
        return null;
    }

    @Override
    public Phase getPhase() {
        return null;
    }

    @Override
    public String getRequestId() {
        return null;
    }

    @Override
    public String getResponseId() {
        return null;
    }

    public String getStatementRequestStatus() {
        return null;
    }

    @Override
    public String getIncomingRequestId() {
        return null;
    }

    @Override
    public String getIncomingResponseId() {
        return null;
    }

    public String getStatementDocumentStatus() {
        return null;
    }

    public StatementRequestDto getStatementRequest() {
        return null;
    }

    @Override
    public GetRequestStatusDto getGetRequestStatus() {
        return null;
    }

    @Override
    public IncomingDto getIncoming() {
        return null;
    }

    @Override
    public GetRequestStatusDto getStatementDocument() {
        return null;
    }

    @Override
    public int getPhaseNum() {
        return 0;
    }
}
