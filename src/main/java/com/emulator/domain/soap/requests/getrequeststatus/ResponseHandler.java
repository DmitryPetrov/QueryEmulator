package com.emulator.domain.soap.requests.getrequeststatus;

import com.emulator.domain.soap.SoapMessageList;
import com.emulator.domain.soap.com.bssys.sbns.upg.GetRequestStatusResponse;
import com.emulator.domain.soap.requests.getrequeststatus.statement.Operation;
import com.emulator.domain.soap.requests.getrequeststatus.statement.Sign;
import com.emulator.domain.soap.requests.getrequeststatus.statement.SignCollection;
import com.emulator.domain.soap.requests.getrequeststatus.statement.Statement;
import com.emulator.domain.soap.requests.getrequeststatus.stateresponse.StateResponse;
import com.emulator.domain.soap.requests.getrequeststatus.stateresponse.UserWorkspace;
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

        NodeList modelList = response.getElementsByTagName(MODEL_NODE_NAME);

        for (int i = 0; i < modelList.getLength(); i++) {
            Node model = modelList.item(i);

            String textContent = model.getTextContent();
            if (textContent.contains("Statement")) {
                setStatement(textContent, result);
            }
            if (textContent.contains("StatusResponse")) {
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

        NodeList operationsNodeList = document.getElementsByTagName(statement.OPERATIONS_NODE_NAME);
        for (int i = 0; i < operationsNodeList.getLength(); i++) {
            String textContent = operationsNodeList.item(i).getTextContent();
            Operation operation = new Operation();
            setOperation(textContent, operation);
            statement.add(operation);
        }

        NodeList signCollectionNode = document.getElementsByTagName(statement.SIGN_COLLECTION_INNER_NODE_NAME);
        if (signCollectionNode.getLength() == 1) {
            SignCollection signCollection = statement.getSignCollection();
            signCollection.setBankMessage(getNodeValue(document, signCollection.BANK_MESSAGE_NODE_NAME));
            signCollection.setDigestName(getNodeValue(document, signCollection.DIGEST_NAME_NODE_NAME));

            NodeList signsNodeList = document.getElementsByTagName(signCollection.SIGN_NODE_NAME);
            for (int i = 0; i < signsNodeList.getLength(); i++) {
                String textContent = operationsNodeList.item(i).getTextContent();
                Sign sign = new Sign();
                setSign(textContent, sign);
                signCollection.add(sign);
            }
        }
    }

    private void setOperation(String xml, Operation operation) throws IOException, SAXException {
        Document document = toDocument(xml);

        operation.setAcceptDate(getNodeValue(document, operation.ACCEPT_DATE_NODE_NAME));
        operation.setCashSymbol(getNodeValue(document, operation.CASH_SYMBOL_NODE_NAME));
        operation.setCbc(getNodeValue(document, operation.CBC_NODE_NAME));
        operation.setChargeType(getNodeValue(document, operation.CHARGE_TYPE_NODE_NAME));
        operation.setCorrAccCurr(getNodeValue(document, operation.CORR_ACC_CURR_NODE_NAME));
        operation.setCorrAccCurrSum(getNodeValue(document, operation.CORR_ACC_CURR_SUM_NODE_NAME));
        operation.setCreateDocId(getNodeValue(document, operation.CREATE_DOC_ID_NODE_NAME));
        operation.setCredit(getNodeValue(document, operation.CREDIT_NODE_NAME));
        operation.setCreditNat(getNodeValue(document, operation.CREDIT_NAT_NODE_NAME));
        operation.setDboDocId(getNodeValue(document, operation.DBO_DOC_ID_NODE_NAME));
        operation.setDebet(getNodeValue(document, operation.DEBET_NODE_NAME));
        operation.setDebetNat(getNodeValue(document, operation.DEBET_NAT_NODE_NAME));
        operation.setDepartmentalInfoDocDate(getNodeValue(document, operation.DEPARTMENTAL_INFO_DOC_DATE_NODE_NAME));
        operation.setDepartmentalInfoDocNo(getNodeValue(document, operation.DEPARTMENTAL_INFO_DOC_NO_NODE_NAME));
        operation.setDocDate(getNodeValue(document, operation.DOC_DATE_NODE_NAME));
        operation.setDocDate2(getNodeValue(document, operation.DOC_DATE_2_NODE_NAME));
        operation.setDocExtId(getNodeValue(document, operation.DOC_EXT_ID_NODE_NAME));
        operation.setDocMatchTime(getNodeValue(document, operation.DOC_MATCH_TIME_NODE_NAME));
        operation.setDocNumber(getNodeValue(document, operation.DOC_NUMBER_NODE_NAME));
        operation.setDocNumber2(getNodeValue(document, operation.DOC_NUMBER_2_NODE_NAME));
        operation.setDocShifr(getNodeValue(document, operation.DOC_SHIFR_NODE_NAME));
        operation.setDocumentDate(getNodeValue(document, operation.DOCUMENT_DATE_NODE_NAME));
        operation.setDocumentId(getNodeValue(document, operation.DOCUMENT_ID_NODE_NAME));
        operation.setDocumentNumber(getNodeValue(document, operation.DOCUMENT_NUMBER_NODE_NAME));
        operation.setDocumentSum(getNodeValue(document, operation.DOCUMENT_SUM_NODE_NAME));
        operation.setDocumentType(getNodeValue(document, operation.DOCUMENT_TYPE_NODE_NAME));
        operation.setFilial(getNodeValue(document, operation.FILIAL_NODE_NAME));
        operation.setFioForStamp(getNodeValue(document, operation.FIO_FOR_STAMP_NODE_NAME));
        operation.setInfo(getNodeValue(document, operation.INFO_NODE_NAME));
        operation.setLetterOfCreditAddCond(getNodeValue(document, operation.LETTER_OF_CREDIT_ADD_COND_NODE_NAME));
        operation.setLetterOfCreditDemandDocs(getNodeValue(document, operation.LETTER_OF_CREDIT_DEMAND_DOCS_NODE_NAME));
        operation.setLetterOfCreditPayAcc(getNodeValue(document, operation.LETTER_OF_CREDIT_PAY_ACC_NODE_NAME));
        operation.setLetterOfCreditPaymCond(getNodeValue(document, operation.LETTER_OF_CREDIT_PAYM_COND_NODE_NAME));
        operation.setLetterOfCreditPeriodVal(getNodeValue(document, operation.LETTER_OF_CREDIT_PERIOD_VAL_NODE_NAME));
        operation.setLetterOfCreditType(getNodeValue(document, operation.LETTER_OF_CREDIT_TYPE_NODE_NAME));
        operation.setNumPayment(getNodeValue(document, operation.NUM_PAYMENT_NODE_NAME));
        operation.setNumberPP(getNodeValue(document, operation.NUMBER_PP_NODE_NAME));
        operation.setoS(getNodeValue(document, operation.OS_NODE_NAME));
        operation.setOcato(getNodeValue(document, operation.OCATO_NODE_NAME));
        operation.setOperationDate(getNodeValue(document, operation.OPERATION_DATE_NODE_NAME));
        operation.setOperationType(getNodeValue(document, operation.OPERATION_TYPE_NODE_NAME));
        operation.setPayerAccount(getNodeValue(document, operation.PAYER_ACCOUNT_NODE_NAME));
        operation.setPayerBankBic(getNodeValue(document, operation.PAYER_BANK_BIC_NODE_NAME));
        operation.setPayerBankCorrAccount(getNodeValue(document, operation.PAYER_BANK_CORR_ACCOUNT_NODE_NAME));
        operation.setPayerBankName(getNodeValue(document, operation.PAYER_BANK_NAME_NODE_NAME));
        operation.setPayerCurrCode(getNodeValue(document, operation.PAYER_CURR_CODE_NODE_NAME));
        operation.setPayerINN(getNodeValue(document, operation.PAYER_INN_NODE_NAME));
        operation.setPayerKPP(getNodeValue(document, operation.PAYER_KPP_NODE_NAME));
        operation.setPayerName(getNodeValue(document, operation.PAYER_NAME_NODE_NAME));
        operation.setPayingCondition(getNodeValue(document, operation.PAYING_CONDITION_NODE_NAME));
        operation.setPaymentGoalId(getNodeValue(document, operation.PAYMENT_GOAL_ID_NODE_NAME));
        operation.setPaymentOrder(getNodeValue(document, operation.PAYMENT_ORDER_NODE_NAME));
        operation.setPaymentPurpose(getNodeValue(document, operation.PAYMENT_PURPOSE_NODE_NAME));
        operation.setPaytCode(getNodeValue(document, operation.PAYT_CODE_NODE_NAME));
        operation.setPaytKind(getNodeValue(document, operation.PAYT_KIND_NODE_NAME));
        operation.setPeriodVal(getNodeValue(document, operation.PERIOD_VAL_NODE_NAME));
        operation.setReceiverAccount(getNodeValue(document, operation.RECEIVER_ACCOUNT_NODE_NAME));
        operation.setReceiverBankBic(getNodeValue(document, operation.RECEIVER_BANK_BIC_NODE_NAME));
        operation.setReceiverBankCorrAccount(getNodeValue(document, operation.RECEIVER_BANK_CORR_ACCOUNT_NODE_NAME));
        operation.setReceiverBankName(getNodeValue(document, operation.RECEIVER_BANK_NAME_NODE_NAME));
        operation.setReceiverCurrCode(getNodeValue(document, operation.RECEIVER_CURR_CODE_NODE_NAME));
        operation.setReceiverINN(getNodeValue(document, operation.RECEIVER_INN_NODE_NAME));
        operation.setReceiverKPP(getNodeValue(document, operation.RECEIVER_KPP_NODE_NAME));
        operation.setReceiverName(getNodeValue(document, operation.RECEIVER_NAME_NODE_NAME));
        operation.setRecieptDate(getNodeValue(document, operation.RECIEPT_DATE_NODE_NAME));
        operation.setReserv23(getNodeValue(document, operation.RESERV_23_NODE_NAME));
        operation.setsNumDoc(getNodeValue(document, operation.S_NUM_DOC_NODE_NAME));
        operation.setS_TI(getNodeValue(document, operation.S_TI_NODE_NAME));
        operation.setStatus(getNodeValue(document, operation.STATUS_NODE_NAME));
        operation.setSumRest(getNodeValue(document, operation.SUM_REST_NODE_NAME));
        operation.setTaxPeriod(getNodeValue(document, operation.TAX_PERIOD_NODE_NAME));
        operation.setUip(getNodeValue(document, operation.UIP_NODE_NAME));
        operation.setValueDate(getNodeValue(document, operation.VALUE_DATE_NODE_NAME));
        operation.setWriteOffDate(getNodeValue(document, operation.WRITE_OFF_DATE_NODE_NAME));
    }

    private void setSign(String xml, Sign sign) throws IOException, SAXException {
        Document document = toDocument(xml);

        sign.setCertificateGuid(getNodeValue(document, sign.CERTIFICATE_GUID_NODE_NAME));
        sign.setContent(getNodeValue(document, sign.CONTENT_NODE_NAME));
        sign.setContentLarge(getNodeValue(document, sign.CONTENT_LARGE_NODE_NAME));
        sign.setDigestScheme(getNodeValue(document, sign.DIGEST_SCHEME_NODE_NAME));
        sign.setDigestSchemeFormat(getNodeValue(document, sign.DIGEST_SCHEME_FORMAT_NODE_NAME));
        sign.setDigestSchemeVersion(getNodeValue(document, sign.DIGEST_SCHEME_VERSION_NODE_NAME));
        sign.setDtCheck(getNodeValue(document, sign.DT_CHECK_NODE_NAME));
        sign.setDtCreate(getNodeValue(document, sign.DT_CREATE_NODE_NAME));
        sign.setOrgId(getNodeValue(document, sign.ORG_ID_NODE_NAME));
        sign.setOrgName(getNodeValue(document, sign.ORG_NAME_NODE_NAME));
        sign.setPosition(getNodeValue(document, sign.POSITION_NODE_NAME));
        sign.setSafeTouchAutoSign(getNodeValue(document, sign.SAFE_TOUCH_AUTO_SIGN_NODE_NAME));
        sign.setSafeTouchDigestScheme(getNodeValue(document, sign.SAFE_TOUCH_DIGEST_SCHEME_NODE_NAME));
        sign.setSafeTouchDigestSchemeVersion(getNodeValue(document, sign.SAFE_TOUCH_DIGEST_SCHEME_VERSION_NODE_NAME));
        sign.setSignAuthorityId(getNodeValue(document, sign.SIGN_AUTHORITY_ID_NODE_NAME));
        sign.setSignHash(getNodeValue(document, sign.SIGN_HASH_NODE_NAME));
        sign.setSignKey(getNodeValue(document, sign.SIGN_KEY_NODE_NAME));
        sign.setSignType(getNodeValue(document, sign.SIGN_TYPE_NODE_NAME));
        sign.setSignerFullName(getNodeValue(document, sign.SIGNER_FULL_NAME_NODE_NAME));
        sign.setUserIP(getNodeValue(document, sign.USER_IP_NODE_NAME));
        sign.setUserMAC(getNodeValue(document, sign.USER_MAC_NODE_NAME));
        sign.setUserName(getNodeValue(document, sign.USER_NAME_NODE_NAME));
        sign.setValid(getNodeValue(document, sign.VALID_NODE_NAME));

        NodeList confirmSignNodeList = document.getElementsByTagName(sign.CONFIRM_SIGN_NODE_NAME);
        if (confirmSignNodeList.getLength() == 1) {
            String textContent = confirmSignNodeList.item(0).getTextContent();
            Sign confirmSign = sign.getConfirmSign();
            setSign(textContent, confirmSign);
        }

        NodeList userWorkspaceNodeList = document.getElementsByTagName(sign.USER_WORKSPACE_INNER_NODE_NAME);
        if (userWorkspaceNodeList.getLength() != 1) {
            String textContent = userWorkspaceNodeList.item(0).getTextContent();
            UserWorkspace userWorkspace = sign.getUserWorkspace();
            setUserWorkspace(textContent, userWorkspace);
        }
    }

    private void setUserWorkspace(String xml, UserWorkspace userWorkspace) throws IOException, SAXException {
        Document document = toDocument(xml);

        userWorkspace.setAVPActive(getNodeValue(document, userWorkspace.AVP_ACTIVE_NODE_NAME));
        userWorkspace.setOSUpdatable(getNodeValue(document, userWorkspace.OS_UPDATABLE_NODE_NAME));
        userWorkspace.setAddInfo(getNodeValue(document, userWorkspace.ADD_INFO_NODE_NAME));
        userWorkspace.setFaultPassAttemptCount(getNodeValue(document, userWorkspace.FAULT_PASS_ATTEMPT_COUNT_NODE_NAME));
        userWorkspace.setHashCode(getNodeValue(document, userWorkspace.HASH_CODE_NODE_NAME));
        userWorkspace.setNotRemoteAccess(getNodeValue(document, userWorkspace.NOT_REMOTE_ACCESS_NODE_NAME));
        userWorkspace.setOuterKeyStorage(getNodeValue(document, userWorkspace.OUTER_KEY_STORAGE_NODE_NAME));
        userWorkspace.setPassChanged(getNodeValue(document, userWorkspace.PASS_CHANGED_NODE_NAME));
        userWorkspace.setUserMAC(getNodeValue(document, userWorkspace.USER_MAC_NODE_NAME));
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
