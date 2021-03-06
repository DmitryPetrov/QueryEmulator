package com.emulator.domain.soap.signcollection.dto;

import java.util.ArrayList;
import java.util.List;

public class SignCollectionDto {

    private String bankMessage = "";
    private String digestName = "";
    private List<SignDto> signs = new ArrayList<>();

    public String getBankMessage() {
        return bankMessage;
    }

    public void setBankMessage(String bankMessage) {
        this.bankMessage = bankMessage;
    }

    public String getDigestName() {
        return digestName;
    }

    public void setDigestName(String digestName) {
        this.digestName = digestName;
    }

    public List<SignDto> getSigns() {
        return signs;
    }

}
