package com.emulator.domain.soap.requestchain;

public enum RequestChainPhase {

    START(0),
    STATEMENT_REQUEST(1),
    STATEMENT_REQUEST_STATUS(2),
    INCOMING(3),
    STATEMENT_DOCUMENT(4),
    FINNISH(5);

    RequestChainPhase(int i) {

    }
}
