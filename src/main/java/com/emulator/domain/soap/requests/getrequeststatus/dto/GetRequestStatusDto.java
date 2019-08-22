package com.emulator.domain.soap.requests.getrequeststatus.dto;

import com.emulator.domain.soap.requests.DataTransferObject;

import java.util.List;

public class GetRequestStatusDto extends DataTransferObject {

    private String attrCreateTime = "";
    private String attrRequestId = "";
    private String attrResponseId = "";
    private String attrSender = "";
    private String attrVersion = "";

    private boolean notProcessedYet = false;

    private List<StateResponseDto> stateResponseList;

    public String getAttrCreateTime() {
        return attrCreateTime;
    }

    public void setAttrCreateTime(String attrCreateTime) {
        this.attrCreateTime = attrCreateTime;
    }

    public String getAttrRequestId() {
        return attrRequestId;
    }

    public void setAttrRequestId(String attrRequestId) {
        this.attrRequestId = attrRequestId;
    }

    public String getAttrResponseId() {
        return attrResponseId;
    }

    public void setAttrResponseId(String attrResponseId) {
        this.attrResponseId = attrResponseId;
    }

    public String getAttrSender() {
        return attrSender;
    }

    public void setAttrSender(String attrSender) {
        this.attrSender = attrSender;
    }

    public String getAttrVersion() {
        return attrVersion;
    }

    public void setAttrVersion(String attrVersion) {
        this.attrVersion = attrVersion;
    }

    public boolean isNotProcessedYet() {
        return notProcessedYet;
    }

    public void setNotProcessedYet(boolean notProcessedYet) {
        this.notProcessedYet = notProcessedYet;
    }

    public List<StateResponseDto> getStateResponseList() {
        return stateResponseList;
    }

    public void setStateResponseList(List<StateResponseDto> stateResponseList) {
        this.stateResponseList = stateResponseList;
    }
}
