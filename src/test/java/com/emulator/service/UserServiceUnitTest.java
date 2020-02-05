package com.emulator.service;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

class UserServiceUnitTest {

    @Test
    void getUser_happyPath_appUser() throws Exception{
        // given
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUser user = Mockito.mock(AppUser.class);
        String attrName = "user";

        Mockito.when(session.getAttribute(eq(attrName))).thenReturn(user);

        // when
        AppUser result = new UserService().getUser(session);

        // then
        Assert.assertSame(user, result);
    }

    @Test
    void getUser_userNotAuthorized_exception() throws Exception{
        // given
        HttpSession session = Mockito.mock(HttpSession.class);
        String attrName = "user";

        Mockito.when(session.getAttribute(eq(attrName))).thenThrow(UserIsNotAuthorizedException.class);

        // then
        Assertions.assertThrows(
                UserIsNotAuthorizedException.class,
                () -> new UserService().getUser(session));
    }

    @Test
    void authorizationCheck_happyPath_nothing() throws Exception{
        // given
        HttpSession session = Mockito.mock(HttpSession.class);
        AppUser user = Mockito.mock(AppUser.class);
        String attrName = "user";

        Mockito.when(session.getAttribute(eq(attrName))).thenReturn(user);

        // when
        try {
            AppUser result = new UserService().getUser(session);
        } catch (Exception e) {

            // then
            Assert.assertTrue(false);
        }
    }
}