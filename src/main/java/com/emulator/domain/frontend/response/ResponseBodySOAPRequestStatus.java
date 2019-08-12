package com.emulator.domain.frontend.response;

public class ResponseBodySOAPRequestStatus extends ResponseBodyData {

    private String soapMessages;

    public String getSoapMessages() {
        return soapMessages;
    }

    public void setSoapMessages(String soapMessages) {
        this.soapMessages = soapMessages;
    }
}
