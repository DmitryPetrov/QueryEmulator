package com.emulator.domain.soap.requests.getrequeststatus;

import com.emulator.domain.soap.requests.getrequeststatus.dto.StateResponseDto;

public class StateResponse {

    public final String ATTR_XMLNS_NAME = "xmlns";

    public final String BANK_MESSAGE_NAME = "bankMessage";
    public final String DOC_ID_NAME = "docId";
    public final String DOC_TYPE_NAME = "docType";
    public final String EXT_ID_NAME = "extId";
    public final String STATE_NAME = "state";

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

    public StateResponseDto getDto() {
        StateResponseDto dto = new StateResponseDto();
        dto.setAttrXmlns(this.getAttrXmlns());
        dto.setBankMessage(this.getBankMessage());
        dto.setDocId(this.getDocId());
        dto.setDocType(this.getDocType());
        dto.setExtId(this.getExtId());
        dto.setState(this.getState());

        return dto;
    }
}
