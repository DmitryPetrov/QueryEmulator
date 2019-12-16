package com.emulator.domain.requestchain;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import com.emulator.domain.soap.requests.incoming.IncomingDto;
import org.xml.sax.SAXException;

import java.io.IOException;

public interface RequestChain {

    void nextStep() throws IOException, SAXException;

    void nextStep(IncomingData data);

    void checkPhase(Phase newPhase);

    AppUser getUser();

    Phase getPhase();

    String getRequestId();

    String getResponseId();

    String getIncomingRequestId();

    String getIncomingResponseId();

    GetRequestStatusDto getGetRequestStatus();

    IncomingDto getIncoming();

    GetRequestStatusDto getStatementDocument();

    int getPhaseNum();
}
