package com.emulator.domain.soap.requests.incoming;

public class IncomingResult {

    private final String responseId;

    IncomingResult(String responseId) {
        this.responseId = responseId;
    }

    public String getResponseId() {
        return responseId;
    }

}
