package com.emulator.domain.soap.signcollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component("SignCollectionMessageBuilder")
public class SignCollectionMessageBuilder {

    public Element build(Element parentElement, SignCollection signCollection) {
        return buildSignCollection(parentElement, signCollection);
    }

    private Element buildSignCollection(Element parentElement, SignCollection signCollection) {
        Document doc = parentElement.getOwnerDocument();

        Element root = doc.createElement(signCollection.SIGN_COLLECTION_NODE_NAME);
        parentElement.appendChild(root);

        buildElementSignCollection2(root, signCollection);

        return root;
    }

    private void buildElementSignCollection2(Element parentElement, SignCollection signCollection) {
        Document doc = parentElement.getOwnerDocument();

        Element signCollection2 = doc.createElement(signCollection.SIGN_COLLECTION_2_NODE_NAME);
        parentElement.appendChild(signCollection2);

        createTextElement(signCollection2, signCollection.BANK_MESSAGE_NODE_NAME, signCollection.getBankMessage());
        createTextElement(signCollection2, signCollection.DIGEST_NAME_NODE_NAME, signCollection.getDigestName());

        buildElementSigns(signCollection2, signCollection);
    }

    private void buildElementSigns(Element parentElement, SignCollection signCollection) {
        Document doc = parentElement.getOwnerDocument();

        Element signs = doc.createElement(signCollection.SIGNS_NODE_NAME);
        parentElement.appendChild(signs);

        List<Sign> signList = signCollection.getSigns();
        for (Sign sign : signList) {
            Element signElem = doc.createElement(signCollection.SIGN_NODE_NAME);
            signs.appendChild(signElem);

            String signHash = sign.getSignHash().replaceAll(":", "").toUpperCase();
            createTextElement(signElem, sign.SIGN_HASH_NODE_NAME, signHash);
            createTextElement(signElem, sign.CERTIFICATE_GUID_NODE_NAME, sign.getCertificateGuid());
            createTextElement(signElem, sign.CONTENT_NODE_NAME, sign.getContent().replaceAll("\n", ""));

            createTextElement(signElem, sign.SIGN_TYPE_NODE_NAME, sign.getSignType());
            createTextElement(signElem, sign.SIGN_AUTHORITY_ID_NODE_NAME, sign.getSignAuthorityId());
            createTextElement(signElem, sign.SIGNER_FULL_NAME_NODE_NAME, sign.getSignerFullName());

            createTextElement(signElem, sign.DIGEST_SCHEME_NODE_NAME, sign.getDigestScheme());
            createTextElement(signElem, sign.DIGEST_SCHEME_FORMAT_NODE_NAME, sign.getDigestSchemeFormat());
            createTextElement(signElem, sign.DIGEST_SCHEME_VERSION_NODE_NAME, sign.getDigestSchemeVersion());

            createTextElement(signElem, sign.USER_NAME_NODE_NAME, sign.getUserName());
            createTextElement(signElem, sign.ORG_ID_NODE_NAME, sign.getOrgId());
            createTextElement(signElem, sign.ORG_NAME_NODE_NAME, sign.getOrgName());
            createTextElement(signElem, sign.SAFE_TOUCH_AUTO_SIGN_NODE_NAME, sign.getSafeTouchAutoSign());

            createTextElement(signElem, sign.DT_CREATE_NODE_NAME, sign.getDtCreate());
        }
    }

    private void createTextElement(Element parentElement, String elementName, String elementValue) {
        Document doc = parentElement.getOwnerDocument();
        Text text = doc.createTextNode(elementValue);
        Element element = doc.createElement(elementName);
        element.appendChild(text);
        parentElement.appendChild(element);
    }
}
