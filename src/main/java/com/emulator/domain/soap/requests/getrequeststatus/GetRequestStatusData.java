package com.emulator.domain.soap.requests.getrequeststatus;

import com.emulator.domain.soap.requests.RequestParameters;

public class GetRequestStatusData extends RequestParameters {

    private String responseId = "";

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    @Override
    public void check() {

    }
}
