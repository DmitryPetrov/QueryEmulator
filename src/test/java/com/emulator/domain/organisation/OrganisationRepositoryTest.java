package com.emulator.domain.organisation;

import com.emulator.domain.requestchain.PayRequestChain;
import com.emulator.exception.ParameterIsNullException;
import com.emulator.repository.OrganisationRepository;
import com.emulator.repository.entity.Account;
import com.emulator.repository.entity.Organisation;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class OrganisationRepositoryTest {

    private OrganisationRepository repository;

    private List<Organisation> savedOrgs;

    @Autowired
    public OrganisationRepositoryTest(OrganisationRepository repository){
        this.repository = repository;
    }

    private Organisation getOrg(int id) {
        Organisation org = new Organisation();
        List<Account> accounts = new ArrayList<Account>();

        Account accountA = new Account();
        accountA.setOrganisation(org);
        accountA.setBankSettlementType("test BankSettlementType A" + id);
        accountA.setBankName("test BankName A" + id);
        accountA.setBankCorrAccount("test BankCorrAccount A" + id);
        accountA.setBankCity("test BankCity A" + id);
        accountA.setBankBic("test BankBic A" + id);
        accountA.setAccountId("test AccountId A" + id);
        accountA.setAccount("test Account A" + id);

        Account accountB = new Account();
        accountB.setOrganisation(org);
        accountB.setBankSettlementType("test BankSettlementType B" + id);
        accountB.setBankName("test BankName B" + id);
        accountB.setBankCorrAccount("test BankCorrAccount B" + id);
        accountB.setBankCity("test BankCity B" + id);
        accountB.setBankBic("test BankBic B" + id);
        accountB.setAccountId("test AccountId B" + id);
        accountB.setAccount("test Account B" + id);

        accounts.add(accountA);
        accounts.add(accountB);

        org.setAccounts(accounts);
        org.setOrgName("test OrgName " + id);
        org.setOrgInn("test OrgInn " + id);
        org.setOrgId("test OrgId " + id);
        return org;
    }

    @BeforeEach
    void fillDataBase() {
        savedOrgs = new ArrayList<>();
        savedOrgs.add(repository.save(getOrg(1)));
        savedOrgs.add(repository.save(getOrg(2)));
    }

    @AfterEach
    void clearDataBase() {
        repository.deleteAll();
    }

    @Test
    public void save_happyPath_savedEntity() {
        Organisation organisation = getOrg(3);
        organisation = repository.save(organisation);
        Organisation savedOrganisation = repository.getOne(organisation.getId());

        Assert.assertEquals(organisation, savedOrganisation);
    }

    @Test
    public void findAll_happyPath_savedEntities() {
        List<Organisation> result = repository.findAll();

        Assert.assertEquals(result, savedOrgs);
    }

    @Test
    public void delete_happyPath_removedEntity() {
        Organisation orgForRemove = savedOrgs.get(0);

        repository.delete(orgForRemove);

        Assertions.assertThrows(
                DataAccessException.class,
                () -> repository.getOne(orgForRemove.getId())
        );
    }

    @Test
    public void update_happyPath_updatedRecord() {
        String updatedOrgId = "updated OrgId";
        String updatedAccountId = "updated AccountId";

        Organisation orgForUpdate = savedOrgs.get(0);
        orgForUpdate.setOrgId(updatedOrgId);
        orgForUpdate.getAccounts().get(0).setAccountId(updatedAccountId);
        orgForUpdate.getAccounts().add(getAccount(orgForUpdate));

        repository.save(orgForUpdate);

        Organisation updatedOrg = repository.getOne(orgForUpdate.getId());

        Assert.assertEquals(updatedOrgId, updatedOrg.getOrgId());
        Assert.assertEquals(updatedAccountId, updatedOrg.getAccounts().get(0).getAccountId());
        Assert.assertEquals(3, updatedOrg.getAccounts().size());
    }

    private Account getAccount(Organisation org) {
        Account account = new Account();
        account.setOrganisation(org);
        account.setBankSettlementType("BankSettlementType");
        account.setBankName("BankName");
        account.setBankCorrAccount("BankCorrAccount");
        account.setBankCity("BankCity");
        account.setBankBic("BankBic");
        account.setAccountId("AccountId");
        account.setAccount("Account");
        return account;
    }

}