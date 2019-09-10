package com.emulator.domain.soap.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.dom.DOMSource;

public class RequestMessageHandler implements WebServiceMessageCallback {

    private static Logger log = LoggerFactory.getLogger(RequestMessageHandler.class);

    private String nodeName;
    private String message;

    public RequestMessageHandler(String nodeName, String message) {
        this.nodeName = nodeName;
        this.message = message;
    }

    @Override
    public void doWithMessage(WebServiceMessage message) {
        log.debug("Search node for injection. Node='"+ nodeName + "'");

        Node node = getNode(message);
        Document doc = node.getOwnerDocument();
        CDATASection cdata = doc.createCDATASection(this.message);

        log.debug("Inject message in request.");
        node.appendChild(cdata);
    }

    private Node getNode(WebServiceMessage message) {
        DOMSource domSource = (DOMSource) message.getPayloadSource();
        NodeList nodeList = domSource.getNode().getChildNodes();

        Node node = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeName().compareTo(nodeName) == 0) {
                node = nodeList.item(i);
                break;
            }
        }
        return node;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getMessage() {
        return message;
    }

}
