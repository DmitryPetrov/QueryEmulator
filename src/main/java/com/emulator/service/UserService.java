package com.emulator.service;

import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService {

    public AppUser getUser(HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) {
            throw new UserIsNotAuthorizedException("User is not authorized");
        }
        return user;
    }

    public void authorizationCheck(HttpSession session) {
        getUser(session);
    }

}
