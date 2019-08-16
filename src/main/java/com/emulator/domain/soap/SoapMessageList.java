package com.emulator.domain.soap;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SoapMessageList {

    private List<String> messageList = new ArrayList<>();
    private List<String> lastSoapRequest = new ArrayList<>();

    public void add(String message) {
        System.out.println(">>>>SoapMessageList");
        System.out.println(message);
        lastSoapRequest.add(message);
        messageList.add(new String(message));
    }

    public String getLastRequestAsString() {
        String result = "";
        for (String message : messageList) {
            result += message;
            result += System.lineSeparator();
        }
        return result;
    }

    public List<String> getLastRequestMessageList() {
        return lastSoapRequest;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void clearLastRequestMessageList() {
        lastSoapRequest.clear();
    }

    public void clear() {
        messageList.clear();
        clearLastRequestMessageList();
    }
}
