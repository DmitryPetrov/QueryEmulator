package com.emulator.config;

import com.emulator.domain.SOAPClient;
import com.emulator.domain.entity.AppUser;
import com.emulator.domain.wsclient.com.bssys.sbns.upg.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
@Import(WebConfig.class)
public class Config {

    @Bean
    Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("com.emulator.domain.wsclient.com.bssys.sbns.upg");

        return jaxb2Marshaller;
    }

    @Bean
    public SOAPClient soapClient(Jaxb2Marshaller marshaller) {
        SOAPClient client = new SOAPClient();
        client.setDefaultUri("https://correqtst00:8443/sbns-upg/upg");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        ClientInterceptor[] interceptors = new ClientInterceptor[]{interceptor()};
        client.setInterceptors(interceptors);

        disableSslVerification();

        return client;
    }

    @Bean
    public SoapClientInterceptor interceptor() {
        return new SoapClientInterceptor();
    }

    @Bean
    public ObjectFactory objectFactory() {
        return new ObjectFactory();
    }

    @Bean
    public XmlMessagePrinter xmlMessagePrinter() {
        return new XmlMessagePrinter();
    }

    @Bean(name = "defaultUser")
    public AppUser defaultUser(@Value("${defaultUser.userName}") String userName,
                               @Value("${defaultUser.password}")String password) {
        return new AppUser(userName, password);
    }

    private void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new UnTrustworthyTrustManager()};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
