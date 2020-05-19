package com.emulator.repository.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountId")
    @SequenceGenerator(name = "accountId", sequenceName = "account_id_seq", allocationSize = 1,
            initialValue = 1)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idOrganisation")
    private Organisation organisation;

    @Column(name = "account", nullable = false, updatable = true, unique = true)
    private String account = "";

    @Column(name = "accountId", nullable = false, updatable = true, unique = true)
    private String accountId = "";

    @Column(name = "bankSettlementType", nullable = false, updatable = true, unique = false)
    private String bankSettlementType = "";

    @Column(name = "bankCity", nullable = false, updatable = true, unique = false)
    private String bankCity = "";

    @Column(name = "bankName", nullable = false, updatable = true, unique = false)
    private String bankName = "";

    @Column(name = "bankBic", nullable = false, updatable = true, unique = false)
    private String bankBic = "";

    @Column(name = "bankCorrAccount", nullable = false, updatable = true, unique = false)
    private String bankCorrAccount = "";

    public Account() {
    }

    public void update(Account source) {
        this.account = source.getAccount();
        this.accountId = source.getAccountId();
        this.bankSettlementType = source.getBankSettlementType();
        this.bankCity = source.getBankCity();
        this.bankName = source.getBankName();
        this.bankBic = source.getBankBic();
        this.bankCorrAccount = source.getBankCorrAccount();
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", account='" + account + '\'' +
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
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account1 = (Account) o;
        return id == account1.id &&
                account.equals(account1.account) &&
                accountId.equals(account1.accountId) &&
                bankSettlementType.equals(account1.bankSettlementType) &&
                bankCity.equals(account1.bankCity) &&
                bankName.equals(account1.bankName) &&
                bankBic.equals(account1.bankBic) &&
                bankCorrAccount.equals(account1.bankCorrAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, accountId, bankSettlementType, bankCity, bankName, bankBic, bankCorrAccount);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
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
