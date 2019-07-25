package com.emulator.domain.frontend;

public class SOAPConnectionStatus {

    private String status;
    private String message;
    private String soapMessages;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
