package com.emulator.controller;

import com.emulator.domain.frontend.response.Response;
import com.emulator.domain.organisation.*;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.repository.OrganisationRepository;
import com.emulator.repository.entity.Account;
import com.emulator.repository.entity.Organisation;
import com.emulator.service.OrganisationService;
import com.emulator.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class OrganisationControllerIntegrationTest_happyPath {

    private OrganisationData getOrganisationData() {
        OrganisationData data = new OrganisationData();
        data.setOrgId("");
        data.setOrgName("");
        data.setOrgInn("");
        return data;
    }

    private HttpSession getSessionMock() {
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUser user = Mockito.mock(AppUser.class);
        String attrName = "user";
        Mockito.when(session.getAttribute(eq(attrName))).thenReturn(user);
        return session;
    }

    @Test
    void addOrg_happyPath_succeedResponse() {
        // given
        Logger log = Mockito.mock(Logger.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = getSessionMock();

        UserService service = new UserService();
        OrganisationTransformer transformer = new OrganisationTransformer();
        OrganisationService orgService = new OrganisationService(log, orgRepo, service, transformer);
        OrganisationData data = getOrganisationData();

        // when
        Response response = new OrganisationController(serviceController, orgService).addOrg(session, data);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Mockito.verify(orgRepo).save(any(Organisation.class));
    }

    private List<Organisation> getOrgs() {
        List<Organisation> result = new ArrayList<>();
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

        result.add(org);
        return result;
    }

    @Test
    void getOrgs_happyPath_succeedResponse() {
        // given
        Logger log = Mockito.mock(Logger.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = getSessionMock();

        UserService service = new UserService();
        OrganisationTransformer transformer = new OrganisationTransformer();
        OrganisationService orgService = new OrganisationService(log, orgRepo, service, transformer);
        List<Organisation> orgs = getOrgs();

        Mockito.when(orgRepo.findAll()).thenReturn(orgs);

        // when
        OrganisationController controller = new OrganisationController(serviceController, orgService);
        OrganisationResponse response = (OrganisationResponse) controller.getOrgs(session);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Assert.assertEquals(orgs.get(0).getOrgId(), response.getOrganisations().get(0).getOrgId());
    }

    @Test
    void updateOrg_happyPath_succeedResponse() {
        // given
        Logger log = Mockito.mock(Logger.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = getSessionMock();

        UserService service = new UserService();
        OrganisationTransformer transformer = new OrganisationTransformer();
        OrganisationService orgService = new OrganisationService(log, orgRepo, service, transformer);
        OrganisationData data = getOrganisationData();
        String id = "1";

        // when
        Response response = new OrganisationController(serviceController, orgService).updateOrg(session, id, data);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Mockito.verify(orgRepo).save(any(Organisation.class));
    }

    @Test
    void removeOrg_happyPath_succeedResponse() {
        // given
        Logger log = Mockito.mock(Logger.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = getSessionMock();

        UserService service = new UserService();
        OrganisationTransformer transformer = new OrganisationTransformer();
        OrganisationService orgService = new OrganisationService(log, orgRepo, service, transformer);
        String id = "1";

        // when
        Response response = new OrganisationController(serviceController, orgService).removeOrg(session, id);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Mockito.verify(orgRepo).deleteById(Long.parseLong(id));
    }
}