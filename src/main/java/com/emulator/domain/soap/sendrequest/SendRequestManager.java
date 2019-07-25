package com.emulator.domain.soap.sendrequest;

import com.emulator.config.XmlMessagePrinter;
import com.emulator.domain.entity.SendRequestRequest1Data;
import com.emulator.domain.soap.com.bssys.sbns.upg.*;
import com.emulator.domain.soap.exception.BadCredentialsLoginException;
import com.emulator.domain.soap.exception.SOAPServerSendRequestRequest1Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;

@Component
public class SendRequestManager {
    @Autowired
    ObjectFactory factory;

    @Autowired
    XmlMessagePrinter messagePrinter;

    @Autowired
    WebServiceTemplate webServiceTemplate;

    @Autowired
    private List<String> soapMassageTrace;

    public void request1(SendRequestRequest1Data data) throws SOAPServerSendRequestRequest1Exception {
        SendRequests request = factory.createSendRequests();
        request.setSessionId("1");
        List<String> requestData = request.getRequests();

        requestData.add(buildXML());

        JAXBElement<SendRequests> request1Element = factory.createSendRequests(request);
        JAXBElement<SendRequestsResponse> request1ResponseElement;

        request1ResponseElement = (JAXBElement<SendRequestsResponse>) webServiceTemplate
                .marshalSendAndReceive("http://localhost:8081/", request1Element, messagePrinter);
        SendRequestsResponse response = request1ResponseElement.getValue();

        String SOAPServerMessage = response.getReturn().get(0);
//        if (SOAPServerMessage.contains("NONEXISTENT SESSION"))
//        {
        String soapMessages = "";
        for (String message : soapMassageTrace) {
            message = message.replaceAll("&lt;", "<");
            message = message.replaceAll("&gt;", ">");
            soapMessages += ("\n" + message);
        }
        soapMassageTrace.clear();

        String exceptionMessage = "";
        exceptionMessage += SOAPServerMessage;
        exceptionMessage += "\n>>>>SAOP Messages:";
        exceptionMessage += soapMessages;

        SOAPServerSendRequestRequest1Exception exception = new SOAPServerSendRequestRequest1Exception(exceptionMessage);
        exception.setSoapMessages(soapMessages);
        throw exception;

//        }

        //return getPreLoginResult(response);

    }

    private String buildXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            Element requestElement = doc.createElement("upg:Request");
            doc.appendChild(requestElement);

            Attr upg = doc.createAttribute("xmlns:upg");
            upg.setValue("http://bssys.com/upg/request");
            requestElement.setAttributeNode(upg);

            Attr upgRaif = doc.createAttribute("xmlns:upgRaif");
            upgRaif.setValue("http://bssys.com/upg/request/raif");
            requestElement.setAttributeNode(upgRaif);

            Attr xsi = doc.createAttribute("xmlns:xsi");
            xsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
            requestElement.setAttributeNode(xsi);

            Attr requestId = doc.createAttribute("requestId");
            requestId.setValue("requestId");
            requestElement.setAttributeNode(requestId);

            Attr version = doc.createAttribute("version");
            version.setValue("version");
            requestElement.setAttributeNode(version);

            Element modelsElement = doc.createElement("upg:Models");
            requestElement.appendChild(modelsElement);

            Element modelElement = doc.createElement("upg:Model");
            modelsElement.appendChild(modelElement);

            Element statementRequestElement = doc.createElement("StatementRequest");
            modelElement.appendChild(statementRequestElement);

            Attr xmlns = doc.createAttribute("xmlns");
            xmlns.setValue("http://bssys.com/sbns/integration");
            statementRequestElement.setAttributeNode(xmlns);

            Element bankMessageElement = doc.createElement("bankMessage");
            statementRequestElement.appendChild(bankMessageElement);


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            //transformer.setOutputProperties(OutputKeys.);
            DOMSource source = new DOMSource(requestElement);

            StringWriter rootStr = new StringWriter();
            StreamResult result = new StreamResult(rootStr);

            transformer.transform(source, result);







            Document cdataWrapper = docBuilder.newDocument();
            CDATASection cdata = cdataWrapper.createCDATASection(rootStr.toString());

            DOMSource cdataSource = new DOMSource(cdata);

            StringWriter cdataWriter = new StringWriter();
            StreamResult cdataResult = new StreamResult(cdataWriter);

            transformer.transform(cdataSource, cdataResult);

            return cdataWriter.toString();

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

        return "xml builder error";
    }
}
