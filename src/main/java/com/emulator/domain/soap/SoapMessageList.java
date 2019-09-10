package com.emulator.domain.soap;

import com.emulator.controller.StatementRequestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SoapMessageList {

    private static Logger log = LoggerFactory.getLogger(StatementRequestController.class);

    private List<String> messageList = new ArrayList<>();
    private List<String> lastSoapRequest = new ArrayList<>();

    public void add(String message) {
        message = formatMessage(message);

        log.info(message);

        lastSoapRequest.add(message);
        messageList.add(new String(message));
    }

    private String formatMessage(String message) {
        message = message.replaceAll("  ", "");
        message = message.replaceAll("\r", "");
        message = message.replaceAll("\n", "");
        message = message.replaceAll("\t", "");
        message = message.replaceAll("&amp;", "&");
        message = message.replaceAll("&#13;", "");
        message = message.replaceAll("&lt;", "<");
        message = message.replaceAll("&gt;", ">");
        message = message.replaceAll("> ", ">");

        return message;
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

    public String getMessageListAsString() {
        String result = "";
        for (String message : messageList) {
            result += message;
            result += System.lineSeparator();
        }
        return result;
    }

    public void clearLastRequestMessageList() {
        log.debug("Clear last request message list");
        lastSoapRequest.clear();
    }

    public void clear() {
        log.debug("Clear all message list");
        messageList.clear();
        clearLastRequestMessageList();
    }
}
