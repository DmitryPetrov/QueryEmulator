package com.emulator.domain.soap.requests.getrequeststatus;

import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.com.bssys.sbns.upg.GetRequestStatusResponse;
import com.emulator.exception.ParameterIsNullException;
import com.emulator.exception.SoapServerGetRequestStatusException;
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
class ResponseHandler {
    private static String NOT_PROCESSED_YET = "NOT PROCESSED YET";

    private static String MODEL_NODE_NAME = "upg:Model";
    private static int MODEL_NODE_INDEX = 0;

    GetRequestStatusResult getResult(JAXBElement<GetRequestStatusResponse> response) throws IOException,
            SAXException {
        if (response == null) {
            throw new ParameterIsNullException("GetRequestStatusResponseHandler.response must not be 'null'");
        }
        String responseMessage = toString(response);
        return parse(responseMessage);
    }

    private GetRequestStatusResult parse(String responseMessage) throws IOException, SAXException {
        checkErrors(responseMessage);

        if (responseMessage.contains(NOT_PROCESSED_YET)) {
            GetRequestStatusResult result = new GetRequestStatusResult();
            result.setNotProcessedYet(true);
            return result;
        }

        Document document = toDocument(responseMessage);
        Element response = document.getDocumentElement();

        return getResult(response);
    }

    private GetRequestStatusResult getResult(Element response) throws IOException, SAXException {
        GetRequestStatusResult result = new GetRequestStatusResult();

        NamedNodeMap attributes = response.getAttributes();

        result.setNamespaceUpg(getAttrValue(attributes, result.NAMESPACE_UPG_NAME));
        result.setNamespaceUpgRussia(getAttrValue(attributes, result.NAMESPACE_UPG_RUSSIA_NAME));
        result.setNamespaceXsi(getAttrValue(attributes, result.NAMESPACE_XSI_NAME));
        result.setAttrCreateTime(getAttrValue(attributes, result.ATTR_CREATE_TIME_NAME));
        result.setAttrRequestId(getAttrValue(attributes, result.ATTR_REQUEST_ID_NAME));
        result.setAttrResponseId(getAttrValue(attributes, result.ATTR_RESPONSE_ID_NAME));
        result.setAttrSender(getAttrValue(attributes, result.ATTR_SENDER_NAME));
        result.setAttrVersion(getAttrValue(attributes, result.ATTR_VERSION_NAME));

        Node model = response.getElementsByTagName(MODEL_NODE_NAME).item(MODEL_NODE_INDEX);
        setStatusResponse(model.getTextContent(), result);

        return result;
    }

    private void setStatusResponse(String xml, GetRequestStatusResult result) throws
            IOException, SAXException {
        Document document = toDocument(xml);
        StateResponse stateResponse = result.getStateResponse();

        NamedNodeMap attributes = document.getDocumentElement().getAttributes();
        stateResponse.setAttrXmlns(getAttrValue(attributes, stateResponse.ATTR_XMLNS_NAME));

        stateResponse.setBankMessage(getNodeValue(document, stateResponse.BANK_MESSAGE_NAME));
        stateResponse.setDocId(getNodeValue(document, stateResponse.DOC_ID_NAME));
        stateResponse.setDocType(getNodeValue(document, stateResponse.DOC_TYPE_NAME));
        stateResponse.setExtId(getNodeValue(document, stateResponse.EXT_ID_NAME));
        stateResponse.setState(getNodeValue(document, stateResponse.STATE_NAME));
    }

    @Autowired
    DocumentBuilder docBuilder;

    private Document toDocument(String response) throws IOException, SAXException {
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        return docBuilder.parse(inputStream);
    }

    private String toString(JAXBElement<GetRequestStatusResponse> response) {
        String result = "";
        for (String responseLine : response.getValue().getReturn()) {
            result += responseLine;
        }
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
                || (response.contains("Error"))
                || (response.contains("UNKNOWN REQUEST"))) {
            handleError(response);
        }
    }

    @Autowired
    private SoapMessageList soapMessageList;

    private void handleError(String response) {
        String exceptionMessage = response;
        exceptionMessage += "\n>>>>SAOP Messages:";
        exceptionMessage += soapMessageList.getLastRequestAsString();

        SoapServerGetRequestStatusException exception = new SoapServerGetRequestStatusException(exceptionMessage);
        exception.setSoapMessages(soapMessageList.getLastRequestAsString());
        exception.setSoapResponse(response);
        throw exception;
    }
}
