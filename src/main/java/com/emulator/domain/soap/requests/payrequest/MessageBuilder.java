package com.emulator.domain.soap.requests.payrequest;

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

@Component("PayRequestMessageBuilder")
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

    String build(PayRequestData data) {
        return buildPayRequest(data);
    }

    private String buildPayRequest(PayRequestData data) {
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

        String payRequestElement = buildElementPayRequest(modelElement, data);
        modelElement.appendChild(doc.createTextNode(payRequestElement));

        return toString(requestElement);
    }

    private String buildElementPayRequest(Element parentElement, PayRequestData data) {
        Document doc = parentElement.getOwnerDocument();

        Element payRequestElement = doc.createElement(data.PAY_REQUEST_NODE_NAME);

        createAttribute(payRequestElement, data.XMLNS_ATTR_NAME, data.attrXmlns);

        Element bankMessageElement = doc.createElement(data.BANK_MESSAGE_NODE_NAME);
        payRequestElement.appendChild(bankMessageElement);

        createTextElement(payRequestElement, data.ACCEPT_TERM_NODE_NAME, data.getAcceptTerm());
        createTextElement(payRequestElement, data.ACCOUNT_ID_NODE_NAME, data.getAccountId());
        createTextElement(payRequestElement, data.BANK_ACCEPT_DATE_NODE_NAME, data.getBankAcceptDate());
        createTextElement(payRequestElement, data.BANK_MESSAGE_NODE_NAME, data.getBankMessage());
        createTextElement(payRequestElement, data.BANK_MESSAGE_AUTHOR_NODE_NAME, data.getBankMessageAuthor());
        createTextElement(payRequestElement, data.DOC_DATE_NODE_NAME, data.getDocDate());
        createTextElement(payRequestElement, data.DOC_DISPATCH_DATE_NODE_NAME, data.getDocDispatchDate());
        createTextElement(payRequestElement, data.DOC_ID_NODE_NAME, data.getDocId());
        createTextElement(payRequestElement, data.DOC_NUMBER_NODE_NAME, data.getDocNumber());
        createTextElement(payRequestElement, data.DOCUMENT_SUM_NODE_NAME, data.getDocumentSum());
        createTextElement(payRequestElement, data.EXTERNAL_ID_NODE_NAME, data.getExternalId());
        createTextElement(payRequestElement, data.EXTERNAL_UPG_ID_NODE_NAME, data.getExternalUPGId());
        createTextElement(payRequestElement, data.LAST_MODIFY_DATE_NODE_NAME, data.getLastModifyDate());
        createTextElement(payRequestElement, data.MESSAGE_ONLY_FOR_BANK_NODE_NAME, data.getMessageOnlyForBank());
        createTextElement(payRequestElement, data.NEW_STATE_NODE_NAME, data.getNewState());
        createTextElement(payRequestElement, data.OPERATION_DATE_NODE_NAME, data.getOperationDate());
        createTextElement(payRequestElement, data.OPERATION_TYPE_NODE_NAME, data.getOperationType());
        createTextElement(payRequestElement, data.ORG_ID_NODE_NAME, data.getOrgId());
        createTextElement(payRequestElement, data.ORG_NAME_NODE_NAME, data.getOrgName());
        createTextElement(payRequestElement, data.PAYER_ACCOUNT_NODE_NAME, data.getPayerAccount());
        createTextElement(payRequestElement, data.PAYER_BANK_BIC_NODE_NAME, data.getPayerBankBic());
        createTextElement(payRequestElement, data.PAYER_BANK_CORR_ACCOUNT_NODE_NAME, data.getPayerBankCorrAccount());
        createTextElement(payRequestElement, data.PAYER_BANK_NAME_NODE_NAME, data.getPayerBankName());
        createTextElement(payRequestElement, data.PAYER_ID_NODE_NAME, data.getPayerId());
        createTextElement(payRequestElement, data.PAYER_INN_NODE_NAME, data.getPayerInn());
        createTextElement(payRequestElement, data.PAYER_NAME_NODE_NAME, data.getPayerName());
        createTextElement(payRequestElement, data.PAYMENT_CONDITION_NODE_NAME, data.getPaymentCondition());
        createTextElement(payRequestElement, data.PAYMENT_CONDITION_CODE_NODE_NAME, data.getPaymentConditionCode());
        createTextElement(payRequestElement, data.PAYMENT_KIND_NODE_NAME, data.getPaymentKind());
        createTextElement(payRequestElement, data.PAYMENT_KIND_CODE_NODE_NAME, data.getPaymentKindCode());
        createTextElement(payRequestElement, data.PAYMENT_PRIORITY_NODE_NAME, data.getPaymentPriority());
        createTextElement(payRequestElement, data.PAYMENT_PURPOSE_NODE_NAME, data.getPaymentPurpose());
        createTextElement(payRequestElement, data.QUEUE_DATE_NODE_NAME, data.getQueueDate());
        createTextElement(payRequestElement, data.RECEIVER_ACCOUNT_NODE_NAME, data.getReceiverAccount());
        createTextElement(payRequestElement, data.RECEIVER_BANK_BIC_NODE_NAME, data.getReceiverBankBic());
        createTextElement(payRequestElement, data.RECEIVER_BANK_CORR_ACCOUNT_NODE_NAME, data
                .getReceiverBankCorrAccount());
        createTextElement(payRequestElement, data.RECEIVER_BANK_NAME_NODE_NAME, data.getReceiverBankName());
        createTextElement(payRequestElement, data.RECEIVER_INN_NODE_NAME, data.getReceiverInn());
        createTextElement(payRequestElement, data.RECEIVER_NAME_NODE_NAME, data.getReceiverName());
        createTextElement(payRequestElement, data.RECIEVE_D_PAYER_BANK_NODE_NAME, data.getRecieveDPayerBank());
        createTextElement(payRequestElement, data.RESERV_23_NODE_NAME, data.getReserv23());
        createTextElement(payRequestElement, data.TEMPLATE_NODE_NAME, data.getTemplate());
        createTextElement(payRequestElement, data.UIP_NODE_NAME, data.getUip());
        createTextElement(payRequestElement, data.VAT_CALCULATION_RULE_NODE_NAME, data.getVatCalculationRule());
        createTextElement(payRequestElement, data.VAT_RATE_NODE_NAME, data.getVatRate());
        createTextElement(payRequestElement, data.VAT_SUM_NODE_NAME, data.getVatSum());

        buildElementPayRequestPart(payRequestElement, data);

        if (data.getSignCollection() != null) {
            Element signCollectionElement = singBuilder.build(payRequestElement, data.getSignCollection());
            payRequestElement.appendChild(signCollectionElement);
        }

        return toString(payRequestElement);
    }

    private void buildElementPayRequestPart(Element parentElement, PayRequestData data) {
        Document doc = parentElement.getOwnerDocument();

        Element payRequestPart = doc.createElement(data.PAY_REQUEST_NODE_NAME);
        parentElement.appendChild(payRequestPart);

        List<PayRequestPartData> partPaymentsList = data.getPartPayments();
        for (PayRequestPartData payRequestPartdata : partPaymentsList) {
            Element acc = doc.createElement(payRequestPartdata.PAY_REQUEST_PART_NODE_NAME);
            payRequestPart.appendChild(acc);

            createTextElement(acc, payRequestPartdata.ORDER_NUMBER_NODE_NAME, payRequestPartdata.getOrderNumber());
            createTextElement(acc, payRequestPartdata.ORDER_DATE_NODE_NAME, payRequestPartdata.getOrderDate());
            createTextElement(acc, payRequestPartdata.PAYMENT_BALANCE_NODE_NAME, payRequestPartdata.getPaymentBalance
                    ());
            createTextElement(acc, payRequestPartdata.PART_PAYMENT_SUM_NODE_NAME, payRequestPartdata
                    .getPartPaymentSum());
            createTextElement(acc, payRequestPartdata.STR_NUM_NODE_NAME, payRequestPartdata.getStrNum());
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
