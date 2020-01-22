package com.emulator.domain.soap;

import com.emulator.controller.StatementRequestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SoapMessageList {

    private static Logger log;

    private List<String> messageList = new ArrayList<>();

    public SoapMessageList(Logger log) {
        this.log = log;
    }

    public SoapMessageList() {
        this.log = LoggerFactory.getLogger(StatementRequestController.class);
    }

    public void add(String message) {
        message = formatMessage(message);
        log.info(message);
        messageList.add(message);
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

    public List<String> getLastRequestMessageList() {
        List<String> result = null;

        int size = messageList.size();
        if (size == 0) {
            result = new ArrayList<>();
        }
        if (size == 1) {
            result = new ArrayList<>();
            result.add(messageList.get(0));
        }
        if (size >= 2) {
            result = messageList.subList((size - 2), size);
        }

        return result;
    }

    public String getLastMessage() {
        if (messageList.size() == 0) {
            return "";
        }
        return messageList.get(messageList.size() - 1);
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

    public void clear() {
        log.debug("Clear all message list");
        messageList.clear();
    }
}
