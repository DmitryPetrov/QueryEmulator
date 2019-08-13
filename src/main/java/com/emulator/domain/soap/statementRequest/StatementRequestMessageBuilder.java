package com.emulator.domain.soap.statementrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;

@Component
public class StatementRequestMessageBuilder {

    private static final String REQUEST_NODE = "upg:Request";
    private static final String MODELS_NODE = "upg:Models";
    private static final String MODEL_NODE = "upg:Model";
    private static final String STATEMENT_REQUEST_NODE = "StatementRequest";
    private static final String BANK_MESSAGE_NODE = "bankMessage";
    private static final String DOC_DATE_NODE = "docDate";
    private static final String DOC_ID_NODE = "docId";
    private static final String DOC_NUMBER_NODE = "docNumber";
    private static final String FROM_DATE_NODE = "fromDate";
    private static final String ORG_ID_NODE = "orgId";
    private static final String ORG_INN_NODE = "orgInn";
    private static final String ORG_NAME_NODE = "orgName";
    private static final String TO_DATE_NODE = "toDate";
    private static final String ACCOUNTS_NODE = "accounts";
    private static final String ACC_NODE = "Acc";
    private static final String ACCOUNT_NODE = "account";
    private static final String BANK_BIC_NODE = "bankBIC";
    private static final String BANK_NAME_NODE = "bankName";

    private static final String REQUEST_ID_ATTRIBUTE = "requestId";
    private static final String VERSION_ATTRIBUTE = "version";
    private static final String XMLNS_ATTRIBUTE = "xmlns";

    private static final String UPG_NAMESPACE = "xmlns:upg";
    private static final String UPG_RAIF_NAMESPACE = "xmlns:upgRaif";
    private static final String XSI_NAMESPACE = "xmlns:xsi";


    @Autowired
    private DocumentBuilder docBuilder;

    public String build(StatementRequestData data) {
        return buildStatementRequest(data);
    }

    private String buildStatementRequest(StatementRequestData data) {
        Document doc = docBuilder.newDocument();

        Element requestElement = doc.createElement(REQUEST_NODE);
        doc.appendChild(requestElement);

        createAttribute(requestElement, UPG_NAMESPACE, data.requestNameSpaceUpgValue);
        createAttribute(requestElement, UPG_RAIF_NAMESPACE, data.requestNameSpaceUpgRaifValue);
        createAttribute(requestElement, XSI_NAMESPACE, data.requestNameSpaceXsiValue);
        createAttribute(requestElement, REQUEST_ID_ATTRIBUTE, data.requestAttrRequestId);
        createAttribute(requestElement, VERSION_ATTRIBUTE, data.requestAttrVersion);

        Element modelsElement = doc.createElement(MODELS_NODE);
        requestElement.appendChild(modelsElement);

        Element modelElement = doc.createElement(MODEL_NODE);
        modelsElement.appendChild(modelElement);

        String statementRequestElement = buildElementStatementRequest(modelElement, data);
        modelElement.appendChild(doc.createTextNode(statementRequestElement));

        return toString(requestElement);
    }

    private String buildElementStatementRequest(Element parentElement, StatementRequestData data) {
        Document doc = parentElement.getOwnerDocument();

        Element statementRequestElement = doc.createElement(STATEMENT_REQUEST_NODE);

        createAttribute(statementRequestElement, XMLNS_ATTRIBUTE, data.statementRequestAttrXmlns);

        Element bankMessageElement = doc.createElement(BANK_MESSAGE_NODE);
        statementRequestElement.appendChild(bankMessageElement);

        createTextElement(statementRequestElement, DOC_DATE_NODE, data.getDocDate());
        createTextElement(statementRequestElement, DOC_ID_NODE, data.getDocId());
        createTextElement(statementRequestElement, DOC_NUMBER_NODE, data.getDocNumber());
        createTextElement(statementRequestElement, FROM_DATE_NODE, data.getFromDate());
        createTextElement(statementRequestElement, ORG_ID_NODE, data.getOrgId());
        createTextElement(statementRequestElement, ORG_INN_NODE, data.getOrgInn());
        createTextElement(statementRequestElement, ORG_NAME_NODE, data.getOrgName());
        createTextElement(statementRequestElement, TO_DATE_NODE, data.getToDate());

        buildElementAccounts(statementRequestElement, data);

        return toString(statementRequestElement);
    }

    private void buildElementAccounts(Element parentElement, StatementRequestData data) {
        Document doc = parentElement.getOwnerDocument();

        Element accounts = doc.createElement(ACCOUNTS_NODE);
        parentElement.appendChild(accounts);

        List<StatementRequestDataAccount> accountList = data.getAccounts();
        for (StatementRequestDataAccount accountData : accountList) {
            Element acc = doc.createElement(ACC_NODE);
            accounts.appendChild(acc);

            createTextElement(acc, ACCOUNT_NODE, accountData.getAccount());
            createTextElement(acc, BANK_BIC_NODE, accountData.getBankBIC());
            createTextElement(acc, BANK_NAME_NODE, accountData.getBankName());
            createTextElement(acc, ORG_NAME_NODE, data.getOrgName());
        }
    }

    private void createAttribute(Element parentElement, String attributeName, String attributeValue) {
        Document doc = parentElement.getOwnerDocument();
        Attr attr = doc.createAttribute(attributeName);
        attr.setValue(attributeValue);
        parentElement.setAttributeNode(attr);
    }

    private void createTextElement(Element parentElement, String elementName, String elementValue) {
        Document doc = parentElement.getOwnerDocument();
        Text text = doc.createTextNode(elementValue);
        Element element = doc.createElement(elementName);
        element.appendChild(text);
        parentElement.appendChild(element);
    }


    @Autowired
    private Transformer transformer;

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
