package com.emulator.domain.soap.requests.authorization;

import com.emulator.domain.soap.com.bssys.sbns.upg.*;
import com.emulator.domain.soap.requests.ErrorResponseHandler;
import com.emulator.domain.soap.requests.authorization.login.ClientAuthData;
import com.emulator.domain.soap.requests.authorization.login.ClientAuthDataBuilder;
import com.emulator.domain.soap.requests.authorization.login.LoginResult;
import com.emulator.domain.soap.requests.authorization.prelogin.PreLoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;
import java.io.IOException;
import java.util.List;

@Component
public class AuthorizationManager {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    private ClientAuthDataBuilder clientAuthDataBuilder;

    public AppUser authorization(AppUser user) throws IOException {
        PreLoginResult preLoginResult = runPreLogin(user);

        ClientAuthData authData = clientAuthDataBuilder.build(user, preLoginResult);
        LoginResult loginResult = runLogin(user, preLoginResult, authData);
        AppUser authorizedUser = new AppUser(user, loginResult.getSessionId());

        return authorizedUser;
    }

    private PreLoginResult runPreLogin(AppUser user) {
        JAXBElement<PreLogin> request = buildRequest(user);
        JAXBElement<PreLoginResponse> response;

        response = (JAXBElement<PreLoginResponse>) webServiceTemplate
                .marshalSendAndReceive(request);

        return getPreLoginResult(response);
    }

    @Autowired
    private ObjectFactory factory;

    private JAXBElement<PreLogin> buildRequest(AppUser user) {
        PreLogin request = factory.createPreLogin();
        request.setUserLogin(user.getUserName());

        return factory.createPreLogin(request);
    }

    private PreLoginResult getPreLoginResult(JAXBElement<PreLoginResponse> response) {
        List<byte[]> responseFields = response.getValue().getReturn();

        PreLoginResult result = new PreLoginResult();
        result.setSalt(responseFields.get(0));
        result.setBytesFromServer(responseFields.get(1));
        result.setLegacySalt(responseFields.get(2));
        result.setPreLoginId(responseFields.get(3));
        result.setUserHash(responseFields.get(4));

        return result;
    }

    private LoginResult runLogin(AppUser user, PreLoginResult preLoginResult, ClientAuthData authData) {
        JAXBElement<Login> request = buildRequest(user, preLoginResult, authData);
        JAXBElement<LoginResponse> response;

        response = (JAXBElement<LoginResponse>) webServiceTemplate
                .marshalSendAndReceive(request);

        return getLoginResult(response);
    }

    private JAXBElement<Login> buildRequest(AppUser user, PreLoginResult preLoginResult, ClientAuthData authData) {
        Login request = factory.createLogin();
        request.setUserLogin(user.getUserName());
        request.setPreloginId(preLoginResult.getPreLoginIdString());
        List<byte[]> clientAuthData = request.getClientAuthData();
        clientAuthData.add(authData.getPasswordHash());
        clientAuthData.add(authData.getExtPasswordData());

        return factory.createLogin(request);
    }

    @Autowired
    private ErrorResponseHandler errorHandler;

    private LoginResult getLoginResult(JAXBElement<LoginResponse> response) {
        String responseMessage = response.getValue().getReturn();

        errorHandler.check(responseMessage);

        LoginResult loginResult = new LoginResult();
        loginResult.setSessionId(responseMessage);
        return loginResult;
    }
}
