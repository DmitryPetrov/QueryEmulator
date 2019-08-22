package com.emulator.domain.soap.requests.getrequeststatus;

import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.getrequeststatus.dto.StateResponseDto;
import com.emulator.domain.soap.requests.getrequeststatus.stateresponse.StateResponse;
import com.emulator.exception.ParameterIsNullException;

import java.util.ArrayList;
import java.util.List;

public class GetRequestStatusResult {

    public final String NAMESPACE_UPG_NAME = "xmlns:upg";
    public final String NAMESPACE_UPG_RUSSIA_NAME = "xmlns:upgRussia";
    public final String NAMESPACE_XSI_NAME = "xmlns:xsi";

    public final String ATTR_CREATE_TIME_NAME = "createTime";
    public final String ATTR_REQUEST_ID_NAME = "requestId";
    public final String ATTR_RESPONSE_ID_NAME = "responseId";
    public final String ATTR_SENDER_NAME = "sender";
    public final String ATTR_VERSION_NAME = "version";

    private String namespaceUpg = "";
    private String namespaceUpgRussia = "";
    private String namespaceXsi = "";

    private String attrCreateTime = "";
    private String attrRequestId = "";
    private String attrResponseId = "";
    private String attrSender = "";
    private String attrVersion = "";

    private boolean notProcessedYet = false;

    private List<StateResponse> stateResponseList;

    public String getNamespaceUpg() {
        return namespaceUpg;
    }

    public void setNamespaceUpg(String namespaceUpg) {
        this.namespaceUpg = namespaceUpg;
    }

    public String getNamespaceUpgRussia() {
        return namespaceUpgRussia;
    }

    public void setNamespaceUpgRussia(String namespaceUpgRussia) {
        this.namespaceUpgRussia = namespaceUpgRussia;
    }

    public String getNamespaceXsi() {
        return namespaceXsi;
    }

    public void setNamespaceXsi(String namespaceXsi) {
        this.namespaceXsi = namespaceXsi;
    }

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
        this.notProcessedYet = true;
    }

    public List<StateResponse> getStateResponseList() {
        if (stateResponseList == null) {
            this.stateResponseList = new ArrayList<>();
        }
        return stateResponseList;
    }

    public void setStateResponseList(List<StateResponse> stateResponseList) {
        if (stateResponseList == null) {
            throw new ParameterIsNullException("com.emulator.domain.soap.requests.getrequeststatus.GetRequestStatusResult" +
                    ".List<StateResponse> stateResponseList must not be null.");
        }
        this.stateResponseList = stateResponseList;
    }

    GetRequestStatusDto getDto() {
        GetRequestStatusDto dto = new GetRequestStatusDto();
        dto.setAttrCreateTime(this.getAttrCreateTime());
        dto.setAttrRequestId(this.getAttrRequestId());
        dto.setAttrResponseId(this.getAttrResponseId());
        dto.setAttrSender(this.getAttrSender());
        dto.setAttrVersion(this.getAttrVersion());
        dto.setNotProcessedYet(this.isNotProcessedYet());

        List<StateResponseDto> stateResponseDtoList = new ArrayList<>(stateResponseList.size());
        for (StateResponse stateResponse: stateResponseList) {
            stateResponseDtoList.add(stateResponse.getDto());
        }
        dto.setStateResponseList(stateResponseDtoList);

        dto.setRequestId(getAttrRequestId());
        dto.setResponseId(getAttrResponseId());
        dto.setRequestName("Get Request Status");

        return dto;
    }
}
