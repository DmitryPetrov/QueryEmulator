package com.emulator.domain.soap.exception;

public abstract class SOAPServerBadResponseException extends Exception {

    private String message;
    private String soapMessages;

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
}
