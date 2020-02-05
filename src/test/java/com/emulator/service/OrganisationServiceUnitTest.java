package com.emulator.service;

import com.emulator.domain.organisation.OrganisationData;
import com.emulator.domain.organisation.OrganisationPool;
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
        OrganisationPool pool = Mockito.mock(OrganisationPool.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData org = Mockito.mock(OrganisationData.class);

        // when
        OrganisationResponse response = new OrganisationService(log, pool, service).add(session, org);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Mockito.verify(service).authorizationCheck(session);
        Mockito.verify(org).check();
        Mockito.verify(pool).add(org);
    }

    @Test
    void getAll_happyPath_succeedResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationPool pool = Mockito.mock(OrganisationPool.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        List<OrganisationData> orgs = Mockito.mock(List.class);

        Mockito.when(pool.getAll()).thenReturn(orgs);

        // when
        OrganisationResponse response = new OrganisationService(log, pool, service).getAll(session);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Assert.assertSame(orgs, response.getOrganisations());
        Mockito.verify(service).authorizationCheck(session);
        Mockito.verify(pool).getAll();
    }

    @Test
    void update_happyPath_succeedResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationPool pool = Mockito.mock(OrganisationPool.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData data = Mockito.mock(OrganisationData.class);
        String id = "1";

        // when
        OrganisationResponse response = new OrganisationService(log, pool, service).update(session, id, data);

        // then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Mockito.verify(service).authorizationCheck(session);
        Mockito.verify(data).check();
        Mockito.verify(pool).update(Long.parseLong(id), data);
    }

    @Test
    void remove_happyPath_succeedResponse() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationPool pool = Mockito.mock(OrganisationPool.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        String id = "1";

        // when
        OrganisationResponse response = new OrganisationService(log, pool, service).remove(session, id);

        //then
        Assert.assertNotNull(response);
        Assert.assertEquals("OK", response.getStatus());
        Mockito.verify(service).authorizationCheck(session);
        Mockito.verify(pool).remove(Long.parseLong(id));
    }

    @Test
    void add_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationPool pool = Mockito.mock(OrganisationPool.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData org = Mockito.mock(OrganisationData.class);

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, pool, service).add(session, org));
    }

    @Test
    void getAll_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationPool pool = Mockito.mock(OrganisationPool.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, pool, service).getAll(session));
    }

    @Test
    void update_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationPool pool = Mockito.mock(OrganisationPool.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        OrganisationData org = Mockito.mock(OrganisationData.class);
        String id = "1";

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, pool, service).update(session, id, org));
    }

    @Test
    void remove_userIsNotAuthorized_exception() throws Exception {
        // given
        Logger log = Mockito.mock(Logger.class);
        UserService service = Mockito.mock(UserService.class);
        OrganisationPool pool = Mockito.mock(OrganisationPool.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        String id = "1";

        doThrow(new UserIsNotAuthorizedException("")).when(service).authorizationCheck(session);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new OrganisationService(log, pool, service).remove(session, id));
    }

}