package com.emulator.domain.soap.statementrequest;

import com.emulator.domain.entity.RequestParameters;
import com.emulator.exception.RequestParameterLengthException;

public class DataAccount extends RequestParameters {

    private final String ACCOUNT_DEFAULT_VALUE = "40702810800000005897";
    private final String BANK_BIC_DEFAULT_VALUE = "044030861";
    private final String BANK_NAME_DEFAULT_VALUE = "АО \"АБ \"РОССИЯ\"";

    private String account = "";
    private String bankBIC = "";
    private String bankName = "";

    @Override
    public void check() throws RequestParameterLengthException {
        checkStringLength("account", this.account, 20);
        checkStringLength("bankBIC", this.bankBIC, 64);
        checkStringLength("bankName", this.bankName, 500);
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
}
