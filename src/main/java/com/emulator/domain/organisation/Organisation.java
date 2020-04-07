package com.emulator.domain.organisation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organisation")
    private List<Account> accounts = new ArrayList<>();

    public Organisation() {
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "id=" + id +
                ", orgName='" + orgName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgInn='" + orgInn + '\'' +
                ", accounts=" + accounts +
                '}';
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
