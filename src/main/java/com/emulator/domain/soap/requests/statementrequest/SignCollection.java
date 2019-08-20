package com.emulator.domain.soap.requests.statementrequest;

import com.emulator.domain.soap.requests.statementrequest.dto.SignCollectionDto;
import com.emulator.domain.soap.requests.statementrequest.dto.SignDto;

import java.util.ArrayList;
import java.util.List;

public class SignCollection {

    private String bankMessage = "";
    private String digestName = "";
    private List<Sign> signs = new ArrayList<>();

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

    public List<Sign> getSigns() {
        return signs;
    }

    public void setSigns(List<Sign> signs) {
        this.signs = signs;
    }

    public SignCollectionDto getDto() {
        SignCollectionDto dto = new SignCollectionDto();
        dto.setBankMessage(this.getBankMessage());
        dto.setDigestName(this.getDigestName());

        List<SignDto> signsDto = new ArrayList<>(getSigns().size());
        for(Sign sign: getSigns()) {
            signsDto.add(sign.getDto());
        }

        return dto;
    }
}
