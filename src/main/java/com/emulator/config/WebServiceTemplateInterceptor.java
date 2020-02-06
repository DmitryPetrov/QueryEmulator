package com.emulator.config;

import com.emulator.domain.soap.SoapMessageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WebServiceTemplateInterceptor implements ClientInterceptor {

    private static final String CHARSET_NAME_UTF8 = "UTF-8";

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        saveRequestMessage(messageContext);
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        saveResponseMessage(messageContext);
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        saveResponseMessage(messageContext);
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

    }

    @Autowired
    private SoapMessageStorage storage;

    private void saveResponseMessage(MessageContext messageContext) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            messageContext.getResponse().writeTo(stream);
            storage.add(stream.toString(CHARSET_NAME_UTF8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveRequestMessage(MessageContext messageContext) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            messageContext.getRequest().writeTo(stream);
            storage.add(stream.toString(CHARSET_NAME_UTF8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
