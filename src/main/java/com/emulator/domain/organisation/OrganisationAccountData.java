package com.emulator.domain.organisation;

import com.emulator.domain.soap.requests.RequestParameters;

import java.util.Objects;

public class OrganisationAccountData extends RequestParameters {

    private String account = "";
    private String accountId = "";
    private String bankSettlementType = "";
    private String bankCity = "";
    private String bankName = "";
    private String bankBic = "";
    private String bankCorrAccount = "";

    @Override
    public void check() {

    }

    @Override
    public String toString() {
        return "OrganisationAccountData{" +
                "account='" + account + '\'' +
                ", accountId='" + accountId + '\'' +
                ", bankSettlementType='" + bankSettlementType + '\'' +
                ", bankCity='" + bankCity + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankBic='" + bankBic + '\'' +
                ", bankCorrAccount='" + bankCorrAccount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrganisationAccountData that = (OrganisationAccountData) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(bankSettlementType, that.bankSettlementType) &&
                Objects.equals(bankCity, that.bankCity) &&
                Objects.equals(bankName, that.bankName) &&
                Objects.equals(bankBic, that.bankBic) &&
                Objects.equals(bankCorrAccount, that.bankCorrAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, accountId, bankSettlementType, bankCity, bankName, bankBic,
                bankCorrAccount);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBankSettlementType() {
        return bankSettlementType;
    }

    public void setBankSettlementType(String bankSettlementType) {
        this.bankSettlementType = bankSettlementType;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBic() {
        return bankBic;
    }

    public void setBankBic(String bankBic) {
        this.bankBic = bankBic;
    }

    public String getBankCorrAccount() {
        return bankCorrAccount;
    }

    public void setBankCorrAccount(String bankCorrAccount) {
        this.bankCorrAccount = bankCorrAccount;
    }
}
