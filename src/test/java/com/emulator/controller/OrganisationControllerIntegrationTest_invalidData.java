package com.emulator.controller;

import com.emulator.domain.frontend.response.Response;
import com.emulator.domain.organisation.OrganisationData;
import com.emulator.domain.organisation.OrganisationRepository;
import com.emulator.domain.organisation.OrganisationTransformer;
import com.emulator.domain.soap.SoapMessageStorage;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.service.OrganisationService;
import com.emulator.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

public class OrganisationControllerIntegrationTest_invalidData {

    private OrganisationData getOrganisationData() {
        OrganisationData data = Mockito.mock(OrganisationData.class);
        doThrow(new RequestParameterLengthException("")).when(data).check();
        return data;
    }

    private HttpSession getSessionMock() {
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUser user = Mockito.mock(AppUser.class);
        String attrName = "user";
        Mockito.when(session.getAttribute(eq(attrName))).thenReturn(user);
        return session;
    }

    private OrganisationController getController() {
        Logger log = Mockito.mock(Logger.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        SoapMessageStorage messageStorage = Mockito.mock(SoapMessageStorage.class);
        ServiceController serviceController = new ServiceController(log, messageStorage);

        UserService service = new UserService();
        OrganisationTransformer transformer = new OrganisationTransformer();
        OrganisationService orgService = new OrganisationService(log, orgRepo, service, transformer);

        return new OrganisationController(serviceController, orgService);
    }

    @Test
    void addOrg_userNotAuthorized_userNotAuthorizedResponse() {
        // given
        HttpSession session = getSessionMock();
        OrganisationData data = getOrganisationData();
        OrganisationController controller = getController();

        // when
        Response response = controller.addOrg(session, data);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("ERROR", response.getStatus());
    }

    @Test
    void getOrgs_userNotAuthorized_userNotAuthorizedResponse() {
        // given
        HttpSession session = getSessionMock();
        OrganisationData data = getOrganisationData();
        OrganisationController controller = getController();

        // when
        Response response = controller.getOrgs(session);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }

    @Test
    void updateOrg_userNotAuthorized_userNotAuthorizedResponse() {
        // given
        HttpSession session = getSessionMock();
        OrganisationData data = getOrganisationData();
        OrganisationController controller = getController();
        String id = "1";

        // when
        Response response = controller.updateOrg(session, id, data);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("ERROR", response.getStatus());
    }

    @Test
    void removeOrg_userNotAuthorized_userNotAuthorizedResponse() {
        // given
        HttpSession session = getSessionMock();
        OrganisationController controller = getController();
        String id = "1";

        // when
        Response response = controller.removeOrg(session, id);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }
}
