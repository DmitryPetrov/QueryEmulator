package com.emulator.domain.soap.requests.getrequeststatus.statement;

import java.util.ArrayList;
import java.util.List;

public class SignCollection {

    public final String BANK_MESSAGE_NODE_NAME = "bankMessage";
    public final String DIGEST_NAME_NODE_NAME = "digestName";
    public final String SIGNS_NODE_NAME = "signs";
    public final String SIGN_NODE_NAME = "Sign";


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

    public boolean add(Sign sign) {
        return signs.add(sign);
    }
}
