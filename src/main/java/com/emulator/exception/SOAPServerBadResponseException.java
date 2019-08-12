package com.emulator.exception;

public abstract class SOAPServerBadResponseException extends Exception {

    private String message;
    private String soapMessages;
    private String soapResponse;

    public SOAPServerBadResponseException(String message) {
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

    public String getSoapMessages() {
        return soapMessages;
    }

    public void setSoapMessages(String soapMessages) {
        this.soapMessages = soapMessages;
    }

    public String getSoapResponse() {
        return soapResponse;
    }

    public void setSoapResponse(String soapResponse) {
        this.soapResponse = soapResponse;
    }
}
