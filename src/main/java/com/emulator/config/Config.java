package com.emulator.config;

import com.emulator.controller.AuthorizationController;
import com.emulator.domain.soap.com.bssys.sbns.upg.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import javax.net.ssl.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
@Import(WebConfig.class)
public class Config {

    private static Logger log = LoggerFactory.getLogger(AuthorizationController.class);

    @Bean
    Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("com.emulator.domain.soap.com.bssys.sbns.upg");

        log.debug("Jaxb2Marshaller was built");
        return jaxb2Marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller, @Value("${soapServer.address}") String
            serverAddress) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setDefaultUri(serverAddress);
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);

        ClientInterceptor[] interceptors = new ClientInterceptor[]{interceptor()};
        webServiceTemplate.setInterceptors(interceptors);

        disableSslVerification();

        log.debug("WebServiceTemplate was built. Server address: " + serverAddress);
        return webServiceTemplate;
    }

    @Bean
    public WebServiceTemplateInterceptor interceptor() {
        log.debug("WebServiceTemplateInterceptor was built");
        return new WebServiceTemplateInterceptor();
    }

    @Bean
    public ObjectFactory objectFactory() {
        log.debug("ObjectFactory for soap requests/responses was built");
        return new ObjectFactory();
    }

    @Bean
    public Transformer transformer() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        log.debug("Transformer was built");
        return transformer;
    }

    @Bean
    public DocumentBuilder documentBuilder() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        log.debug("DocumentBuilder was built");
        return docBuilder;
    }

    @Bean
    public LoggingFilter requestLoggingFilter() {
        int maxPayloadLength = 10000;

        LoggingFilter loggingFilter = new LoggingFilter();
        loggingFilter.setMaxPayloadLength(maxPayloadLength);
        log.debug("LoggingFilter was built. Max payload length: " + maxPayloadLength);
        return loggingFilter;
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
