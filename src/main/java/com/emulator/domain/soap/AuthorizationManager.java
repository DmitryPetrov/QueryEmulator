package com.emulator.domain.soap;

import com.emulator.config.XmlMessagePrinter;
import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.com.bssys.sbns.upg.*;
import com.emulator.domain.soap.exception.BadCredentialsLoginException;
import com.emulator.domain.soap.exception.SOAPServerLoginException;
import com.emulator.domain.soap.login.ClientAuthData;
import com.emulator.domain.soap.login.ClientAuthDataBuilder;
import com.emulator.domain.soap.login.LoginResult;
import com.emulator.domain.soap.prelogin.PreLoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    XmlMessagePrinter messagePrinter;

    @Autowired
    WebServiceTemplate webServiceTemplate;

    @Autowired
    private List<String> soapMassageTrace;

    @Autowired
    ClientAuthDataBuilder clientAuthDataBuilder;

    public AppUser authorization(String userName, String password) throws SOAPServerLoginException {
        AppUser user = getUser(userName, password);
        PreLoginResult preLoginResult = callPreLogin(user);

        try {
            ClientAuthData authData = clientAuthDataBuilder.build(user, preLoginResult);
            LoginResult loginResult = callLogin(user, preLoginResult, authData);
            user.setSessionId(loginResult);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadCredentialsLoginException e) {
            SOAPServerLoginException soapException = new SOAPServerLoginException(e.getMessage());
            soapException.setSoapMessages(e.getSoapMessages());
            throw soapException;
        }

        return user;
    }

    @Autowired
    @Qualifier("defaultUser")
    private AppUser defaultUser;

    private AppUser getUser(String userName, String password) {
        if((userName == null) || (password == null)) {
            return defaultUser;
        }
        if((userName.equals("(initialState)")) && (password.equals("(initialState)"))) {
            return defaultUser;
        }

        return new AppUser(userName, password);
    }

    private PreLoginResult callPreLogin(AppUser user) {
        PreLogin request = factory.createPreLogin();
        request.setUserLogin(user.getUserName());
        JAXBElement<PreLogin> preLoginElement = factory.createPreLogin(request);
        JAXBElement<PreLoginResponse> preLoginResponseElement;

        preLoginResponseElement = (JAXBElement<PreLoginResponse>) webServiceTemplate
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

    private LoginResult callLogin(AppUser user, PreLoginResult preLoginResult, ClientAuthData authData) throws
            BadCredentialsLoginException {
        Login request = factory.createLogin();
        request.setUserLogin(user.getUserName());
        request.setPreloginId(preLoginResult.getPreLoginIdString());
        List<byte[]> clientAuthData = request.getClientAuthData();
        clientAuthData.add(authData.getPasswordHash());
        clientAuthData.add(authData.getExtPasswordData());
        JAXBElement<Login> loginElement = factory.createLogin(request);
        JAXBElement<LoginResponse> loginResponseElement;

        loginResponseElement = (JAXBElement<LoginResponse>) webServiceTemplate
                .marshalSendAndReceive(loginElement, messagePrinter);
        LoginResponse response = loginResponseElement.getValue();

        return getLoginResult(response);
    }

    private LoginResult getLoginResult(LoginResponse response) throws BadCredentialsLoginException {
        String responseStr = response.getReturn();
        if (responseStr.equals("BAD_CREDENTIALS")) {
            String exceptionMessage = responseStr;

            String soapMessages = "";
            for (String message: soapMassageTrace) {
                soapMessages += ("\n" + message);
            }

            exceptionMessage += "\n>>>>SAOP Messages:" + soapMessages;

            soapMassageTrace.clear();

            BadCredentialsLoginException e = new BadCredentialsLoginException(exceptionMessage);
            e.setSoapMessages(soapMessages);

            throw e;
        }

        LoginResult loginResult = new LoginResult();
        loginResult.setSessionId(responseStr);
        return loginResult;
    }

}
