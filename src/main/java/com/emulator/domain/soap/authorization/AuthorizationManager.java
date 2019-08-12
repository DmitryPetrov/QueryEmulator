package com.emulator.domain.soap.authorization;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.authorization.login.ClientAuthData;
import com.emulator.domain.soap.authorization.login.ClientAuthDataBuilder;
import com.emulator.domain.soap.authorization.login.LoginResult;
import com.emulator.domain.soap.authorization.prelogin.PreLoginResult;
import com.emulator.domain.soap.com.bssys.sbns.upg.*;
import com.emulator.exception.SOAPServerLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;
import java.io.IOException;
import java.util.List;

@Component
public class AuthorizationManager {
    @Autowired
    ObjectFactory factory;

    @Autowired
    WebServiceTemplate webServiceTemplate;

    @Autowired
    private List<String> soapMassageTrace;

    @Autowired
    ClientAuthDataBuilder clientAuthDataBuilder;

    public AppUser authorization(AppUser user) throws IOException {
        PreLoginResult preLoginResult = callPreLogin(user);

        ClientAuthData authData = clientAuthDataBuilder.build(user, preLoginResult);
        LoginResult loginResult = callLogin(user, preLoginResult, authData);
        AppUser authorizedUser = new AppUser(user, loginResult.getSessionId());

        return authorizedUser;
    }

    private PreLoginResult callPreLogin(AppUser user) {
        PreLogin request = factory.createPreLogin();
        request.setUserLogin(user.getUserName());
        JAXBElement<PreLogin> preLoginElement = factory.createPreLogin(request);
        JAXBElement<PreLoginResponse> preLoginResponseElement;

        preLoginResponseElement = (JAXBElement<PreLoginResponse>) webServiceTemplate
                .marshalSendAndReceive(preLoginElement);
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

    private LoginResult callLogin(AppUser user, PreLoginResult preLoginResult, ClientAuthData authData) throws
            SOAPServerLoginException {
        Login request = factory.createLogin();
        request.setUserLogin(user.getUserName());
        request.setPreloginId(preLoginResult.getPreLoginIdString());
        List<byte[]> clientAuthData = request.getClientAuthData();
        clientAuthData.add(authData.getPasswordHash());
        clientAuthData.add(authData.getExtPasswordData());
        JAXBElement<Login> loginElement = factory.createLogin(request);
        JAXBElement<LoginResponse> loginResponseElement;

        loginResponseElement = (JAXBElement<LoginResponse>) webServiceTemplate
                .marshalSendAndReceive(loginElement);
        LoginResponse response = loginResponseElement.getValue();

        return getLoginResult(response);
    }

    private LoginResult getLoginResult(LoginResponse response) throws SOAPServerLoginException {
        String responseStr = response.getReturn();
        if (responseStr.equals("BAD_CREDENTIALS")) {
            String soapMessages = "";
            for (String message : soapMassageTrace) {
                soapMessages += ("\n" + message);
            }
            soapMassageTrace.clear();

            String exceptionMessage = "";
            exceptionMessage += responseStr;
            exceptionMessage += "\n>>>>SAOP Messages:";
            exceptionMessage += soapMessages;

            SOAPServerLoginException exception = new SOAPServerLoginException(exceptionMessage);
            exception.setSoapMessages(soapMessages);
            exception.setSoapResponse(responseStr);
            throw exception;
        }

        LoginResult loginResult = new LoginResult();
        loginResult.setSessionId(responseStr);
        return loginResult;
    }

}
