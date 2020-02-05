package com.emulator.domain.organisation;

import com.emulator.domain.soap.requests.RequestParameters;

import java.util.Objects;

public class OrganisationData extends RequestParameters {

    private String orgName = "";
    private String orgId = "";
    private String payerName = "";
    private String payerAccount = "";
    private String payerInn = "";
    private String payerId = "";
    private String payerBankName = "";
    private String payerBankBic = "";
    private String payerBankCorrAccount = "";

    @Override
    public void check() {

    }

    @Override
    public String toString() {
        return "OrganisationData{" +
                "orgName='" + orgName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", payerName='" + payerName + '\'' +
                ", payerAccount='" + payerAccount + '\'' +
                ", payerInn='" + payerInn + '\'' +
                ", payerId='" + payerId + '\'' +
                ", payerBankName='" + payerBankName + '\'' +
                ", payerBankBic='" + payerBankBic + '\'' +
                ", payerBankCorrAccount='" + payerBankCorrAccount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrganisationData that = (OrganisationData) o;
        return Objects.equals(orgName, that.orgName) &&
                Objects.equals(orgId, that.orgId) &&
                Objects.equals(payerName, that.payerName) &&
                Objects.equals(payerAccount, that.payerAccount) &&
                Objects.equals(payerInn, that.payerInn) &&
                Objects.equals(payerId, that.payerId) &&
                Objects.equals(payerBankName, that.payerBankName) &&
                Objects.equals(payerBankBic, that.payerBankBic) &&
                Objects.equals(payerBankCorrAccount, that.payerBankCorrAccount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orgName, orgId, payerName, payerAccount, payerInn, payerId, payerBankName, payerBankBic, payerBankCorrAccount);
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getPayerInn() {
        return payerInn;
    }

    public void setPayerInn(String payerInn) {
        this.payerInn = payerInn;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getPayerBankBic() {
        return payerBankBic;
    }

    public void setPayerBankBic(String payerBankBic) {
        this.payerBankBic = payerBankBic;
    }

    public String getPayerBankCorrAccount() {
        return payerBankCorrAccount;
    }

    public void setPayerBankCorrAccount(String payerBankCorrAccount) {
        this.payerBankCorrAccount = payerBankCorrAccount;
    }

}
