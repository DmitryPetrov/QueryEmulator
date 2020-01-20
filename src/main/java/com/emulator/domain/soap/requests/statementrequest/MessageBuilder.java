package com.emulator.domain.soap.requests.statementrequest;

import com.emulator.domain.soap.signcollection.SignCollectionMessageBuilder;
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

@Component("StatementRequestMessageBuilder")
public class MessageBuilder {

    private DocumentBuilder docBuilder;
    private SignCollectionMessageBuilder singBuilder;
    private Transformer transformer;

    @Autowired
    public MessageBuilder(DocumentBuilder docBuilder, SignCollectionMessageBuilder singBuilder,
                          Transformer transformer) {
        this.docBuilder = docBuilder;
        this.singBuilder = singBuilder;
        this.transformer = transformer;
    }

    String build(StatementRequestData data) {
        return buildStatementRequest(data);
    }

    private String buildStatementRequest(StatementRequestData data) {
        Document doc = docBuilder.newDocument();

        Element requestElement = doc.createElement(data.REQUEST_NODE_NAME);
        doc.appendChild(requestElement);

        createAttribute(requestElement, data.UPG_NAMESPACE_NAME, data.namespaceUpg);
        createAttribute(requestElement, data.UPG_RAIF_NAMESPACE_NAME, data.namespaceUpgRaif);
        createAttribute(requestElement, data.XSI_NAMESPACE_NAME, data.namespaceXsi);
        createAttribute(requestElement, data.REQUEST_ID_ATTR_NAME, data.attrRequestId);
        createAttribute(requestElement, data.VERSION_ATTR_NAME, data.attrVersion);

        Element modelsElement = doc.createElement(data.MODELS_NODE_NAME);
        requestElement.appendChild(modelsElement);

        Element modelElement = doc.createElement(data.MODEL_NODE_NAME);
        modelsElement.appendChild(modelElement);

        String statementRequestElement = buildElementStatementRequest(modelElement, data);
        modelElement.appendChild(doc.createTextNode(statementRequestElement));

        return toString(requestElement);
    }

    private String buildElementStatementRequest(Element parentElement, StatementRequestData data) {
        Document doc = parentElement.getOwnerDocument();

        Element statementRequestElement = doc.createElement(data.STATEMENT_REQUEST_NODE_NAME);

        createAttribute(statementRequestElement, data.XMLNS_ATTR_NAME, data.attrXmlns);

        Element bankMessageElement = doc.createElement(data.BANK_MESSAGE_NODE_NAME);
        statementRequestElement.appendChild(bankMessageElement);

        createTextElement(statementRequestElement, data.DOC_DATE_NODE_NAME, data.getDocDate());
        createTextElement(statementRequestElement, data.DOC_ID_NODE_NAME, data.getDocId());
        createTextElement(statementRequestElement, data.DOC_NUMBER_NODE_NAME, data.getDocNumber());
        createTextElement(statementRequestElement, data.FROM_DATE_NODE_NAME, data.getFromDate());
        createTextElement(statementRequestElement, data.ORG_ID_NODE_NAME, data.getOrgId());
        createTextElement(statementRequestElement, data.ORG_INN_NODE_NAME, data.getOrgInn());
        createTextElement(statementRequestElement, data.ORG_NAME_NODE_NAME, data.getOrgName());
        createTextElement(statementRequestElement, data.TO_DATE_NODE_NAME, data.getToDate());

        buildElementAccounts(statementRequestElement, data);

        if (data.getSignCollection() != null) {
            Element signCollectionElement = singBuilder.build(statementRequestElement, data.getSignCollection());
            statementRequestElement.appendChild(signCollectionElement);
        }

        return toString(statementRequestElement);
    }

    private void buildElementAccounts(Element parentElement, StatementRequestData data) {
        Document doc = parentElement.getOwnerDocument();

        Element accounts = doc.createElement(data.ACCOUNTS_NODE_NAME);
        parentElement.appendChild(accounts);

        List<AccData> accountList = data.getAccounts();
        for (AccData accountData : accountList) {
            Element acc = doc.createElement(data.ACC_NODE_NAME);
            accounts.appendChild(acc);

            createTextElement(acc, accountData.ACCOUNT_NODE_NAME, accountData.getAccount());
            createTextElement(acc, accountData.BANK_BIC_NODE_NAME, accountData.getBankBIC());
            createTextElement(acc, accountData.BANK_NAME_NODE_NAME, accountData.getBankName());
            createTextElement(acc, data.ORG_NAME_NODE_NAME, data.getOrgName());
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
