package com.emulator.domain.soap.requests.getrequeststatus;

import com.emulator.domain.soap.com.bssys.sbns.upg.GetRequestStatusResponse;
import com.emulator.domain.soap.requests.ErrorResponseHandler;
import com.emulator.domain.soap.requests.getrequeststatus.statement.Operation;
import com.emulator.domain.soap.requests.getrequeststatus.statement.Statement;
import com.emulator.domain.soap.requests.getrequeststatus.stateresponse.StateResponse;
import com.emulator.domain.soap.signcollection.ConfirmSign;
import com.emulator.domain.soap.signcollection.Sign;
import com.emulator.domain.soap.signcollection.SignCollection;
import com.emulator.domain.soap.signcollection.UserWorkspace;
import com.emulator.exception.ParameterIsNullException;
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

    GetRequestStatusResult getResult(JAXBElement<GetRequestStatusResponse> response) throws IOException,
            SAXException {
        if (response == null) {
            throw new ParameterIsNullException("GetRequestStatusResponseHandler.response must not be 'null'");
        }
        String responseMessage = toString(response);
        return parse(responseMessage);
    }

    @Autowired
    private ErrorResponseHandler errorHandler;

    private GetRequestStatusResult parse(String responseMessage) throws IOException, SAXException {
        errorHandler.check(responseMessage);

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

        NodeList modelList = response.getElementsByTagName(MODEL_NODE_NAME);

        for (int i = 0; i < modelList.getLength(); i++) {
            Node model = modelList.item(i);

            String textContent = model.getTextContent();
            if (textContent.contains("</Statement>")) {
                setStatement(textContent, result);
            }
            if (textContent.contains("</StateResponse>")) {
                setStatusResponse(textContent, result);
            }
        }

        return result;
    }

    private void setStatement(String xml, GetRequestStatusResult result) throws IOException, SAXException {
        Document document = toDocument(xml);

        Statement statement = new Statement();
        result.add(statement);

        NamedNodeMap attributes = document.getDocumentElement().getAttributes();
        statement.setAttrXmlns(getAttrValue(attributes, statement.XMLNS_ATTR_NAME));

        statement.setAcceptDate(getNodeValue(document, statement.ACCEPT_DATE_NODE_NAME));
        statement.setAccount(getNodeValue(document, statement.ACCOUNT_NODE_NAME));
        statement.setAccountId(getNodeValue(document, statement.ACCOUNT_ID_NODE_NAME));
        statement.setActual(getNodeValue(document, statement.ACTUAL_NODE_NAME));
        statement.setAuthor(getNodeValue(document, statement.AUTHOR_NODE_NAME));
        statement.setBankBIC(getNodeValue(document, statement.BANK_BIC_NODE_NAME));
        statement.setBankName(getNodeValue(document, statement.BANK_NAME_NODE_NAME));
        statement.setBankNameBIC(getNodeValue(document, statement.BANK_NAME_BIC_NODE_NAME));
        statement.setCard1Sum(getNodeValue(document, statement.CARD_1_SUM_NODE_NAME));
        statement.setCard2Sum(getNodeValue(document, statement.CARD_2_SUM_NODE_NAME));
        statement.setCreditReturn(getNodeValue(document, statement.CREDIT_RETURN_NODE_NAME));
        statement.setCreditReturnNat(getNodeValue(document, statement.CREDIT_RETURN_NAT_NODE_NAME));
        statement.setCurrCode(getNodeValue(document, statement.CURR_CODE_NODE_NAME));
        statement.setCurrIsoCode(getNodeValue(document, statement.CURR_ISO_CODE_NODE_NAME));
        statement.setDebetReturn(getNodeValue(document, statement.DEBET_RETURN_NODE_NAME));
        statement.setDebetReturnNat(getNodeValue(document, statement.DEBET_RETURN_NAT_NODE_NAME));
        statement.setDocComment(getNodeValue(document, statement.DOC_COMMENT_NODE_NAME));
        statement.setDocDate(getNodeValue(document, statement.DOC_DATE_NODE_NAME));
        statement.setDocNumber(getNodeValue(document, statement.DOC_NUMBER_NODE_NAME));
        statement.setDocTypeVersion(getNodeValue(document, statement.DOC_TYPE_VERSION_NODE_NAME));
        statement.setExternalId(getNodeValue(document, statement.EXTERNAL_ID_NODE_NAME));
        statement.setFromDate(getNodeValue(document, statement.FROM_DATE_NODE_NAME));
        statement.setHashcode(getNodeValue(document, statement.HASHCODE_NODE_NAME));
        statement.setInboundBalance(getNodeValue(document, statement.INBOUND_BALANCE_NODE_NAME));
        statement.setInboundBalanceNat(getNodeValue(document, statement.INBOUND_BALANCE_NAT_NODE_NAME));
        statement.setIndexed(getNodeValue(document, statement.INDEXED_NODE_NAME));
        statement.setIsFinal(getNodeValue(document, statement.IS_FINAL_NODE_NAME));
        statement.setLastOperationDate(getNodeValue(document, statement.LAST_OPERATION_DATE_NODE_NAME));
        statement.setOldDocId(getNodeValue(document, statement.OLD_DOC_ID_NODE_NAME));
        statement.setOrgInn(getNodeValue(document, statement.ORG_INN_NODE_NAME));
        statement.setOrgName(getNodeValue(document, statement.ORG_NAME_NODE_NAME));
        statement.setOutboundBalance(getNodeValue(document, statement.OUTBOUND_BALANCE_NODE_NAME));
        statement.setOutboundBalanceNat(getNodeValue(document, statement.OUTBOUND_BALANCE_NAT_NODE_NAME));
        statement.setPlanOutboundBalance(getNodeValue(document, statement.PLAN_OUTBOUND_BALANCE_NODE_NAME));
        statement.setPrevOperationDate(getNodeValue(document, statement.PREV_OPERATION_DATE_NODE_NAME));
        statement.setRateIn(getNodeValue(document, statement.RATE_IN_NODE_NAME));
        statement.setRateInFull(getNodeValue(document, statement.RATE_IN_FULL_NODE_NAME));
        statement.setRateOut(getNodeValue(document, statement.RATE_OUT_NODE_NAME));
        statement.setRateOutFull(getNodeValue(document, statement.RATE_OUT_FULL_NODE_NAME));
        statement.setRequestId(getNodeValue(document, statement.REQUEST_ID_NODE_NAME));
        statement.setSeizureAmount(getNodeValue(document, statement.SEIZURE_AMOUNT_NODE_NAME));
        statement.setSeizureWholeAmount(getNodeValue(document, statement.SEIZURE_WHOLE_AMOUNT_NODE_NAME));
        statement.setToDate(getNodeValue(document, statement.TO_DATE_NODE_NAME));

        NodeList operationsNodeList = document.getElementsByTagName(statement.OPERATION_NODE_NAME);
        for (int i = 0; i < operationsNodeList.getLength(); i++) {
            Node operationNode = operationsNodeList.item(i);
            Operation operation = new Operation();
            setOperation(operationNode, operation);
            statement.add(operation);
        }

        NodeList signCollectionNodeList = document.getElementsByTagName(statement.SIGN_COLLECTION_INNER_NODE_NAME);
        if (signCollectionNodeList.getLength() == 1) {
            Node signCollectionNode = signCollectionNodeList.item(0);
            SignCollection signCollection = statement.getSignCollection();
            signCollection.setBankMessage(getNodeValue(signCollectionNode.getChildNodes(), signCollection
                    .BANK_MESSAGE_NODE_NAME));
            signCollection.setDigestName(getNodeValue(signCollectionNode.getChildNodes(), signCollection
                    .DIGEST_NAME_NODE_NAME));

            NodeList signsNodeList = signCollectionNodeList.item(0).getChildNodes();
            for (int i = 0; i < signsNodeList.getLength(); i++) {
                Node signNode = signsNodeList.item(i);
                if (signNode.getNodeName().equals(signCollection.SIGN_NODE_NAME)) {
                    Sign sign = new Sign();
                    setSign(signNode, sign);
                    signCollection.add(sign);
                }
            }
        }
    }

    private String getNodeValue(NodeList list, String nodeName) {
        for (int i = 0; i < list.getLength(); i++) {
            Node childNode = list.item(i);
            if (childNode.getNodeName().equals(nodeName)) {
                return childNode.getTextContent();
            }
        }
        return "";
    }

    private void setOperation(Node node, Operation operation) {
        NodeList childNodes = node.getChildNodes();

        operation.setAcceptDate(getNodeValue(childNodes, operation.ACCEPT_DATE_NODE_NAME));
        operation.setCashSymbol(getNodeValue(childNodes, operation.CASH_SYMBOL_NODE_NAME));
        operation.setCbc(getNodeValue(childNodes, operation.CBC_NODE_NAME));
        operation.setChargeType(getNodeValue(childNodes, operation.CHARGE_TYPE_NODE_NAME));
        operation.setCorrAccCurr(getNodeValue(childNodes, operation.CORR_ACC_CURR_NODE_NAME));
        operation.setCorrAccCurrSum(getNodeValue(childNodes, operation.CORR_ACC_CURR_SUM_NODE_NAME));
        operation.setCreateDocId(getNodeValue(childNodes, operation.CREATE_DOC_ID_NODE_NAME));
        operation.setCredit(getNodeValue(childNodes, operation.CREDIT_NODE_NAME));
        operation.setCreditNat(getNodeValue(childNodes, operation.CREDIT_NAT_NODE_NAME));
        operation.setDboDocId(getNodeValue(childNodes, operation.DBO_DOC_ID_NODE_NAME));
        operation.setDebet(getNodeValue(childNodes, operation.DEBET_NODE_NAME));
        operation.setDebetNat(getNodeValue(childNodes, operation.DEBET_NAT_NODE_NAME));
        operation.setDepartmentalInfoDocDate(getNodeValue(childNodes, operation.DEPARTMENTAL_INFO_DOC_DATE_NODE_NAME));
        operation.setDepartmentalInfoDocNo(getNodeValue(childNodes, operation.DEPARTMENTAL_INFO_DOC_NO_NODE_NAME));
        operation.setDocDate(getNodeValue(childNodes, operation.DOC_DATE_NODE_NAME));
        operation.setDocDate2(getNodeValue(childNodes, operation.DOC_DATE_2_NODE_NAME));
        operation.setDocExtId(getNodeValue(childNodes, operation.DOC_EXT_ID_NODE_NAME));
        operation.setDocMatchTime(getNodeValue(childNodes, operation.DOC_MATCH_TIME_NODE_NAME));
        operation.setDocNumber(getNodeValue(childNodes, operation.DOC_NUMBER_NODE_NAME));
        operation.setDocNumber2(getNodeValue(childNodes, operation.DOC_NUMBER_2_NODE_NAME));
        operation.setDocShifr(getNodeValue(childNodes, operation.DOC_SHIFR_NODE_NAME));
        operation.setDocumentDate(getNodeValue(childNodes, operation.DOCUMENT_DATE_NODE_NAME));
        operation.setDocumentId(getNodeValue(childNodes, operation.DOCUMENT_ID_NODE_NAME));
        operation.setDocumentNumber(getNodeValue(childNodes, operation.DOCUMENT_NUMBER_NODE_NAME));
        operation.setDocumentSum(getNodeValue(childNodes, operation.DOCUMENT_SUM_NODE_NAME));
        operation.setDocumentType(getNodeValue(childNodes, operation.DOCUMENT_TYPE_NODE_NAME));
        operation.setFilial(getNodeValue(childNodes, operation.FILIAL_NODE_NAME));
        operation.setFioForStamp(getNodeValue(childNodes, operation.FIO_FOR_STAMP_NODE_NAME));
        operation.setInfo(getNodeValue(childNodes, operation.INFO_NODE_NAME));
        operation.setLetterOfCreditAddCond(getNodeValue(childNodes, operation.LETTER_OF_CREDIT_ADD_COND_NODE_NAME));
        operation.setLetterOfCreditDemandDocs(getNodeValue(childNodes, operation
                .LETTER_OF_CREDIT_DEMAND_DOCS_NODE_NAME));
        operation.setLetterOfCreditPayAcc(getNodeValue(childNodes, operation.LETTER_OF_CREDIT_PAY_ACC_NODE_NAME));
        operation.setLetterOfCreditPaymCond(getNodeValue(childNodes, operation.LETTER_OF_CREDIT_PAYM_COND_NODE_NAME));
        operation.setLetterOfCreditPeriodVal(getNodeValue(childNodes, operation.LETTER_OF_CREDIT_PERIOD_VAL_NODE_NAME));
        operation.setLetterOfCreditType(getNodeValue(childNodes, operation.LETTER_OF_CREDIT_TYPE_NODE_NAME));
        operation.setNumPayment(getNodeValue(childNodes, operation.NUM_PAYMENT_NODE_NAME));
        operation.setNumberPP(getNodeValue(childNodes, operation.NUMBER_PP_NODE_NAME));
        operation.setoS(getNodeValue(childNodes, operation.OS_NODE_NAME));
        operation.setOcato(getNodeValue(childNodes, operation.OCATO_NODE_NAME));
        operation.setOperationDate(getNodeValue(childNodes, operation.OPERATION_DATE_NODE_NAME));
        operation.setOperationType(getNodeValue(childNodes, operation.OPERATION_TYPE_NODE_NAME));
        operation.setPayerAccount(getNodeValue(childNodes, operation.PAYER_ACCOUNT_NODE_NAME));
        operation.setPayerBankBic(getNodeValue(childNodes, operation.PAYER_BANK_BIC_NODE_NAME));
        operation.setPayerBankCorrAccount(getNodeValue(childNodes, operation.PAYER_BANK_CORR_ACCOUNT_NODE_NAME));
        operation.setPayerBankName(getNodeValue(childNodes, operation.PAYER_BANK_NAME_NODE_NAME));
        operation.setPayerCurrCode(getNodeValue(childNodes, operation.PAYER_CURR_CODE_NODE_NAME));
        operation.setPayerINN(getNodeValue(childNodes, operation.PAYER_INN_NODE_NAME));
        operation.setPayerKPP(getNodeValue(childNodes, operation.PAYER_KPP_NODE_NAME));
        operation.setPayerName(getNodeValue(childNodes, operation.PAYER_NAME_NODE_NAME));
        operation.setPayingCondition(getNodeValue(childNodes, operation.PAYING_CONDITION_NODE_NAME));
        operation.setPaymentGoalId(getNodeValue(childNodes, operation.PAYMENT_GOAL_ID_NODE_NAME));
        operation.setPaymentOrder(getNodeValue(childNodes, operation.PAYMENT_ORDER_NODE_NAME));
        operation.setPaymentPurpose(getNodeValue(childNodes, operation.PAYMENT_PURPOSE_NODE_NAME));
        operation.setPaytCode(getNodeValue(childNodes, operation.PAYT_CODE_NODE_NAME));
        operation.setPaytKind(getNodeValue(childNodes, operation.PAYT_KIND_NODE_NAME));
        operation.setPeriodVal(getNodeValue(childNodes, operation.PERIOD_VAL_NODE_NAME));
        operation.setReceiverAccount(getNodeValue(childNodes, operation.RECEIVER_ACCOUNT_NODE_NAME));
        operation.setReceiverBankBic(getNodeValue(childNodes, operation.RECEIVER_BANK_BIC_NODE_NAME));
        operation.setReceiverBankCorrAccount(getNodeValue(childNodes, operation.RECEIVER_BANK_CORR_ACCOUNT_NODE_NAME));
        operation.setReceiverBankName(getNodeValue(childNodes, operation.RECEIVER_BANK_NAME_NODE_NAME));
        operation.setReceiverCurrCode(getNodeValue(childNodes, operation.RECEIVER_CURR_CODE_NODE_NAME));
        operation.setReceiverINN(getNodeValue(childNodes, operation.RECEIVER_INN_NODE_NAME));
        operation.setReceiverKPP(getNodeValue(childNodes, operation.RECEIVER_KPP_NODE_NAME));
        operation.setReceiverName(getNodeValue(childNodes, operation.RECEIVER_NAME_NODE_NAME));
        operation.setRecieptDate(getNodeValue(childNodes, operation.RECIEPT_DATE_NODE_NAME));
        operation.setReserv23(getNodeValue(childNodes, operation.RESERV_23_NODE_NAME));
        operation.setsNumDoc(getNodeValue(childNodes, operation.S_NUM_DOC_NODE_NAME));
        operation.setS_TI(getNodeValue(childNodes, operation.S_TI_NODE_NAME));
        operation.setStatus(getNodeValue(childNodes, operation.STATUS_NODE_NAME));
        operation.setSumRest(getNodeValue(childNodes, operation.SUM_REST_NODE_NAME));
        operation.setTaxPeriod(getNodeValue(childNodes, operation.TAX_PERIOD_NODE_NAME));
        operation.setUip(getNodeValue(childNodes, operation.UIP_NODE_NAME));
        operation.setValueDate(getNodeValue(childNodes, operation.VALUE_DATE_NODE_NAME));
        operation.setWriteOffDate(getNodeValue(childNodes, operation.WRITE_OFF_DATE_NODE_NAME));
    }

    private void setSign(Node node, Sign sign) throws IOException, SAXException {
        NodeList childNodes = node.getChildNodes();

        sign.setCertificateGuid(getNodeValue(childNodes, sign.CERTIFICATE_GUID_NODE_NAME));
        sign.setContent(getNodeValue(childNodes, sign.CONTENT_NODE_NAME));
        sign.setContentLarge(getNodeValue(childNodes, sign.CONTENT_LARGE_NODE_NAME));
        sign.setDigestScheme(getNodeValue(childNodes, sign.DIGEST_SCHEME_NODE_NAME));
        sign.setDigestSchemeFormat(getNodeValue(childNodes, sign.DIGEST_SCHEME_FORMAT_NODE_NAME));
        sign.setDigestSchemeVersion(getNodeValue(childNodes, sign.DIGEST_SCHEME_VERSION_NODE_NAME));
        sign.setDtCheck(getNodeValue(childNodes, sign.DT_CHECK_NODE_NAME));
        sign.setDtCreate(getNodeValue(childNodes, sign.DT_CREATE_NODE_NAME));
        sign.setOrgId(getNodeValue(childNodes, sign.ORG_ID_NODE_NAME));
        sign.setOrgName(getNodeValue(childNodes, sign.ORG_NAME_NODE_NAME));
        sign.setPosition(getNodeValue(childNodes, sign.POSITION_NODE_NAME));
        sign.setSafeTouchAutoSign(getNodeValue(childNodes, sign.SAFE_TOUCH_AUTO_SIGN_NODE_NAME));
        sign.setSafeTouchDigestScheme(getNodeValue(childNodes, sign.SAFE_TOUCH_DIGEST_SCHEME_NODE_NAME));
        sign.setSafeTouchDigestSchemeVersion(getNodeValue(childNodes, sign.SAFE_TOUCH_DIGEST_SCHEME_VERSION_NODE_NAME));
        sign.setSignAuthorityId(getNodeValue(childNodes, sign.SIGN_AUTHORITY_ID_NODE_NAME));
        sign.setSignHash(getNodeValue(childNodes, sign.SIGN_HASH_NODE_NAME));
        sign.setSignKey(getNodeValue(childNodes, sign.SIGN_KEY_NODE_NAME));
        sign.setSignType(getNodeValue(childNodes, sign.SIGN_TYPE_NODE_NAME));
        sign.setSignerFullName(getNodeValue(childNodes, sign.SIGNER_FULL_NAME_NODE_NAME));
        sign.setUserIP(getNodeValue(childNodes, sign.USER_IP_NODE_NAME));
        sign.setUserMAC(getNodeValue(childNodes, sign.USER_MAC_NODE_NAME));
        sign.setUserName(getNodeValue(childNodes, sign.USER_NAME_NODE_NAME));
        sign.setValid(getNodeValue(childNodes, sign.VALID_NODE_NAME));

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeName().equals(sign.CONFIRM_SIGN_NODE_NAME)) {
                ConfirmSign confirmSign = sign.getConfirmSign();
                setConfirmSign(childNode, confirmSign);
            }
            if (childNode.getNodeName().equals(sign.USER_WORKSPACE_INNER_NODE_NAME)) {
                UserWorkspace userWorkspace = sign.getUserWorkspace();
                setUserWorkspace(childNode, userWorkspace);
            }
        }
    }

    private void setConfirmSign(Node node, ConfirmSign confirmSign) throws IOException, SAXException {
        NodeList childNodes = node.getChildNodes();

        confirmSign.setCertificateGuid(getNodeValue(childNodes, confirmSign.CERTIFICATE_GUID_NODE_NAME));
        confirmSign.setContent(getNodeValue(childNodes, confirmSign.CONTENT_NODE_NAME));
        confirmSign.setContentLarge(getNodeValue(childNodes, confirmSign.CONTENT_LARGE_NODE_NAME));
        confirmSign.setDigestScheme(getNodeValue(childNodes, confirmSign.DIGEST_SCHEME_NODE_NAME));
        confirmSign.setDigestSchemeFormat(getNodeValue(childNodes, confirmSign.DIGEST_SCHEME_FORMAT_NODE_NAME));
        confirmSign.setDigestSchemeVersion(getNodeValue(childNodes, confirmSign.DIGEST_SCHEME_VERSION_NODE_NAME));
        confirmSign.setDtCheck(getNodeValue(childNodes, confirmSign.DT_CHECK_NODE_NAME));
        confirmSign.setDtCreate(getNodeValue(childNodes, confirmSign.DT_CREATE_NODE_NAME));
        confirmSign.setOrgId(getNodeValue(childNodes, confirmSign.ORG_ID_NODE_NAME));
        confirmSign.setOrgName(getNodeValue(childNodes, confirmSign.ORG_NAME_NODE_NAME));
        confirmSign.setPosition(getNodeValue(childNodes, confirmSign.POSITION_NODE_NAME));
        confirmSign.setSafeTouchAutoSign(getNodeValue(childNodes, confirmSign.SAFE_TOUCH_AUTO_SIGN_NODE_NAME));
        confirmSign.setSafeTouchDigestScheme(getNodeValue(childNodes, confirmSign.SAFE_TOUCH_DIGEST_SCHEME_NODE_NAME));
        confirmSign.setSafeTouchDigestSchemeVersion(getNodeValue(childNodes, confirmSign
                .SAFE_TOUCH_DIGEST_SCHEME_VERSION_NODE_NAME));
        confirmSign.setSignAuthorityId(getNodeValue(childNodes, confirmSign.SIGN_AUTHORITY_ID_NODE_NAME));
        confirmSign.setSignHash(getNodeValue(childNodes, confirmSign.SIGN_HASH_NODE_NAME));
        confirmSign.setSignKey(getNodeValue(childNodes, confirmSign.SIGN_KEY_NODE_NAME));
        confirmSign.setSignType(getNodeValue(childNodes, confirmSign.SIGN_TYPE_NODE_NAME));
        confirmSign.setSignerFullName(getNodeValue(childNodes, confirmSign.SIGNER_FULL_NAME_NODE_NAME));
        confirmSign.setUserIP(getNodeValue(childNodes, confirmSign.USER_IP_NODE_NAME));
        confirmSign.setUserMAC(getNodeValue(childNodes, confirmSign.USER_MAC_NODE_NAME));
        confirmSign.setUserName(getNodeValue(childNodes, confirmSign.USER_NAME_NODE_NAME));
        confirmSign.setValid(getNodeValue(childNodes, confirmSign.VALID_NODE_NAME));

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeName().equals(confirmSign.USER_WORKSPACE_INNER_NODE_NAME)) {
                UserWorkspace userWorkspace = confirmSign.getUserWorkspace();
                setUserWorkspace(childNode, userWorkspace);
            }
        }
    }

    private void setUserWorkspace(Node node, UserWorkspace userWorkspace) throws IOException, SAXException {
        NodeList childNodes = node.getChildNodes();

        userWorkspace.setAVPActive(getNodeValue(childNodes, userWorkspace.AVP_ACTIVE_NODE_NAME));
        userWorkspace.setOSUpdatable(getNodeValue(childNodes, userWorkspace.OS_UPDATABLE_NODE_NAME));
        userWorkspace.setAddInfo(getNodeValue(childNodes, userWorkspace.ADD_INFO_NODE_NAME));
        userWorkspace.setFaultPassAttemptCount(getNodeValue(childNodes, userWorkspace
                .FAULT_PASS_ATTEMPT_COUNT_NODE_NAME));
        userWorkspace.setHashCode(getNodeValue(childNodes, userWorkspace.HASH_CODE_NODE_NAME));
        userWorkspace.setNotRemoteAccess(getNodeValue(childNodes, userWorkspace.NOT_REMOTE_ACCESS_NODE_NAME));
        userWorkspace.setOuterKeyStorage(getNodeValue(childNodes, userWorkspace.OUTER_KEY_STORAGE_NODE_NAME));
        userWorkspace.setPassChanged(getNodeValue(childNodes, userWorkspace.PASS_CHANGED_NODE_NAME));
        userWorkspace.setUserMAC(getNodeValue(childNodes, userWorkspace.USER_MAC_NODE_NAME));
    }

    private void setStatusResponse(String xml, GetRequestStatusResult result) throws IOException, SAXException {
        Document document = toDocument(xml);

        StateResponse stateResponse = new StateResponse();
        result.add(stateResponse);

        NamedNodeMap attributes = document.getDocumentElement().getAttributes();
        stateResponse.setAttrXmlns(getAttrValue(attributes, stateResponse.XMLNS_ATTR_NAME));

        stateResponse.setBankMessage(getNodeValue(document, stateResponse.BANK_MESSAGE_NODE_NAME));
        stateResponse.setBankMessageAuthor(getNodeValue(document, stateResponse.BANK_MESSAGE_AUTHOR_NODE_NAME));
        stateResponse.setCreateTime(getNodeValue(document, stateResponse.CREATE_TIME_NODE_NAME));
        stateResponse.setDocId(getNodeValue(document, stateResponse.DOC_ID_NODE_NAME));
        stateResponse.setDocType(getNodeValue(document, stateResponse.DOC_TYPE_NODE_NAME));
        stateResponse.setExtId(getNodeValue(document, stateResponse.EXT_ID_NODE_NAME));
        stateResponse.setMessageOnlyForBank(getNodeValue(document, stateResponse.MESSAGE_ONLY_FOR_BANK_NODE_NAME));
        stateResponse.setOperationDate(getNodeValue(document, stateResponse.OPERATION_DATE_NODE_NAME));
        stateResponse.setState(getNodeValue(document, stateResponse.STATE_NODE_NAME));
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
        NodeList nodeList = doc.getElementsByTagName(nodeName);
        if (nodeList.getLength() == 0) {
            return "";
        }
        return nodeList.item(0).getTextContent();
    }
}
