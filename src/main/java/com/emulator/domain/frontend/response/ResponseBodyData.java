package com.emulator.domain.frontend.response;

import com.emulator.domain.soap.requestchain.RequestChain;
import com.emulator.domain.soap.requests.DataTransferObject;

import java.util.ArrayList;
import java.util.List;

public class ResponseBodyData {

    private String status;
    private String message;
    private DataTransferObject object;
    private List<RequestChain> requestList;
    private List<String> soapMessageList;

    public List<String> getSoapMessageList() {
        if (soapMessageList == null) {
            setSoapMessageList(new ArrayList<>());
        }
        return soapMessageList;
    }

    public void setSoapMessageList(List<String> soapMessageList) {
        this.soapMessageList = soapMessageList;
    }

    public String getStatus() {
        if (status == null) {
            setStatus("");
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        if (message == null) {
            setMessage("");
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataTransferObject getObject() {
        if (object == null) {
            setObject(new DataTransferObject());
        }
        return object;
    }

    public void setObject(DataTransferObject object) {
        this.object = object;
    }

    public List<RequestChain> getRequestList() {
        if (this.requestList == null) {
            setRequestList(new ArrayList<>());
        }
        return requestList;
    }

    public void setRequestList(List<RequestChain> requestList) {
        this.requestList = requestList;
    }
}
