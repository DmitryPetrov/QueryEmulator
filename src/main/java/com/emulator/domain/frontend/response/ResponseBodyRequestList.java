package com.emulator.domain.frontend.response;

import com.emulator.domain.entity.RequestParameters;

import java.util.ArrayList;
import java.util.List;

public class ResponseBodyRequestList extends ResponseBodyData{

    private List<RequestParameters> requestList;

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
