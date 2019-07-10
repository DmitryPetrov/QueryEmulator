package com.emulator.domain;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.login.ClientAuthDataBuilder;
import com.emulator.domain.prelogin.PreLoginResult;
import com.emulator.domain.wsclient.com.bssys.sbns.upg.ObjectFactory;
import com.emulator.domain.wsclient.com.bssys.sbns.upg.PreLogin;
import com.emulator.domain.wsclient.com.bssys.sbns.upg.PreLoginResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SOAPClient extends WebServiceGatewaySupport {

    public SOAPClient() {
        super();
    }

    public String authorization(String userLogin, String password) throws IOException {
        AppUser user = new AppUser(userLogin, password);

        PreLoginResult result = callPreLogin(user);

        Map<String, String> authData = new ClientAuthDataBuilder().build(user, result);

        return result.toString() + authData.toString();
    }

    private PreLoginResult callPreLogin(AppUser user) {
        ObjectFactory factory = new ObjectFactory();
        PreLogin request = factory.createPreLogin();
        request.setUserLogin(user.getUserName());

        JAXBElement<PreLogin> preLoginElement = factory.createPreLogin(request);
        JAXBElement<PreLoginResponse> preLoginResponseElement;
        preLoginResponseElement = (JAXBElement<PreLoginResponse>) getWebServiceTemplate()
                .marshalSendAndReceive("https://correqtst00:8443/sbns-upg/upg", preLoginElement);

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
}
