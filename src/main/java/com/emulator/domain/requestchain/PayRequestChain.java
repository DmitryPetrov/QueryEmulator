package com.emulator.domain.requestchain;

import com.emulator.domain.soap.requests.RequestParameters;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import org.xml.sax.SAXException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

public class PayRequestChain implements RequestChain{


    public void nextStep(RequestParameters data) {
        throw new NotImplementedException();
    }

    @Override
    public void nextStep() throws IOException, SAXException {
        throw new NotImplementedException();
    }

    @Override
    public void nextStep(IncomingData data) {
        throw new NotImplementedException();
    }

    @Override
    public void checkPhase(Phase newPhase) {
        throw new NotImplementedException();
    }

    @Override
    public AppUser getUser() {
        throw new NotImplementedException();
    }

    @Override
    public Phase getPhase() {
        throw new NotImplementedException();
    }

    @Override
    public String getRequestId() {
        throw new NotImplementedException();
    }

    @Override
    public String getResponseId() {
        throw new NotImplementedException();
    }

    public String getStatementRequestStatus() {
        throw new NotImplementedException();
    }

    @Override
    public String getIncomingRequestId() {
        throw new NotImplementedException();
    }

    @Override
    public String getIncomingResponseId() {
        throw new NotImplementedException();
    }

    public String getStatementDocumentStatus() {
        throw new NotImplementedException();
    }

    public StatementRequestDto getStatementRequest() {
        throw new NotImplementedException();
    }

    @Override
    public GetRequestStatusDto getGetRequestStatus() {
        throw new NotImplementedException();
    }

    @Override
    public IncomingDto getIncoming() {
        throw new NotImplementedException();
    }

    @Override
    public GetRequestStatusDto getStatementDocument() {
        throw new NotImplementedException();
    }

    @Override
    public int getPhaseNum() {
        throw new NotImplementedException();
    }
}
