package com.emulator.exception;

import java.util.ArrayList;
import java.util.List;

public class SoapServerBadResponseException extends RuntimeException {

    private String message;
    private List<String> soapMessageList;
    private String soapResponse;

    public SoapServerBadResponseException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getSoapMessageList() {
        if (this.soapMessageList == null) {
            setSoapMessageList(new ArrayList<>());
        }
        return soapMessageList;
    }

    public void setSoapMessageList(List<String> soapMessageList) {
        this.soapMessageList = soapMessageList;
    }

    public String getSoapResponse() {
        return soapResponse;
    }

    public void setSoapResponse(String soapResponse) {
        this.soapResponse = soapResponse;
    }
}
