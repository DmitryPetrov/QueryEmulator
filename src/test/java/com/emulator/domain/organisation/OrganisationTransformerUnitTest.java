package com.emulator.domain.organisation;

import com.emulator.repository.entity.Account;
import com.emulator.repository.entity.Organisation;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OrganisationTransformerUnitTest {

    @Test
    void transform() {
        //given
        Organisation org = getOrg();

        //when
        OrganisationData data = new OrganisationTransformer().transform(org);

        //then
        Assert.assertTrue(deepCompare(org, data));
    }

    @Test
    void testTransform() {
        //given
        OrganisationData data = getOrgData();

        //when
        Organisation org = new OrganisationTransformer().transform(data);

        //then
        Assert.assertTrue(deepCompare(org, data));
    }

    private Organisation getOrg() {
        Organisation org = new Organisation();
        List<Account> accounts = new ArrayList<Account>();
        Account account = new Account();

        account.setOrganisation(org);
        account.setBankSettlementType("BankSettlementType");
        account.setBankName("BankName");
        account.setBankCorrAccount("BankCorrAccount");
        account.setBankCity("BankCity");
        account.setBankBic("BankBic");
        account.setAccountId("AccountId");
        account.setAccount("Account");
        account.setId(0);

        accounts.add(account);

        org.setAccounts(accounts);
        org.setOrgName("test org name");
        org.setOrgInn("test org inn");
        org.setOrgId("test org id");
        org.setId(0);
        return org;
    }

    private boolean deepCompare(Organisation org, OrganisationData data) {
        return ((org.getOrgId().equals(data.getOrgId())) &&
                (org.getOrgInn().equals(data.getOrgInn())) &&
                (org.getOrgName().equals(data.getOrgName())) &&
                (compare(org.getAccounts(), data.getAccounts())));
    }

    private boolean compare(List<Account> accounts, List<OrganisationAccountData> accountsData) {
        if (accounts.size() != accountsData.size()) {
            return false;
        }

        for(int i = 0; i < accounts.size(); i++) {
            if (!compare(accounts.get(i), accountsData.get(i))) {
                return false;
            }
        }

        return true;
    }

    private boolean compare(Account account, OrganisationAccountData accountData) {
        return ((account.getAccount().equals(accountData.getAccount())) &&
                (account.getAccountId().equals(accountData.getAccountId())) &&
                (account.getBankBic().equals(accountData.getBankBic())) &&
                (account.getBankCity().equals(accountData.getBankCity())) &&
                (account.getBankCorrAccount().equals(accountData.getBankCorrAccount())) &&
                (account.getBankName().equals(accountData.getBankName())) &&
                (account.getBankSettlementType().equals(accountData.getBankSettlementType())));
    }

    private OrganisationData getOrgData() {
        OrganisationData data = new OrganisationData();
        List<OrganisationAccountData> accounts = new ArrayList<OrganisationAccountData>();
        OrganisationAccountData accountData = new OrganisationAccountData();

        accountData.setBankSettlementType("BankSettlementType");
        accountData.setBankName("BankName");
        accountData.setBankCorrAccount("BankCorrAccount");
        accountData.setBankCity("BankCity");
        accountData.setBankBic("BankBic");
        accountData.setAccountId("AccountId");
        accountData.setAccount("Account");

        accounts.add(accountData);

        data.setAccounts(accounts);
        data.setOrgName("test org name");
        data.setOrgInn("test org inn");
        data.setOrgId("test org id");
        return data;
    }

}