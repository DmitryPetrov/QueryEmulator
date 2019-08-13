package com.emulator.domain.soap.getrequeststatus;

import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.com.bssys.sbns.upg.GetRequestStatusResponse;
import com.emulator.exception.SOAPServerGetRequestStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.DocumentBuilder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class GetRequestStatusResponseHandler {

    private static String MODEL_NODE_NAME = "upg:Model";

    private JAXBElement<GetRequestStatusResponse> response;

    public void setSoapResponse(JAXBElement<GetRequestStatusResponse> response) {
        this.response = response;
    }

    @Autowired
    DocumentBuilder docBuilder;

    private Document toDocument(String response) throws IOException, SAXException {
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        return docBuilder.parse(inputStream);
    }

    private String toString(JAXBElement<GetRequestStatusResponse> response){
        String result = "";
        for (String responseLine : response.getValue().getReturn()) {
            result += responseLine;
        }
        return result;
    }

    public GetRequestStatusResult parse() throws IOException, SAXException {
        String responseMessage = toString(response);
        checkErrors(responseMessage);

        Document document = toDocument(responseMessage);
        Element response = document.getDocumentElement();

        NamedNodeMap attributes = response.getAttributes();
        GetRequestStatusResult result = new GetRequestStatusResult();

        result.setNamespaceUpg(getAttrValue(attributes, result.NAMESPACE_UPG_NAME));
        result.setNamespaceUpgRussia(getAttrValue(attributes, result.NAMESPACE_UPG_RUSSIA_NAME));
        result.setNamespaceXsi(getAttrValue(attributes, result.NAMESPACE_XSI_NAME));
        result.setAttrCreateTime(getAttrValue(attributes, result.ATTR_CREATE_TIME_NAME));
        result.setAttrRequestId(getAttrValue(attributes, result.ATTR_REQUEST_ID_NAME));
        result.setAttrResponseId(getAttrValue(attributes, result.ATTR_RESPONSE_ID_NAME));
        result.setAttrSender(getAttrValue(attributes, result.ATTR_SENDER_NAME));
        result.setAttrVersion(getAttrValue(attributes, result.ATTR_VERSION_NAME));

        Node model = document.getDocumentElement().getElementsByTagName(MODEL_NODE_NAME).item(0);
        Document document2 = toDocument(model.getTextContent());

        GetRequestStatusResultStateResponse getRequestStatusResultStateResponse = result.getGetRequestStatusResultStateResponse();

        getRequestStatusResultStateResponse.setAttrXmlns(getAttrValue(document2.getDocumentElement().getAttributes(), getRequestStatusResultStateResponse.ATTR_XMLNS_NAME ));
        getRequestStatusResultStateResponse.setBankMessage(getNodeValue(document2, getRequestStatusResultStateResponse.BANK_MESSAGE_NAME));
        getRequestStatusResultStateResponse.setDocId(getNodeValue(document2, getRequestStatusResultStateResponse.DOC_ID_NAME));
        getRequestStatusResultStateResponse.setDocType(getNodeValue(document2, getRequestStatusResultStateResponse.DOC_TYPE_NAME));
        getRequestStatusResultStateResponse.setExtId(getNodeValue(document2, getRequestStatusResultStateResponse.EXT_ID_NAME));
        getRequestStatusResultStateResponse.setState(getNodeValue(document2, getRequestStatusResultStateResponse.STATE_NAME));

        return result;
    }

    private String getAttrValue(NamedNodeMap attributes, String attrName) {
        Node attribute = attributes.getNamedItem(attrName);
        return attribute.getTextContent();
    }

    private String getNodeValue(Document doc, String nodeName) {
        Node node = doc.getElementsByTagName(nodeName).item(0);
        return node.getTextContent();
    }

    private void checkErrors(String response) {
        if ((response.contains("NONEXISTENT SESSION"))
                || (response.contains("Error"))) {
            handleError(response);
        }
    }


    @Autowired
    private SoapMessageList soapMessageList;

    private void handleError(String response) {
        String exceptionMessage = response;
        exceptionMessage += "\n>>>>SAOP Messages:";
        exceptionMessage += soapMessageList.getAsString();

        SOAPServerGetRequestStatusException exception = new SOAPServerGetRequestStatusException(exceptionMessage);
        exception.setSoapMessages(soapMessageList.getAsString());
        exception.setSoapResponse(response);
        throw exception;
    }
}
