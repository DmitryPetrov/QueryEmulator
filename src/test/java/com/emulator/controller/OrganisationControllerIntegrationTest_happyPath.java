package com.emulator.controller;

import com.emulator.domain.frontend.response.Response;
import com.emulator.domain.organisation.OrganisationData;
import com.emulator.domain.organisation.OrganisationRepository;
import com.emulator.domain.organisation.OrganisationResponse;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.service.OrganisationService;
import com.emulator.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import java.util.List;

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
        OrganisationService orgService = new OrganisationService(log, orgRepo, service);
        OrganisationData data = getOrganisationData();

        // when
        Response response = new OrganisationController(serviceController, orgService).addOrg(session, data);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Mockito.verify(orgRepo).add(data);
    }

    @Test
    void getOrgs_happyPath_succeedResponse() {
        // given
        Logger log = Mockito.mock(Logger.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = getSessionMock();

        UserService service = new UserService();
        OrganisationService orgService = new OrganisationService(log, orgRepo, service);
        List<OrganisationData> orgs = Mockito.mock(List.class);

        Mockito.when(orgRepo.getAll()).thenReturn(orgs);

        // when
        OrganisationController controller = new OrganisationController(serviceController, orgService);
        OrganisationResponse response = (OrganisationResponse) controller.getOrgs(session);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Assert.assertSame(orgs, response.getOrganisations());
    }

    @Test
    void updateOrg_happyPath_succeedResponse() {
        // given
        Logger log = Mockito.mock(Logger.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = getSessionMock();

        UserService service = new UserService();
        OrganisationService orgService = new OrganisationService(log, orgRepo, service);
        OrganisationData data = getOrganisationData();
        String id = "1";

        // when
        Response response = new OrganisationController(serviceController, orgService).updateOrg(session, id, data);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Mockito.verify(orgRepo).update(Long.parseLong(id), data);
    }

    @Test
    void removeOrg_happyPath_succeedResponse() {
        // given
        Logger log = Mockito.mock(Logger.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        ServiceController serviceController = Mockito.mock(ServiceController.class);
        HttpSession session = getSessionMock();

        UserService service = new UserService();
        OrganisationService orgService = new OrganisationService(log, orgRepo, service);
        String id = "1";

        // when
        Response response = new OrganisationController(serviceController, orgService).removeOrg(session, id);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Mockito.verify(orgRepo).remove(Long.parseLong(id));
    }
}