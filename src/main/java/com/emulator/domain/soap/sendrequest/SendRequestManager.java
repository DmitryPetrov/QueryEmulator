package com.emulator.domain.soap.sendrequest;

import com.emulator.config.XmlMessagePrinter;
import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.StatementRequestData;
import com.emulator.domain.soap.com.bssys.sbns.upg.ObjectFactory;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequests;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequestsResponse;
import com.emulator.domain.soap.exception.SOAPServerStatementRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.w3c.dom.*;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
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

    public void getStatementRequestResult(AppUser user, StatementRequestData data) throws SOAPServerStatementRequestException {
        SendRequests request = factory.createSendRequests();
        request.setSessionId(user.getSessionId());
        List<String> requestData = request.getRequests();

        requestData.add(buildStatementRequest(data));

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
            message = message.replaceAll("&lt;", "<br/>&lt;");
//            message = message.replaceAll("&gt;", ">");
            soapMessages += ("\n" + message);
        }
        soapMassageTrace.clear();

        String exceptionMessage = "";
        exceptionMessage += SOAPServerMessage;
        exceptionMessage += "\n>>>>SAOP Messages:";
        exceptionMessage += soapMessages;

        SOAPServerStatementRequestException exception = new SOAPServerStatementRequestException(exceptionMessage);
        exception.setSoapMessages(soapMessages);
        throw exception;

//        }

        //return getPreLoginResult(response);

    }

    @Autowired
    Transformer transformer;

    @Autowired
    DocumentBuilder docBuilder;

    private String buildStatementRequest(StatementRequestData data) {
        Element xml = buildStatementRequestXml(data);

        Document cdataWrapper = docBuilder.newDocument();
        CDATASection cdata = cdataWrapper.createCDATASection(fromDoc(xml));

        return fromDoc(cdata);
    }

    private Element buildStatementRequestXml(StatementRequestData data) {
        Document doc = docBuilder.newDocument();

        Element requestElement = doc.createElement("upg:Request");
        doc.appendChild(requestElement);

        Attr upg = doc.createAttribute(data.requestNameSpaceUpg);
        upg.setValue(data.requestNameSpaceUpgValue);
        requestElement.setAttributeNode(upg);

        Attr upgRaif = doc.createAttribute(data.requestNameSpaceUpgRaif);
        upgRaif.setValue(data.requestNameSpaceUpgRaifValue);
        requestElement.setAttributeNode(upgRaif);

        Attr xsi = doc.createAttribute(data.requestNameSpaceXsi);
        xsi.setValue(data.requestNameSpaceXsiValue);
        requestElement.setAttributeNode(xsi);

        Attr requestId = doc.createAttribute("requestId");
        requestId.setValue(data.requestAttrRequestId);
        requestElement.setAttributeNode(requestId);

        Attr version = doc.createAttribute("version");
        version.setValue(data.requestAttrVersion);
        requestElement.setAttributeNode(version);

        Element modelsElement = doc.createElement("upg:Models");
        requestElement.appendChild(modelsElement);

        Element modelElement = doc.createElement("upg:Model");
        modelsElement.appendChild(modelElement);

        Element statementRequestElement = doc.createElement("StatementRequest");
        modelElement.appendChild(statementRequestElement);

        Attr xmlns = doc.createAttribute("xmlns");
        xmlns.setValue(data.statementRequestAttrXmlns);
        statementRequestElement.setAttributeNode(xmlns);

        Element bankMessageElement = doc.createElement("bankMessage");
        statementRequestElement.appendChild(bankMessageElement);

        Element docDate = doc.createElement("docDate");
        docDate.appendChild(doc.createTextNode(data.getDocDate()));
        statementRequestElement.appendChild(docDate);

        Element docId = doc.createElement("docId");
        docId.appendChild(doc.createTextNode(data.getDocId()));
        statementRequestElement.appendChild(docId);

        Element docNumber = doc.createElement("docNumber");
        docNumber.appendChild(doc.createTextNode(data.getDocNumber()));
        statementRequestElement.appendChild(docNumber);

        Element fromDate = doc.createElement("fromDate");
        fromDate.appendChild(doc.createTextNode(data.getFromDate()));
        statementRequestElement.appendChild(fromDate);

        Element orgId = doc.createElement("orgId");
        orgId.appendChild(doc.createTextNode(data.getOrgId()));
        statementRequestElement.appendChild(orgId);

        Element orgInn = doc.createElement("orgInn");
        orgInn.appendChild(doc.createTextNode(data.getOrgInn()));
        statementRequestElement.appendChild(orgInn);

        Element orgName = doc.createElement("orgName");
        orgName.appendChild(doc.createTextNode(data.getOrgName()));
        statementRequestElement.appendChild(orgName);

        Element toDate = doc.createElement("toDate");
        toDate.appendChild(doc.createTextNode(data.getToDate()));
        statementRequestElement.appendChild(toDate);

        Element accounts = doc.createElement("accounts");
        statementRequestElement.appendChild(accounts);

        Element acc = doc.createElement("Acc");
        accounts.appendChild(acc);

        Element account = doc.createElement("account");
        account.appendChild(doc.createTextNode(data.getAccount()));
        acc.appendChild(account);

        Element bankBIC = doc.createElement("bankBIC");
        bankBIC.appendChild(doc.createTextNode(data.getBankBIC()));
        acc.appendChild(bankBIC);

        Element bankName = doc.createElement("bankName");
        bankName.appendChild(doc.createTextNode(data.getBankName()));
        acc.appendChild(bankName);

        acc.appendChild(orgName);

        return requestElement;
    }

    private String fromDoc(Node element) {
        DOMSource source = new DOMSource(element);
        StringWriter stringWriter = new StringWriter();
        StreamResult result = new StreamResult(stringWriter);
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return stringWriter.toString();
    }
}
