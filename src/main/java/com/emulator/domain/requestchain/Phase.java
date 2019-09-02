package com.emulator.domain.requestchain;

enum Phase {

    START(0),
    STATEMENT_REQUEST(1),
    STATEMENT_REQUEST_STATUS(2),
    INCOMING(3),
    STATEMENT_DOCUMENT(4),
    FINNISH(5);

    Phase(int i) {

    }
}
