package com.emulator.domain.soap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;


import java.util.List;

class SoapMessageStorageUnitTest {

    @Test
    void getLastRequestMessageList_0Messages_EmptyList() {
        Logger log = Mockito.mock(Logger.class);

        SoapMessageStorage list = new SoapMessageStorage(log);
        List<String> messages = list.getLastRequestMessageList();

        Assertions.assertEquals(0, messages.size());
    }

    @Test
    void getLastRequestMessageList_1Messages_ListContains1LastMessage() {
        Logger log = Mockito.mock(Logger.class);
        String test = "test";

        SoapMessageStorage list = new SoapMessageStorage(log);
        list.add(test);
        List<String> messages = list.getLastRequestMessageList();

        Assertions.assertEquals(1, messages.size());
        Assertions.assertEquals(test, messages.get(0));
    }

    @Test
    void getLastRequestMessageList_3Messages_ListContains2LastMessages() {
        Logger log = Mockito.mock(Logger.class);
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";

        SoapMessageStorage list = new SoapMessageStorage(log);
        list.add(test1);
        list.add(test2);
        list.add(test3);
        List<String> messages = list.getLastRequestMessageList();

        Assertions.assertEquals(2, messages.size());
        Assertions.assertEquals(test2, messages.get(0));
        Assertions.assertEquals(test3, messages.get(1));
    }

    @Test
    void getLastMessage_0Messages_EmptyList() {
        Logger log = Mockito.mock(Logger.class);

        SoapMessageStorage list = new SoapMessageStorage(log);
        String message = list.getLastMessage();

        Assertions.assertEquals("", message);
    }

    @Test
    void getLastMessage_1Messages_ListContains1LastMessage() {
        Logger log = Mockito.mock(Logger.class);
        String test = "test";

        SoapMessageStorage list = new SoapMessageStorage(log);
        list.add(test);
        String message = list.getLastMessage();

        Assertions.assertEquals(test, message);
    }

}