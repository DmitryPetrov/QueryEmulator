package com.emulator.repository.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Organisation")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organisationId")
    @SequenceGenerator(name = "organisationId", sequenceName = "organisation_id_seq",
            allocationSize = 1, initialValue = 1)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private long id;

    @Column(name = "orgName", nullable = false, updatable = true, unique = false)
    private String orgName;

    @Column(name = "orgId", nullable = false, updatable = true, unique = true)
    private String orgId;

    @Column(name = "orgInn", nullable = false, updatable = true, unique = true)
    private String orgInn;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organisation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    public Organisation() {
    }

    public void update(Organisation dataSource) {
        this.orgId = dataSource.getOrgId();
        this.orgInn = dataSource.getOrgInn();
        this.orgName = dataSource.getOrgName();

        List<Account> accountsForUpdate = this.accounts;
        List<Account> accountsSource = dataSource.getAccounts();

        //remove account from 'accountsForUpdate' if 'accountsSource' do not have account with same id
        removeAccount(accountsForUpdate, accountsSource);

        //update account from 'accountsForUpdate' if 'accountsSource' have account with same id
        updateAccounts(accountsForUpdate, accountsSource);

        //add account to 'accountsForUpdate' from 'accountsSource' if 'accountsSource' have account with id = 0
        addNewAccounts(accountsForUpdate, accountsSource);
    }

    private void addNewAccounts(List<Account> accountsForUpdate, List<Account> dataSource) {
        for (Account account: dataSource) {
            if (account.getId() == 0) {
                account.setOrganisation(this);
                accountsForUpdate.add(account);
            }
        }
    }

    private void updateAccounts(List<Account> accountsForUpdate, List<Account> dataSource) {
        for (Account account: accountsForUpdate) {
            for (Account accountSource: dataSource) {
                if (account.getId() == accountSource.getId()) {
                    account.update(accountSource);
                    break;
                }
            }
        }
    }

    private void removeAccount(List<Account> accountsForUpdate, List<Account> dataSource) {
        List<Account> accountsForRemove = new ArrayList<>();
        for (Account account: accountsForUpdate) {
            boolean havePair = false;

            for (Account accountData: dataSource) {
                if (account.getId() == accountData.getId()) {
                    havePair = true;
                    break;
                }
            }

            if (!havePair) {
                accountsForRemove.add(account);
            }
        }

        accountsForUpdate.removeAll(accountsForRemove);
    }

    @Override
    public String toString() {
        String accountsId = "";
        for(Account account: accounts) {
            accountsId = account.getId() + " ";
        }
        return "Organisation{" +
                "id=" + id +
                ", orgName='" + orgName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgInn='" + orgInn + '\'' +
                ", accounts={" + accountsId + "}" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organisation)) return false;
        Organisation that = (Organisation) o;
        return id == that.id &&
                orgName.equals(that.orgName) &&
                orgId.equals(that.orgId) &&
                orgInn.equals(that.orgInn) &&
                accounts.equals(that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orgName, orgId, orgInn, accounts);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
