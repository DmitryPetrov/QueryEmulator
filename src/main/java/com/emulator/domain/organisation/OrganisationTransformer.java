package com.emulator.domain.organisation;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrganisationTransformer {

    public Organisation transform(OrganisationData data) {
        Organisation org = new Organisation();
        org.setOrgId(data.getOrgId());
        org.setOrgInn(data.getOrgInn());
        org.setOrgName(data.getOrgName());
        org.setAccounts(transform(data.getAccounts(), org));
        return org;
    }

    private List<Account> transform(List<OrganisationAccountData> list, Organisation org) {
        List<Account> accounts = new ArrayList<>(list.size());
        for (OrganisationAccountData accountData : list) {
            accounts.add(transform(accountData, org));
        }
        return accounts;
    }

    private Account transform(OrganisationAccountData accountData, Organisation org) {
        Account account = new Account();
        account.setAccount(accountData.getAccount());
        account.setAccountId(accountData.getAccountId());
        account.setBankBic(accountData.getBankBic());
        account.setBankCity(accountData.getBankCity());
        account.setBankCorrAccount(accountData.getBankCorrAccount());
        account.setBankName(accountData.getBankName());
        account.setBankSettlementType(accountData.getBankSettlementType());
        account.setOrganisation(org);
        return account;
    }

    public OrganisationData transform(Organisation org) {
        OrganisationData data = new OrganisationData();
        data.setOrgId(org.getOrgId());
        data.setOrgInn(org.getOrgInn());
        data.setOrgName(org.getOrgName());
        data.setAccounts(transform(org.getAccounts()));
        return data;
    }

    private List<OrganisationAccountData> transform(List<Account> accounts) {
        List<OrganisationAccountData> list = new ArrayList<>(accounts.size());
        for (Account account : accounts) {
            list.add(transform(account));
        }
        return list;
    }

    private OrganisationAccountData transform(Account account) {
        OrganisationAccountData accountData = new OrganisationAccountData();
        accountData.setAccount(account.getAccount());
        accountData.setAccountId(account.getAccountId());
        accountData.setBankBic(account.getBankBic());
        accountData.setBankCity(account.getBankCity());
        accountData.setBankCorrAccount(account.getBankCorrAccount());
        accountData.setBankName(account.getBankName());
        accountData.setBankSettlementType(account.getBankSettlementType());
        return accountData;
    }
}
