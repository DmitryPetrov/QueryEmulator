package com.emulator.service;

import com.emulator.domain.frontend.response.ResponseSoapMessageList;
import com.emulator.domain.soap.SoapMessageStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SoapMessageService {

    private static Logger log;
    private UserService userService;
    private SoapMessageStorage messageStorage;

    /*
        Constructor for tests
    */
    public SoapMessageService(Logger logger, UserService userService, SoapMessageStorage messageStorage) {
        this.log = logger;
        this.userService = userService;
        this.messageStorage = messageStorage;
    }

    @Autowired
    public SoapMessageService(UserService userService, SoapMessageStorage messageStorage) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.userService = userService;
        this.messageStorage = messageStorage;
    }

    public ResponseSoapMessageList getAll(HttpSession session) {
        log.debug("SoapMessageStorageService: Request getAllMessage");
        userService.authorizationCheck(session);
        return getSuccessResponseAllSoapMessage();
    }

    public ResponseSoapMessageList getLastRequest(HttpSession session) {
        log.debug("SoapMessageStorageService: Request getLastRequestMessage");
        userService.authorizationCheck(session);
        return getSuccessResponseLastRequestSoapMessage();
    }


    public ResponseSoapMessageList removeAll(HttpSession session) {
        log.debug("SoapMessageStorageService: Request removeAllMessage");
        userService.authorizationCheck(session);
        messageStorage.clear();
        return getSuccessResponseRemoveAllSoapMessage();
    }

    private ResponseSoapMessageList getSuccessResponseLastRequestSoapMessage() {
        ResponseSoapMessageList result = new ResponseSoapMessageList();
        result.setStatus("OK");
        result.setMessage("Last Request soap message list");
        result.setSoapMessageList(messageStorage.getLastRequestMessageList());
        return result;
    }

    private ResponseSoapMessageList getSuccessResponseAllSoapMessage() {
        ResponseSoapMessageList result = new ResponseSoapMessageList();
        result.setStatus("OK");
        result.setMessage("All soap message list");
        result.setSoapMessageList(messageStorage.getMessageList());
        return result;
    }

    private ResponseSoapMessageList getSuccessResponseRemoveAllSoapMessage() {
        ResponseSoapMessageList result = new ResponseSoapMessageList();
        result.setStatus("OK");
        result.setMessage("Remove soap message list");
        result.setSoapMessageList(messageStorage.getMessageList());
        return result;
    }

}
