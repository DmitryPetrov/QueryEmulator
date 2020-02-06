package com.emulator.service;

import com.emulator.domain.organisation.OrganisationData;
import com.emulator.domain.organisation.OrganisationRepository;
import com.emulator.domain.organisation.OrganisationResponse;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.mockito.Mockito.doThrow;

public class OrganisationServiceUnitTest {

    @Test
    void add_happyPath_succeedResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData org = Mockito.mock(OrganisationData.class);

        // when
        OrganisationResponse response = new OrganisationService(log, orgRepo, service).add(session, org);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }

    @Test
    void getAll_happyPath_succeedResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        List<OrganisationData> orgs = Mockito.mock(List.class);

        Mockito.when(orgRepo.getAll()).thenReturn(orgs);

        // when
        OrganisationResponse response = new OrganisationService(log, orgRepo, service).getAll(session);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Assert.assertSame(orgs, response.getOrganisations());
    }

    @Test
    void update_happyPath_succeedResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData data = Mockito.mock(OrganisationData.class);
        String id = "1";

        // when
        OrganisationResponse response = new OrganisationService(log, orgRepo, service).update(session, id, data);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }

    @Test
    void remove_happyPath_succeedResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        String id = "1";

        // when
        OrganisationResponse response = new OrganisationService(log, orgRepo, service).remove(session, id);

        //then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }

    @Test
    void add_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData org = Mockito.mock(OrganisationData.class);

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, orgRepo, service).add(session, org));
    }

    @Test
    void getAll_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, orgRepo, service).getAll(session));
    }

    @Test
    void update_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData org = Mockito.mock(OrganisationData.class);
        String id = "1";

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, orgRepo, service).update(session, id, org));
    }

    @Test
    void remove_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        String id = "1";

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, orgRepo, service).remove(session, id));
    }

}