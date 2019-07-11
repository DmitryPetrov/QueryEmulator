package com.emulator.domain;

import com.emulator.config.XmlMessagePrinter;
import com.emulator.domain.entity.AppUser;
import com.emulator.domain.login.ClientAuthDataBuilder;
import com.emulator.domain.login.LoginResult;
import com.emulator.domain.prelogin.PreLoginResult;
import com.emulator.domain.wsclient.com.bssys.sbns.upg.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SOAPClient extends WebServiceGatewaySupport {

    @Autowired
    ObjectFactory factory;

    @Autowired
    XmlMessagePrinter messagePrinter;

    public SOAPClient() {
        super();
    }

    public String authorization(String userLogin, String password) throws IOException {
        AppUser user = new AppUser(userLogin, password);

        PreLoginResult preLoginResult = callPreLogin(user);

        Map<String, String> authData = new ClientAuthDataBuilder().build(user, preLoginResult);

        LoginResult loginResult = callLogin(user, preLoginResult, authData);

        return preLoginResult.toString() + "<br>"+ authData.toString() + "<br>" + loginResult.getSessionId();
    }

    private PreLoginResult callPreLogin(AppUser user) {
        PreLogin request = factory.createPreLogin();
        request.setUserLogin(user.getUserName());

        JAXBElement<PreLogin> preLoginElement = factory.createPreLogin(request);
        JAXBElement<PreLoginResponse> preLoginResponseElement;
        preLoginResponseElement = (JAXBElement<PreLoginResponse>) getWebServiceTemplate()
                .marshalSendAndReceive(preLoginElement, messagePrinter);

        PreLoginResponse response = preLoginResponseElement.getValue();

        return getPreLoginResult(response);
    }

    private PreLoginResult getPreLoginResult(PreLoginResponse response) {
        List<byte[]> responseFields = response.getReturn();

        PreLoginResult result = new PreLoginResult();
        result.setSalt(responseFields.get(0));
        result.setBytesFromServer(responseFields.get(1));
        result.setLegacySalt(responseFields.get(2));
        result.setPreLoginId(responseFields.get(3));
        result.setUserHash(responseFields.get(4));

        return result;
    }

    private LoginResult callLogin(AppUser user, PreLoginResult preLoginResult, Map<String, String> authData) {
        Login request = factory.createLogin();
        request.setUserLogin(user.getUserName());
        request.setPreloginId(preLoginResult.getPreLoginIdString());
        List<byte[]> clientAuthData = request.getClientAuthData();
        clientAuthData.add(authData.get("extPasswordData").getBytes());
        clientAuthData.add(authData.get("passwordHash").getBytes());

        JAXBElement<Login> loginElement = factory.createLogin(request);
        JAXBElement<LoginResponse> loginResponseElement;
        loginResponseElement = (JAXBElement<LoginResponse>) getWebServiceTemplate()
                .marshalSendAndReceive(loginElement, messagePrinter);

        LoginResponse response = loginResponseElement.getValue();

        return getLoginResult(response);
    }

    private LoginResult getLoginResult(LoginResponse response) {
        LoginResult loginResult = new LoginResult();

        loginResult.setSessionId(response.getReturn());

        return loginResult;
    }
}
