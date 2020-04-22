package com.emulator.service;

import com.emulator.domain.organisation.*;
import com.emulator.exception.UserIsNotAuthorizedException;
import com.emulator.repository.OrganisationRepository;
import com.emulator.repository.entity.Organisation;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

public class OrganisationServiceUnitTest {

    @Test
    void add_happyPath_succeedResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationTransformer transformer = Mockito.mock(OrganisationTransformer.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData org = Mockito.mock(OrganisationData.class);

        // when
        OrganisationResponse response = new OrganisationService(log, orgRepo, service, transformer).add(session, org);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }

    private List<Organisation> getOrgs() {
        List<Organisation> result = new ArrayList<>();
        result.add(Mockito.mock(Organisation.class));
        return result;
    }

    @Test
    void getAll_happyPath_succeedResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationTransformer transformer = Mockito.mock(OrganisationTransformer.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        List<Organisation> orgs = getOrgs();

        Mockito.when(orgRepo.findAll()).thenReturn(orgs);

        // when
        OrganisationResponse response = new OrganisationService(log, orgRepo, service, transformer).getAll(session);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Mockito.verify(transformer).transform(any(Organisation.class));
    }

    @Test
    void update_happyPath_succeedResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationTransformer transformer = Mockito.mock(OrganisationTransformer.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData data = Mockito.mock(OrganisationData.class);
        String id = "1";

        // when
        OrganisationResponse response = new OrganisationService(log, orgRepo, service, transformer)
                .update(session, id, data);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }

    @Test
    void remove_happyPath_succeedResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationTransformer transformer = Mockito.mock(OrganisationTransformer.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        String id = "1";

        // when
        OrganisationResponse response = new OrganisationService(log, orgRepo, service, transformer).remove(session, id);

        //then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }

    @Test
    void add_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationTransformer transformer = Mockito.mock(OrganisationTransformer.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData org = Mockito.mock(OrganisationData.class);

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, orgRepo, service, transformer).add(session, org));
    }

    @Test
    void getAll_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationTransformer transformer = Mockito.mock(OrganisationTransformer.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, orgRepo, service, transformer).getAll(session));
    }

    @Test
    void update_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationTransformer transformer = Mockito.mock(OrganisationTransformer.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData org = Mockito.mock(OrganisationData.class);
        String id = "1";

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, orgRepo, service, transformer).update(session, id, org));
    }

    @Test
    void remove_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationTransformer transformer = Mockito.mock(OrganisationTransformer.class);
        OrganisationRepository orgRepo = Mockito.mock(OrganisationRepository.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        String id = "1";

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, orgRepo, service, transformer).remove(session, id));
    }

}