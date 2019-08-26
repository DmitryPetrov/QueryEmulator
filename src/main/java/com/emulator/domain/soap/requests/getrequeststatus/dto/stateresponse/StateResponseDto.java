package com.emulator.domain.soap.requests.getrequeststatus.dto.stateresponse;

public class StateResponseDto {

    private String bankMessage = "";
    private String bankMessageAuthor = "";
    private String createTime = "";
    private String docId = "";
    private String docType = "";
    private String extId = "";
    private String messageOnlyForBank = "";
    private String operationDate = "";
    private String state = "";

    public String getBankMessage() {
        return bankMessage;
    }

    public void setBankMessage(String bankMessage) {
        this.bankMessage = bankMessage;
    }

    public String getBankMessageAuthor() {
        return bankMessageAuthor;
    }

    public void setBankMessageAuthor(String bankMessageAuthor) {
        this.bankMessageAuthor = bankMessageAuthor;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getMessageOnlyForBank() {
        return messageOnlyForBank;
    }

    public void setMessageOnlyForBank(String messageOnlyForBank) {
        this.messageOnlyForBank = messageOnlyForBank;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
