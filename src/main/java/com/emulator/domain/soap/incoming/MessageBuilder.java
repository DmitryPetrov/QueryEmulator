package com.emulator.domain.soap.incoming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Component ("IncomingMessageBuilder")
class MessageBuilder {

    @Autowired
    private DocumentBuilder docBuilder;

    String build(IncomingData data) {
        return buildIncomingRequest(data);
    }

    private String buildIncomingRequest(IncomingData data) {
        Document doc = docBuilder.newDocument();

        Element request = doc.createElement(data.REQUEST_NODE_NAME);
        doc.appendChild(request);

        createAttribute(request, data.REQUEST_ID_ATTR_NAME, data.getAttrRequestId());
        createAttribute(request, data.VERSION_ATTR_NAME, data.getAttrVersion());
        createAttribute(request, data.SENDER_ATTR_NAME, data.getAttrSender());
        createAttribute(request, data.RECEIVER_ATTR_NAME, data.getAttrReceiver());
        createAttribute(request, data.UPG_NAMESPACE_NAME, data.getNamespaceUpg());
        createAttribute(request, data.XSI_NAMESPACE_NAME, data.getNamespaceXsi());

        Element incoming = doc.createElement(data.INCOMING_NODE_NAME);
        request.appendChild(incoming);

        createAttribute(incoming, data.STATE_REQUEST_ATTR_NAME, data.getAttrStateRequest());
        createAttribute(incoming, data.INCOMING_ID_ATTR_NAME, data.getAttrIncomingId());
        createAttribute(incoming, data.TIMESTAMP_ATTR_NAME, data.getAttrTimestamp());

        Element docTypes = doc.createElement(data.DOC_TYPES_NODE_NAME);
        incoming.appendChild(docTypes);

        for (String item: data.getDocTypes()) {
            createTextElement(docTypes, data.DOC_TYPE_NODE_NAME, item);
        }

        //CDATASection cdata = doc.createCDATASection(toString(request));

        return toString(request);
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
