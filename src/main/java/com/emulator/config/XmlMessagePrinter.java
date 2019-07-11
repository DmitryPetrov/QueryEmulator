package com.emulator.config;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;

import java.io.IOException;

public class XmlMessagePrinter implements WebServiceMessageCallback {
    @Override
    public void doWithMessage(WebServiceMessage message) throws IOException {
        message.writeTo(System.out);
    }

}
