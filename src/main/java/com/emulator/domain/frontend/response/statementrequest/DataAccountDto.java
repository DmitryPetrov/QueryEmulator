package com.emulator.domain.frontend.response.statementrequest;

public class DataAccountDto {

    private String account = "";
    private String bankBIC = "";
    private String bankName = "";

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankBIC() {
        return bankBIC;
    }

    public void setBankBIC(String bankBIC) {
        this.bankBIC = bankBIC;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
