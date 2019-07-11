package com.emulator.config;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import java.io.IOException;

public class SoapClientInterceptor implements ClientInterceptor {
    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        print(messageContext, "-FROM_SERVER-");
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        print(messageContext, "-FAULT_FROM_SERVER-");
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

    }

    private void print(MessageContext messageContext, String text) {
        try {
            System.out.println();
            System.out.println("----------");
            System.out.println(text);
            messageContext.getResponse().writeTo(System.out);
            System.out.println();
            System.out.println("----------");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
