package com.emulator.service;

import com.emulator.domain.organisation.*;
import com.emulator.exception.UserIsNotAuthorizedException;
import com.emulator.repository.OrganisationRepository;
import com.emulator.repository.entity.Organisation;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

public class OrganisationServiceUnitTest {

    private Logger log;
    private UserService userService;
    private OrganisationTransformer transformer;
    private OrganisationRepository orgRepo;
    private HttpSession session;
    private OrganisationData orgData;
    private String id;

    @BeforeEach
    void before() {
        log = Mockito.mock(Logger.class);
        userService = Mockito.mock(UserService.class);
        transformer = Mockito.mock(OrganisationTransformer.class);
        orgRepo = Mockito.mock(OrganisationRepository.class);
        session = Mockito.mock(HttpSession.class);
        orgData = Mockito.mock(OrganisationData.class);
        id = "1";
    }

    @Test
    void add_happyPath_succeedResponse() throws Exception {
        // given
        OrganisationService orgService = new OrganisationService(log, orgRepo, userService, transformer);;

        // when
        OrganisationResponse response = orgService.add(session, orgData);

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
        OrganisationService orgService = new OrganisationService(log, orgRepo, userService, transformer);;
        List<Organisation> orgs = getOrgs();

        Mockito.when(orgRepo.findAll()).thenReturn(orgs);

        // when
        OrganisationResponse response = orgService.getAll(session);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Mockito.verify(transformer).transform(any(Organisation.class));
    }

    @Test
    void update_happyPath_succeedResponse() throws Exception {
        // given
        OrganisationService orgService = new OrganisationService(log, orgRepo, userService, transformer);;
        Organisation organisation = Mockito.mock(Organisation.class);
        Mockito.when(orgRepo.findById(Long.parseLong(id))).thenReturn(Optional.of(organisation));

        // when
        OrganisationResponse response = orgService.update(session, id, orgData);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }

    @Test
    void remove_happyPath_succeedResponse() throws Exception {
        // given
        OrganisationService orgService = new OrganisationService(log, orgRepo, userService, transformer);;

        // when
        OrganisationResponse response = orgService.remove(session, id);

        //then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
    }

    @Test
    void add_userIsNotAuthorized_exception() throws Exception {
        // given
        doThrow(new UserIsNotAuthorizedException("")).when(userService).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, orgRepo, userService, transformer).add(session, orgData));
    }

    @Test
    void getAll_userIsNotAuthorized_exception() throws Exception {
        // given
        OrganisationService orgService = new OrganisationService(log, orgRepo, userService, transformer);
        doThrow(new UserIsNotAuthorizedException("")).when(userService).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> orgService.getAll(session));
    }

    @Test
    void update_userIsNotAuthorized_exception() throws Exception {
        // given
        OrganisationService orgService = new OrganisationService(log, orgRepo, userService, transformer);
        String id = "1";

        doThrow(new UserIsNotAuthorizedException("")).when(userService).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> orgService.update(session, id, orgData));
    }

    @Test
    void remove_userIsNotAuthorized_exception() throws Exception {
        // given
        OrganisationService orgService = new OrganisationService(log, orgRepo, userService, transformer);
        String id = "1";

        doThrow(new UserIsNotAuthorizedException("")).when(userService).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, orgRepo, userService, transformer).remove(session, id));
    }

}