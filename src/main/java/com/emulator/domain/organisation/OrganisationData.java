package com.emulator.domain.organisation;

import com.emulator.domain.soap.requests.RequestParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganisationData extends RequestParameters {

    private String id = "";
    private String orgName = "";
    private String orgId = "";
    private String orgInn = "";
    private List<OrganisationAccountData> accounts = new ArrayList<>();

    @Override
    public void check() {

    }

    @Override
    public String toString() {
        return "OrganisationData{" +
                "orgName='" + orgName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgInn='" + orgInn + '\'' +
                ", accounts=" + accounts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrganisationData data = (OrganisationData) o;
        return Objects.equals(orgName, data.orgName) &&
                Objects.equals(orgId, data.orgId) &&
                Objects.equals(orgInn, data.orgInn) &&
                Objects.equals(accounts, data.accounts);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orgName, orgId, orgInn, accounts);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOrgInn() {
        return orgInn;
    }

    public void setOrgInn(String orgInn) {
        this.orgInn = orgInn;
    }

    public List<OrganisationAccountData> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<OrganisationAccountData> accounts) {
        this.accounts = accounts;
    }
}
