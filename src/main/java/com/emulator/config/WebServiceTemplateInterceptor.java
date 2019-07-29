package com.emulator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class WebServiceTemplateInterceptor implements ClientInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        printRequest(messageContext);
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        printResponse(messageContext);
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        printResponse(messageContext);
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

    }

    @Autowired
    private List<String> soapMessageTrace;

    private void printResponse(MessageContext messageContext) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            messageContext.getResponse().writeTo(stream);
            String responseStr = new String(stream.toByteArray());

            soapMessageTrace.add(responseStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printRequest(MessageContext messageContext) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            messageContext.getRequest().writeTo(stream);
            String responseStr = new String(stream.toByteArray());

            soapMessageTrace.add(responseStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
