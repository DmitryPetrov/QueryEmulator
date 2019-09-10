package com.emulator.domain.frontend.response;

import com.emulator.domain.requestchain.RequestChain;

import java.util.ArrayList;
import java.util.List;

public class ResponseBodyData {

    private String status = "";
    private String message = "";
    private RequestChain requestChain;
    private List<RequestChain> requestChainList;
    private List<String> soapMessageList;


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

    public RequestChain getRequestChain() {
        return requestChain;
    }

    public void setRequestChain(RequestChain requestChain) {
        this.requestChain = requestChain;
    }

    public List<RequestChain> getRequestChainList() {
        if (this.requestChainList == null) {
            setRequestChainList(new ArrayList<>());
        }
        return requestChainList;
    }

    public void setRequestChainList(List<RequestChain> requestChainList) {
        this.requestChainList = requestChainList;
    }

    public List<String> getSoapMessageList() {
        if (this.soapMessageList == null) {
            setSoapMessageList(new ArrayList<>());
        }
        return soapMessageList;
    }

    public void setSoapMessageList(List<String> soapMessageList) {
        this.soapMessageList = soapMessageList;
    }

    public String getLogInfo() {
        return "ResponseBodyData{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
