package com.emulator.domain.soap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;


import java.util.List;

class SoapMessageStorageUnitTest {

    Logger log;

    @BeforeEach
    void before() {
        log = Mockito.mock(Logger.class);
    }

    @Test
    void getLastRequestMessageList_0Messages_EmptyList() {
        //when
        List<String> messages = new SoapMessageStorage(log).getLastRequestMessageList();

        //then
        Assertions.assertEquals(0, messages.size());
    }

    @Test
    void getLastRequestMessageList_1Messages_ListContains1LastMessage() {
        //given
        String test = "test";

        //when
        SoapMessageStorage storage = new SoapMessageStorage(log);
        storage.add(test);
        List<String> messages = storage.getLastRequestMessageList();

        //then
        Assertions.assertEquals(1, messages.size());
        Assertions.assertEquals(test, messages.get(0));
    }

    @Test
    void getLastRequestMessageList_3Messages_ListContains2LastMessages() {
        //given
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";

        //when
        SoapMessageStorage storage = new SoapMessageStorage(log);
        storage.add(test1);
        storage.add(test2);
        storage.add(test3);
        List<String> messages = storage.getLastRequestMessageList();

        //then
        Assertions.assertEquals(2, messages.size());
        Assertions.assertEquals(test2, messages.get(0));
        Assertions.assertEquals(test3, messages.get(1));
    }

    @Test
    void getLastMessage_0Messages_EmptyList() {
        //when
        String message = new SoapMessageStorage(log).getLastMessage();

        //then
        Assertions.assertEquals("", message);
    }

    @Test
    void getLastMessage_1Messages_ListContains1LastMessage() {
        //given
        String test = "test";

        //when
        SoapMessageStorage storage = new SoapMessageStorage(log);
        storage.add(test);
        String message = storage.getLastMessage();

        //then
        Assertions.assertEquals(test, message);
    }

}