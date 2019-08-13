package com.emulator.domain.soap.getRequestStatus;

import com.emulator.exception.ParameterIsNullException;

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

    private GetRequestStatusResultStateResponse getRequestStatusResultStateResponse;

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

    public GetRequestStatusResultStateResponse getGetRequestStatusResultStateResponse() {
        if (getRequestStatusResultStateResponse == null) {
            this.getRequestStatusResultStateResponse = new GetRequestStatusResultStateResponse();
        }
        return getRequestStatusResultStateResponse;
    }

    public void setGetRequestStatusResultStateResponse(GetRequestStatusResultStateResponse getRequestStatusResultStateResponse) {
        if(getRequestStatusResultStateResponse == null) {
            throw new ParameterIsNullException("com.emulator.domain.soap.getRequestStatus.GetRequestStatusResult must not be null.");
        }
        this.getRequestStatusResultStateResponse = getRequestStatusResultStateResponse;
    }
}
