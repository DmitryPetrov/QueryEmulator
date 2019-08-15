package com.emulator.domain.soap;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.dom.DOMSource;

public class RequestMessageHandler implements WebServiceMessageCallback {

    private String nodeName;
    private String message;

    public RequestMessageHandler(String nodeName, String message) {
        this.nodeName = nodeName;
        this.message = message;
    }

    @Override
    public void doWithMessage(WebServiceMessage message) {
        Node node = getNode(message);
        Document doc = node.getOwnerDocument();
        CDATASection cdata = doc.createCDATASection(this.message);
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
