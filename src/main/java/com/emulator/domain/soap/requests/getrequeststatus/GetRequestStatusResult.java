package com.emulator.domain.soap.requests.getrequeststatus;

import com.emulator.domain.soap.requests.getrequeststatus.dto.GetRequestStatusDto;
import com.emulator.domain.soap.requests.getrequeststatus.dto.statement.StatementDto;
import com.emulator.domain.soap.requests.getrequeststatus.dto.stateresponse.StateResponseDto;
import com.emulator.domain.soap.requests.getrequeststatus.statement.Statement;
import com.emulator.domain.soap.requests.getrequeststatus.stateresponse.StateResponse;

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

    private List<StateResponse> stateResponseList  = new ArrayList<>();
    private List<Statement> statementList  = new ArrayList<>();

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
        this.notProcessedYet = notProcessedYet;
    }

    public List<StateResponse> getStateResponseList() {
        return stateResponseList;
    }

    public boolean add(StateResponse stateResponse) {
        return stateResponseList.add(stateResponse);
    }

    public List<Statement> getStatementList() {
        return statementList;
    }

    public boolean add(Statement statemente) {
        return statementList.add(statemente);
    }

    GetRequestStatusDto getDto() {
        GetRequestStatusDto dto = new GetRequestStatusDto();
        dto.setAttrCreateTime(this.getAttrCreateTime());
        dto.setAttrRequestId(this.getAttrRequestId());
        dto.setAttrResponseId(this.getAttrResponseId());
        dto.setAttrSender(this.getAttrSender());
        dto.setAttrVersion(this.getAttrVersion());
        dto.setNotProcessedYet(this.isNotProcessedYet());

        List<StateResponseDto> stateResponseDtoList = dto.getStateResponseList();
        for (StateResponse stateResponse: this.getStateResponseList()) {
            stateResponseDtoList.add(stateResponse.getDto());
        }

        List<StatementDto> statementDtoList = dto.getStatementList();
        for (Statement statement: this.getStatementList()) {
            statementDtoList.add(statement.getDto());
        }

        dto.setRequestId(getAttrRequestId());
        dto.setResponseId(getAttrResponseId());
        dto.setRequestName("Get Request Status");

        return dto;
    }
}
