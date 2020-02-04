package com.emulator.domain.frontend.response;

import com.emulator.domain.requestchain.RequestChain;

import java.util.ArrayList;
import java.util.List;

public class ResponseBodyData extends Response {

    private RequestChain requestChain;
    private List<RequestChain> requestChainList;
    private List<String> soapMessageList;


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

}
