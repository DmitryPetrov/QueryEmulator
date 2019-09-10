package com.emulator.domain.soap.requests.statementrequest;

import com.emulator.domain.soap.requests.RequestParameters;
import com.emulator.domain.soap.requests.statementrequest.dto.DataAccountDto;
import com.emulator.exception.RequestParameterLengthException;

public class DataAccount extends RequestParameters {

    static final String ACCOUNT_NODE_NAME = "account";
    static final String BANK_BIC_NODE_NAME = "bankBIC";
    static final String BANK_NAME_NODE_NAME = "bankName";

    private static final int ACCOUNT_NODE_MAX_LENGTH = 20;
    private static final int BANK_BIC_NODE_MAX_LENGTH = 64;
    private static final int BANK_MAX_LENGTH_NODE_NAME = 500;

    private final String ACCOUNT_DEFAULT_VALUE = "40702810800000005897";
    private final String BANK_BIC_DEFAULT_VALUE = "044030861";
    private final String BANK_NAME_DEFAULT_VALUE = "АО \"АБ \"РОССИЯ\"";

    private String account = ACCOUNT_DEFAULT_VALUE;
    private String bankBIC = BANK_BIC_DEFAULT_VALUE;
    private String bankName = BANK_NAME_DEFAULT_VALUE;

    @Override
    public void check() throws RequestParameterLengthException {
        checkStringLength(ACCOUNT_NODE_NAME, getAccount(), ACCOUNT_NODE_MAX_LENGTH);
        checkStringLength(BANK_BIC_NODE_NAME, getBankBIC(), BANK_BIC_NODE_MAX_LENGTH);
        checkStringLength(BANK_NAME_NODE_NAME, getBankName(), BANK_MAX_LENGTH_NODE_NAME);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = checkNull(account, ACCOUNT_DEFAULT_VALUE);
    }

    public String getBankBIC() {
        return bankBIC;
    }

    public void setBankBIC(String bankBIC) {
        this.bankBIC = checkNull(bankBIC, BANK_BIC_DEFAULT_VALUE);
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = checkNull(bankName, BANK_NAME_DEFAULT_VALUE);
    }

    public DataAccountDto getDto() {
        DataAccountDto dto = new DataAccountDto();
        dto.setAccount(this.getAccount());
        dto.setBankBIC(this.getBankBIC());
        dto.setBankName(this.getBankName());
        return dto;
    }

    @Override
    public String toString() {
        return "DataAccount{" +
                "account='" + account + '\'' +
                ", bankBIC='" + bankBIC + '\'' +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
