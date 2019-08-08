package com.emulator.config;

import com.emulator.domain.soap.com.bssys.sbns.upg.ObjectFactory;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import com.sun.xml.bind.marshaller.NoEscapeHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import javax.net.ssl.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Import(WebConfig.class)
public class Config {

    @Bean
    Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("com.emulator.domain.soap.com.bssys.sbns.upg");

        Map<String, Object> marshallerProperties = new HashMap<>();
        CharacterEscapeHandler escapeHandler = NoEscapeHandler.theInstance;
        marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshallerProperties.put("com.sun.xml.bind.marshaller.CharacterEscapeHandler", escapeHandler);
        jaxb2Marshaller.setMarshallerProperties(marshallerProperties);

        try {
            jaxb2Marshaller.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jaxb2Marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller, @Value("${soapServer.address}") String
            serverAddress) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setDefaultUri(serverAddress);
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        //webServiceTemplate.setTransformerFactoryClass();

        ClientInterceptor[] interceptors = new ClientInterceptor[]{interceptor()};
        webServiceTemplate.setInterceptors(interceptors);

        disableSslVerification();

        return webServiceTemplate;
    }

    @Bean
    public WebServiceTemplateInterceptor interceptor() {
        return new WebServiceTemplateInterceptor();
    }

    @Bean
    public ObjectFactory objectFactory() {
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
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "Models");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
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
        return docBuilder;
    }

    @Bean(name = "soapMessageTrace")
    public List<String> soapMessageTrace() {
        return new ArrayList<>();
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
