package com.emulator.domain.soap;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SoapMessageList {

    private List<String> messageList = new ArrayList<>();

    public void add(String message) {
        System.out.println(">>>>SoapMessageList");
        System.out.println(message);
        messageList.add(message);
    }

    public String getAsString() {
        String result = "";
        for (String message : messageList) {
            result += message;
            result += System.lineSeparator();
        }
        return result;
    }

    public void clear() {
        messageList.clear();
    }
}
