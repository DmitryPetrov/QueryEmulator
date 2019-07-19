package com.emulator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class XmlMessagePrinter implements WebServiceMessageCallback {

    @Autowired
    private List<String> soapMessageTrace;

    @Override
    public void doWithMessage(WebServiceMessage message) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        message.writeTo(stream);
        String responseStr = new String(stream.toByteArray());

        soapMessageTrace.add(responseStr);
    }

}
