package com.emulator.domain.entity;

import com.emulator.domain.soap.exception.RequestParameterLengthException;

public interface RequestParameters {
    void check() throws RequestParameterLengthException;
}
