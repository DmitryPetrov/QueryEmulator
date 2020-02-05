package com.emulator.service;

import com.emulator.domain.frontend.response.ResponseBodyData;
import com.emulator.domain.soap.SoapClient;
import com.emulator.domain.soap.requests.authorization.AppUser;
import com.emulator.domain.soap.requests.authorization.AppUserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class AuthorizationService {

    private static Logger log;
    private SoapClient soapClient;

    /*
        Constructor for tests
    */
    public AuthorizationService(Logger logger, SoapClient soapClient) {
        this.log = logger;
        this.soapClient = soapClient;
    }

    @Autowired
    public AuthorizationService(SoapClient soapClient) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.soapClient = soapClient;
    }

    public ResponseBodyData authorization(HttpSession session, AppUserData data) throws IOException {
        log.debug("AuthorizationService: authorization data=" + data);
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) {
            data.check();
            user = soapClient.authorization(data);
            log.debug("AuthorizationService: User was authorized. User=" + user);
            session.setAttribute("user", user);
        } else {
            log.debug("AuthorizationService: User already authorized. User=" + user);
        }
        return getAuthorizationSucceedResponse(user);
    }

    private ResponseBodyData getAuthorizationSucceedResponse(AppUser user) {
        ResponseBodyData result = new ResponseBodyData();
        result.setStatus("OK");
        result.setMessage("Authorization succeed. Session id=" + user.getSessionId());
        return result;
    }
}
