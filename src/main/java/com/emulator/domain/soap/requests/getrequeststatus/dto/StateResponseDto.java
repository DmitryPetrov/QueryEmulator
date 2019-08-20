package com.emulator.domain.soap.requests.getrequeststatus.dto;

public class StateResponseDto {

    private String attrXmlns = "";

    private String bankMessage = "";
    private String docId = "";
    private String docType = "";
    private String extId = "";
    private String state = "";

    public String getAttrXmlns() {
        return attrXmlns;
    }

    public void setAttrXmlns(String attrXmlns) {
        this.attrXmlns = attrXmlns;
    }

    public String getBankMessage() {
        return bankMessage;
    }

    public void setBankMessage(String bankMessage) {
        this.bankMessage = bankMessage;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
