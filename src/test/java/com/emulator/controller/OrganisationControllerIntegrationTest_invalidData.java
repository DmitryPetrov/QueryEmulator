package com.emulator.controller;

import com.emulator.domain.frontend.response.Response;
import com.emulator.domain.organisation.OrganisationData;
import com.emulator.repository.OrganisationRepository;
import com.emulator.domain.organisation.OrganisationTransformer;
import com.emulator.domain.soap.SoapMessageStorage;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.RequestParameterLengthException;
import com.emulator.service.OrganisationService;
import com.emulator.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

public class OrganisationControllerIntegrationTest_invalidData {

    private Logger log;
    private HttpSession session;
    private OrganisationRepository orgRepo;
    private SoapMessageStorage messageStorage;
    private ServiceController serviceController;
    private UserService userService;
    private OrganisationTransformer transformer;
    private OrganisationService orgService;
    private OrganisationData orgData;
    private String id;

    @BeforeEach
    void before() {
        log = Mockito.mock(Logger.class);
        orgRepo = Mockito.mock(OrganisationRepository.class);
        messageStorage = Mockito.mock(SoapMessageStorage.class);
        serviceController = new ServiceController(log, messageStorage);
        userService = new UserService();
        transformer = new OrganisationTransformer();
        orgService = new OrganisationService(log, orgRepo, userService, transformer);
        session = getSessionMock();
        orgData = getOrganisationData();
        id = "1";
    }

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

    @Test
    void addOrg_userNotAuthorized_userNotAuthorizedResponse() {
        // when
        Response response = new OrganisationController(serviceController, orgService).addOrg(session, orgData);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("ERROR", response.getStatus());
    }

    @Test
    void getOrgs_userNotAuthorized_userNotAuthorizedResponse() {
        // when
        Response response = new OrganisationController(serviceController, orgService).getOrgs(session);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }

    @Test
    void updateOrg_userNotAuthorized_userNotAuthorizedResponse() {
        // when
        Response response = new OrganisationController(serviceController, orgService).updateOrg(session, id, orgData);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("ERROR", response.getStatus());
    }

    @Test
    void removeOrg_userNotAuthorized_userNotAuthorizedResponse() {
        // when
        Response response = new OrganisationController(serviceController, orgService).removeOrg(session, id);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }
}
