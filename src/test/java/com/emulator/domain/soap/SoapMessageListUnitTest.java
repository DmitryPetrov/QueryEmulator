package com.emulator.domain.soap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SoapMessageListUnitTest {

    @Test
    void getLastRequestMessageList_3Messages_2LastMessages() {
        Logger log = Mockito.mock(Logger.class);
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";

        SoapMessageList list = new SoapMessageList(log);
        list.add(test1);
        list.add(test2);
        list.add(test3);
        List<String> messages = list.getLastRequestMessageList();

        Assertions.assertEquals(2, messages.size());
        Assertions.assertEquals(test2, messages.get(0));
        Assertions.assertEquals(test3, messages.get(1));

    }
}