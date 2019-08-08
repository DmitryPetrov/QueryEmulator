package com.emulator.domain.soap.statementRequest;

import com.emulator.domain.entity.AppUser;
import com.emulator.domain.soap.com.bssys.sbns.upg.ObjectFactory;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequests;
import com.emulator.domain.soap.com.bssys.sbns.upg.SendRequestsResponse;
import com.emulator.domain.soap.exception.SOAPServerStatementRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
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
public class StatementRequestManager {
    @Autowired
    ObjectFactory factory;

    @Autowired
    WebServiceTemplate webServiceTemplate;

    @Autowired
    private List<String> soapMassageTrace;

    public StatementRequestResult runStatementRequest(AppUser user, StatementRequestData data) throws
            SOAPServerStatementRequestException {
        SendRequests request = factory.createSendRequests();
        request.setSessionId(user.getSessionId());
        List<String> requestData = request.getRequests();

        requestData.add(""/*buildStatementRequest(data)*/);

        WebServiceMessageCallback callbackCDATANumTelefono = new WebServiceMessageCallback() {
            public void doWithMessage(WebServiceMessage message) {

                //Recover the DOMSource dal message soap
                DOMSource domSource = (DOMSource)message.getPayloadSource();

                //recover set of child nodes of domsource
                NodeList nodeList = domSource.getNode().getChildNodes();

                //definisco il nome del tag da cercare
                String nameNumTel = "ns2:requests";//"ns2:sNumTel"; //in this example, but you can define another QName string to recover the target node (or nodes)
                Node nodeNumTel = null;
                for (int i = 0; i < nodeList.getLength(); i++) {
                    System.out.println("nodeList names");
                    System.out.println(nodeList.item(i).getNodeName());
                    //search of text node of this tag name
                    if (nodeList.item(i).getNodeName().compareTo(nameNumTel) == 0) {
                        nodeNumTel = nodeList.item(i);
                        break;
                    }
                }
                //recover the string value (in this case of telephone number)
                String valueNumTelefono = nodeNumTel.getTextContent();
                //clean of value of target text node
                nodeNumTel.setTextContent("");
                //define and inject of CDATA section, in this case encapsulate "+" character
                CDATASection cdata = nodeNumTel.getOwnerDocument().createCDATASection(buildStatementRequest(data));
                //create of new text node
                Text tnode = nodeNumTel.getOwnerDocument().createTextNode(valueNumTelefono);
                //append of CDATASection (in this point is possible to inject many CDATA section on various parts of string (very usefull)
                nodeNumTel.appendChild(cdata);
                nodeNumTel.appendChild(tnode);

                //done!
            }
        };

        JAXBElement<SendRequests> statementRequestElement = factory.createSendRequests(request);
        JAXBElement<SendRequestsResponse> statementRequestResponseElement = null;

        statementRequestResponseElement = (JAXBElement<SendRequestsResponse>) webServiceTemplate
                .marshalSendAndReceive(statementRequestElement, callbackCDATANumTelefono );


        SendRequestsResponse response = statementRequestResponseElement.getValue();

        return getStatementRequestResult(response);
    }

    private StatementRequestResult getStatementRequestResult(SendRequestsResponse response) throws
            SOAPServerStatementRequestException {
        StatementRequestResult result = new StatementRequestResult();

        String responseStr = "";

        for (String responseLine : response.getReturn()) {
            responseStr += responseLine;
        }

        checkErrors(responseStr);

        result.setRequestId(responseStr);
        return result;
    }

    private void checkErrors(String response) throws SOAPServerStatementRequestException {
        System.out.println("StatementRequest response: " + response);

        if (!((response.contains("NONEXISTENT SESSION")) |
                (response.contains("Error")))) {
            return;
        }

        String soapMessages = "";
        for (String message : soapMassageTrace) {
            message = message.replaceAll("&lt;", "<br/>&lt;");
            message = message.replaceAll("&gt;", ">");
            soapMessages += ("\n" + message);
        }
        soapMassageTrace.clear();

        String exceptionMessage = "";
        exceptionMessage += response;
        exceptionMessage += "\n>>>>SAOP Messages:";
        exceptionMessage += soapMessages;

        SOAPServerStatementRequestException exception = new SOAPServerStatementRequestException(exceptionMessage);
        exception.setSoapMessages(soapMessages);
        exception.setSoapResponse(response);
        throw exception;
    }

    @Autowired
    Transformer transformer;

    @Autowired
    DocumentBuilder docBuilder;

    private String buildStatementRequest(StatementRequestData data) {
        Element xml = buildElementRequest(data);

        Document cdataWrapper = docBuilder.newDocument();
        CDATASection cdata = cdataWrapper.createCDATASection(toString(xml));

        System.out.println("buildStatementRequest");
        System.out.println(toString(xml));

        return toString(xml);
    }

    private Element buildElementRequest(StatementRequestData data) {
        Document doc = docBuilder.newDocument();

        Element requestElement = doc.createElement("upg:Request");
        doc.appendChild(requestElement);

        createAttribute(doc, requestElement, data.requestNameSpaceUpg, data.requestNameSpaceUpgValue);
        createAttribute(doc, requestElement, data.requestNameSpaceUpgRaif, data.requestNameSpaceUpgRaifValue);
        createAttribute(doc, requestElement, data.requestNameSpaceXsi, data.requestNameSpaceXsiValue);
        createAttribute(doc, requestElement, "requestId", data.requestAttrRequestId);
        createAttribute(doc, requestElement, "version", data.requestAttrVersion);

//        Attr upg = doc.createAttribute(data.requestNameSpaceUpg);
//        upg.setValue(data.requestNameSpaceUpgValue);
//        requestElement.setAttributeNode(upg);
//
//        Attr upgRaif = doc.createAttribute(data.requestNameSpaceUpgRaif);
//        upgRaif.setValue(data.requestNameSpaceUpgRaifValue);
//        requestElement.setAttributeNode(upgRaif);
//
//        Attr xsi = doc.createAttribute(data.requestNameSpaceXsi);
//        xsi.setValue(data.requestNameSpaceXsiValue);
//        requestElement.setAttributeNode(xsi);
//
//        Attr requestId = doc.createAttribute("requestId");
//        requestId.setValue(data.requestAttrRequestId);
//        requestElement.setAttributeNode(requestId);
//
//        Attr version = doc.createAttribute("version");
//        version.setValue(data.requestAttrVersion);
//        requestElement.setAttributeNode(version);

        Element modelsElement = doc.createElement("upg:Models");
        requestElement.appendChild(modelsElement);

        Element modelElement = doc.createElement("upg:Model");
        modelsElement.appendChild(modelElement);

        String statementRequestElement = buildElementStatementRequest(doc, data);
        modelElement.appendChild(doc.createTextNode(statementRequestElement));

        return requestElement;
    }

    private void createAttribute(Document doc, Element element, String attributeName, String attributeValue) {
        Attr attr = doc.createAttribute(attributeName);
        attr.setValue(attributeValue);
        element.setAttributeNode(attr);
    }

    private String buildElementStatementRequest(Document doc, StatementRequestData data) {
        Element statementRequestElement = doc.createElement("StatementRequest");

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

        Element accounts = buildElementAccounts(doc, data);
        statementRequestElement.appendChild(accounts);

        return toString(statementRequestElement);
    }

    private Element buildElementAccounts(Document doc, StatementRequestData data) {
        Element accounts = doc.createElement("accounts");

        List<StatementRequestDataAccount> accountList = data.getAccounts();

        for (StatementRequestDataAccount accountData : accountList) {
            Element acc = doc.createElement("Acc");
            accounts.appendChild(acc);

            Element account = doc.createElement("account");
            account.appendChild(doc.createTextNode(accountData.getAccount()));
            acc.appendChild(account);

            Element bankBIC = doc.createElement("bankBIC");
            bankBIC.appendChild(doc.createTextNode(accountData.getBankBIC()));
            acc.appendChild(bankBIC);

            Element bankName = doc.createElement("bankName");
            bankName.appendChild(doc.createTextNode(accountData.getBankName()));
            acc.appendChild(bankName);

            Element orgName = doc.createElement("orgName");
            orgName.appendChild(doc.createTextNode(data.getOrgName()));
            acc.appendChild(orgName);
        }

        return accounts;
    }

    private String toString(Node element) {
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
