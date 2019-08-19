package com.emulator.domain.frontend.response.Incoming;

import java.util.List;

public class IncomingDataDto {

    private String attrRequestId = "";
    private String attrVersion = "";
    private String attrSender = "";
    private String attrReceiver = "";

    private String attrStateRequest = "";
    private String attrIncomingId = "";
    private String attrTimestamp = "";
    private List<String> docTypes;

    public String getAttrRequestId() {
        return attrRequestId;
    }

    public void setAttrRequestId(String attrRequestId) {
        this.attrRequestId = attrRequestId;
    }

    public String getAttrVersion() {
        return attrVersion;
    }

    public void setAttrVersion(String attrVersion) {
        this.attrVersion = attrVersion;
    }

    public String getAttrSender() {
        return attrSender;
    }

    public void setAttrSender(String attrSender) {
        this.attrSender = attrSender;
    }

    public String getAttrReceiver() {
        return attrReceiver;
    }

    public void setAttrReceiver(String attrReceiver) {
        this.attrReceiver = attrReceiver;
    }

    public String getAttrStateRequest() {
        return attrStateRequest;
    }

    public void setAttrStateRequest(String attrStateRequest) {
        this.attrStateRequest = attrStateRequest;
    }

    public String getAttrIncomingId() {
        return attrIncomingId;
    }

    public void setAttrIncomingId(String attrIncomingId) {
        this.attrIncomingId = attrIncomingId;
    }

    public String getAttrTimestamp() {
        return attrTimestamp;
    }

    public void setAttrTimestamp(String attrTimestamp) {
        this.attrTimestamp = attrTimestamp;
    }

    public List<String> getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(List<String> docTypes) {
        this.docTypes = docTypes;
    }
}
