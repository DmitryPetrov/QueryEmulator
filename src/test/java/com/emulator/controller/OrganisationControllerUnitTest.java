package com.emulator.controller;

import com.emulator.domain.organisation.OrganisationData;
import com.emulator.exception.UserIsNotAuthorizedException;
import com.emulator.service.OrganisationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;

class OrganisationControllerUnitTest {

    private ServiceController serviceController;
    private OrganisationService orgService;
    private HttpSession session;
    private OrganisationData data;

    @BeforeEach
    void before() {
        serviceController = Mockito.mock(ServiceController.class);
        orgService = Mockito.mock(OrganisationService.class);
        session = Mockito.mock(HttpSession.class);
        data = Mockito.mock(OrganisationData.class);
    }

    @Test
    void addOrg_happyPath_callService() {
        // when
        new OrganisationController(serviceController, orgService).addOrg(session, data);

        // then
        Mockito.verify(orgService).add(session, data);
    }

    @Test
    void getOrgs_happyPath_callService() {
        // when
        new OrganisationController(serviceController, orgService).getOrgs(session);

        // then
        Mockito.verify(orgService).getAll(session);
    }

    @Test
    void updateOrg_happyPath_callService() {
        // given
        String id = "1";

        // when
        new OrganisationController(serviceController, orgService).updateOrg(session, id, data);

        // then
        Mockito.verify(orgService).update(session, id, data);
    }

    @Test
    void removeOrg_happyPath_callService() {
        // given
        String id = "1";

        // when
        new OrganisationController(serviceController, orgService).removeOrg(session, id);

        // then
        Mockito.verify(orgService).remove(session, id);
    }

    @Test
    void addOrg_userNotAuthorized_callGetErrorResponse() {
        // given
        Mockito.when(orgService.add(session, data)).thenThrow(UserIsNotAuthorizedException.class);

        // when
        new OrganisationController(serviceController, orgService).addOrg(session, data);

        // then
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void getOrgs_userNotAuthorized_callGetErrorResponse() {
        // given
        Mockito.when(orgService.getAll(session)).thenThrow(UserIsNotAuthorizedException.class);

        // when
        new OrganisationController(serviceController, orgService).getOrgs(session);

        // then
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void updateOrg_userNotAuthorized_callGetErrorResponse() {
        // given
        String id = "1";
        Mockito.when(orgService.update(session, id, data)).thenThrow(UserIsNotAuthorizedException.class);

        // when
        new OrganisationController(serviceController, orgService).updateOrg(session, id, data);

        // then
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void removeOrg_userNotAuthorized_callGetErrorResponse() {
        // given
        String id = "1";
        Mockito.when(orgService.remove(session, id)).thenThrow(UserIsNotAuthorizedException.class);

        // when
        new OrganisationController(serviceController, orgService).removeOrg(session, id);

        // then
        Mockito.verify(serviceController).getUserIsNotAuthorizedResponse();
    }

    @Test
    void addOrg_serverError_callGetServerFailResponse() {
        // given
        Mockito.when(orgService.add(session, data)).thenThrow(RuntimeException.class);

        // when
        new OrganisationController(serviceController, orgService).addOrg(session, data);

        // then
        Mockito.verify(serviceController).getServerFailResponse(any());
    }

    @Test
    void getOrgs_serverError_callGetServerFailResponse() {
        // given
        Mockito.when(orgService.getAll(session)).thenThrow(RuntimeException.class);

        // when
        new OrganisationController(serviceController, orgService).getOrgs(session);

        // then
        Mockito.verify(serviceController).getServerFailResponse(any());
    }

    @Test
    void updateOrg_serverError_callGetServerFailResponse() {
        // given
        String id = "1";
        Mockito.when(orgService.update(session, id, data)).thenThrow(RuntimeException.class);

        // when
        new OrganisationController(serviceController, orgService).updateOrg(session, id, data);

        // then
        Mockito.verify(serviceController).getServerFailResponse(any());
    }

    @Test
    void removeOrg_serverError_callGetServerFailResponse() {
        // given
        String id = "1";
        Mockito.when(orgService.remove(session, id)).thenThrow(RuntimeException.class);

        // when
        new OrganisationController(serviceController, orgService).removeOrg(session, id);

        // then
        Mockito.verify(serviceController).getServerFailResponse(any());
    }

}