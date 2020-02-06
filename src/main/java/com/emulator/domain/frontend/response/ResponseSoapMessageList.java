package com.emulator.domain.frontend.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseSoapMessageList extends Response{

    private List<String> soapMessageList;

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
