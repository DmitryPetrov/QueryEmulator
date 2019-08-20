package com.emulator.domain.soap.requests.statementrequest;

public class StatementRequestResult {

    private final String responseId;

    StatementRequestResult(String responseId) {
        this.responseId = responseId;
    }

    public String getResponseId() {
        return responseId;
    }
}
