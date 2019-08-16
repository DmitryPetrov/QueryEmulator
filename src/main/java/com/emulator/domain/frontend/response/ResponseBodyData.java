package com.emulator.domain.frontend.response;

import com.emulator.domain.entity.RequestParameters;

import java.util.ArrayList;
import java.util.List;

public class ResponseBodyData {

    private String status;
    private String message;
    private Object object;
    private List<RequestParameters> requestList;
    private List<String> soapMessageList;

    public List<String> getSoapMessageList() {
        return soapMessageList;
    }

    public void setSoapMessageList(List<String> soapMessageList) {
        this.soapMessageList = soapMessageList;
    }

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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<RequestParameters> getRequestList() {
        if (this.requestList == null) {
            this.requestList = new ArrayList<>();
        }
        return requestList;
    }

    public void setRequestList(List<RequestParameters> requestList) {
        this.requestList = requestList;
    }
}
