package com.emulator.domain.requestchain;

import com.emulator.domain.soap.requests.RequestParameters;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.incoming.IncomingData;
import org.xml.sax.SAXException;

import java.io.IOException;

public interface RequestChain {

    void nextStep(RequestParameters data);

    void nextStep() throws IOException, SAXException;

    void nextStep(IncomingData data);

    AppUser getUser();

    String getPhase();

    String getRequestId();

    String getResponseId();

    int getPhaseNum();
}
