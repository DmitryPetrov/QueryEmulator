package com.emulator.domain.soap.requests.payrequest;

import com.emulator.domain.soap.requests.RequestParameters;
import com.emulator.domain.soap.requests.payrequest.dto.PayRequestDto;
import com.emulator.domain.soap.requests.payrequest.dto.PayRequestPartDto;
import com.emulator.domain.soap.signcollection.SignCollection;

import java.util.ArrayList;
import java.util.List;

public class PayRequestData extends RequestParameters {

    static final String REQUEST_NODE_NAME = "upg:Request";
    static final String MODELS_NODE_NAME = "upg:Models";
    static final String MODEL_NODE_NAME = "upg:Model";
    static final String PAY_REQUEST_NODE_NAME = "PayRequest";
    static final String ACCEPT_TERM_NODE_NAME = "acceptTerm";
    static final String ACCOUNT_ID_NODE_NAME = "accountId";
    static final String BANK_ACCEPT_DATE_NODE_NAME = "bankAcceptDate";
    static final String BANK_MESSAGE_NODE_NAME = "bankMessage";
    static final String BANK_MESSAGE_AUTHOR_NODE_NAME = "bankMessageAuthor";
    static final String DOC_DATE_NODE_NAME = "docDate";
    static final String DOC_DISPATCH_DATE_NODE_NAME = "docDispatchDate";
    static final String DOC_ID_NODE_NAME = "docId";
    static final String DOC_NUMBER_NODE_NAME = "docNumber";
    static final String DOCUMENT_SUM_NODE_NAME = "documentSum";
    static final String EXTERNAL_ID_NODE_NAME = "externalId";
    static final String EXTERNAL_UPG_ID_NODE_NAME = "externalUPGId";
    static final String LAST_MODIFY_DATE_NODE_NAME = "lastModifyDate";
    static final String MESSAGE_ONLY_FOR_BANK_NODE_NAME = "messageOnlyForBank";
    static final String NEW_STATE_NODE_NAME = "newState";
    static final String OPERATION_DATE_NODE_NAME = "operationDate";
    static final String OPERATION_TYPE_NODE_NAME = "operationType";
    static final String ORG_ID_NODE_NAME = "orgId";
    static final String ORG_NAME_NODE_NAME = "orgName";
    static final String PAYER_ACCOUNT_NODE_NAME = "payerAccount";
    static final String PAYER_BANK_BIC_NODE_NAME = "payerBankBic";
    static final String PAYER_BANK_CORR_ACCOUNT_NODE_NAME = "payerBankCorrAccount";
    static final String PAYER_BANK_NAME_NODE_NAME = "payerBankName";
    static final String PAYER_ID_NODE_NAME = "payerId";
    static final String PAYER_INN_NODE_NAME = "payerInn";
    static final String PAYER_NAME_NODE_NAME = "payerName";
    static final String PAYMENT_CONDITION_NODE_NAME = "paymentCondition";
    static final String PAYMENT_CONDITION_CODE_NODE_NAME = "paymentConditionCode";
    static final String PAYMENT_KIND_NODE_NAME = "paymentKind";
    static final String PAYMENT_KIND_CODE_NODE_NAME = "paymentKindCode";
    static final String PAYMENT_PRIORITY_NODE_NAME = "paymentPriority";
    static final String PAYMENT_PURPOSE_NODE_NAME = "paymentPurpose";
    static final String QUEUE_DATE_NODE_NAME = "queueDate";
    static final String RECEIVER_ACCOUNT_NODE_NAME = "receiverAccount";
    static final String RECEIVER_BANK_BIC_NODE_NAME = "receiverBankBic";
    static final String RECEIVER_BANK_CORR_ACCOUNT_NODE_NAME = "receiverBankCorrAccount";
    static final String RECEIVER_BANK_NAME_NODE_NAME = "receiverBankName";
    static final String RECEIVER_INN_NODE_NAME = "receiverInn";
    static final String RECEIVER_NAME_NODE_NAME = "receiverName";
    static final String RECIEVE_D_PAYER_BANK_NODE_NAME = "recieveDPayerBank";
    static final String RESERV_23_NODE_NAME = "reserv23";
    static final String TEMPLATE_NODE_NAME = "template";
    static final String UIP_NODE_NAME = "uip";
    static final String VAT_CALCULATION_RULE_NODE_NAME = "vatCalculationRule";
    static final String VAT_RATE_NODE_NAME = "vatRate";
    static final String VAT_SUM_NODE_NAME = "vatSum";
    static final String PART_PAYMENTS_NODE_NAME = "partPayments";
    static final String PAY_REQUEST_PART_NODE_NAME = "PayRequestPart";
    static final String REQUEST_ID_ATTR_NAME = "requestId";
    static final String VERSION_ATTR_NAME = "version";
    static final String XMLNS_ATTR_NAME = "xmlns";
    static final String UPG_NAMESPACE_NAME = "xmlns:upg";
    static final String UPG_RAIF_NAMESPACE_NAME = "xmlns:upgRaif";
    static final String XSI_NAMESPACE_NAME = "xmlns:xsi";

    private final String ACCEPT_TERM_DEFAULT_VALUE = "";
    private final String ACCOUNT_ID_DEFAULT_VALUE = "";
    private final String BANK_ACCEPT_DATE_DEFAULT_VALUE = "";
    private final String BANK_MESSAGE_DEFAULT_VALUE = "";
    private final String BANK_MESSAGE_AUTHOR_DEFAULT_VALUE = "";
    private final String DOC_DATE_DEFAULT_VALUE = "";
    private final String DOC_DISPATCH_DATE_DEFAULT_VALUE = "";
    private final String DOC_ID_DEFAULT_VALUE = "";
    private final String DOC_NUMBER_DEFAULT_VALUE = "";
    private final String DOCUMENT_SUM_DEFAULT_VALUE = "";
    private final String EXTERNAL_ID_DEFAULT_VALUE = "";
    private final String EXTERNAL_UPG_ID_DEFAULT_VALUE = "";
    private final String LAST_MODIFY_DATE_DEFAULT_VALUE = "";
    private final String MESSAGE_ONLY_FOR_BANK_DEFAULT_VALUE = "";
    private final String NEW_STATE_DEFAULT_VALUE = "";
    private final String OPERATION_DATE_DEFAULT_VALUE = "";
    private final String OPERATION_TYPE_DEFAULT_VALUE = "";
    private final String ORG_ID_DEFAULT_VALUE = "";
    private final String ORG_NAME_DEFAULT_VALUE = "";
    private final String PAYER_ACCOUNT_DEFAULT_VALUE = "";
    private final String PAYER_BANK_BIC_DEFAULT_VALUE = "";
    private final String PAYER_BANK_CORR_ACCOUNT_DEFAULT_VALUE = "";
    private final String PAYER_BANK_NAME_DEFAULT_VALUE = "";
    private final String PAYER_ID_DEFAULT_VALUE = "";
    private final String PAYER_INN_DEFAULT_VALUE = "";
    private final String PAYER_NAME_DEFAULT_VALUE = "";
    private final String PAYMENT_CONDITION_DEFAULT_VALUE = "";
    private final String PAYMENT_CONDITION_CODE_DEFAULT_VALUE = "";
    private final String PAYMENT_KIND_DEFAULT_VALUE = "";
    private final String PAYMENT_KIND_CODE_DEFAULT_VALUE = "";
    private final String PAYMENT_PRIORITY_DEFAULT_VALUE = "";
    private final String PAYMENT_PURPOSE_DEFAULT_VALUE = "";
    private final String QUEUE_DATE_DEFAULT_VALUE = "";
    private final String RECEIVER_ACCOUNT_DEFAULT_VALUE = "";
    private final String RECEIVER_BANK_BIC_DEFAULT_VALUE = "";
    private final String RECEIVER_BANK_CORR_ACCOUNT_DEFAULT_VALUE = "";
    private final String RECEIVER_BANK_NAME_DEFAULT_VALUE = "";
    private final String RECEIVER_INN_DEFAULT_VALUE = "";
    private final String RECEIVER_NAME_DEFAULT_VALUE = "";
    private final String RECIEVE_D_PAYER_BANK_DEFAULT_VALUE = "";
    private final String RESERV_23_DEFAULT_VALUE = "";
    private final String TEMPLATE_DEFAULT_VALUE = "";
    private final String UIP_DEFAULT_VALUE = "";
    private final String VAT_CALCULATION_RULE_DEFAULT_VALUE = "";
    private final String VAT_RATE_DEFAULT_VALUE = "";
    private final String VAT_SUM_DEFAULT_VALUE = "";
    private final String REQUEST_ID_ATTR_DEFAULT_VALUE = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa";
    private final String VERSION_ATTR_DEFAULT_VALUE = "1";
    private final String XMLNS_ATTR_DEFAULT_VALUE = "http://bssys.com/sbns/integration";
    private final String UPG_NAMESPACE_DEFAULT_VALUE = "http://bssys.com/upg/request";
    private final String UPG_RAIF_NAMESPACE_DEFAULT_VALUE = "http://bssys.com/upg/request/raif";
    private final String XSI_NAMESPACE_DEFAULT_VALUE = "http://www.w3.org/2001/XMLSchema-instance";

    private final int ACCOUNT_ID_NODE_MAX_LENGTH = 36;
    private final int BANK_MESSAGE_AUTHOR_NODE_MAX_LENGTH = 1024;
    private final int DOC_ID_NODE_MAX_LENGTH = 36;
    private final int DOC_NUMBER_NODE_MAX_LENGTH = 64;
    private final int EXTERNAL_ID_NODE_MAX_LENGTH = 64;
    private final int EXTERNAL_UPG_ID_NODE_MAX_LENGTH = 64;
    private final int NEW_STATE_ID_NODE_MAX_LENGTH = 255;
    private final int OPERATION_TYPE_NODE_MAX_LENGTH = 2;
    private final int ORG_ID_NODE_MAX_LENGTH = 36;
    private final int ORG_NAME_NODE_MAX_LENGTH = 355;
    private final int PAYER_ACCOUNT_NODE_MAX_LENGTH = 20;
    private final int PAYER_BANK_BIC_NODE_MAX_LENGTH = 64;
    private final int PAYER_BANK_CORR_ACCOUNT_NODE_MAX_LENGTH = 50;
    private final int PAYER_BANK_NAME_NODE_MAX_LENGTH = 770;
    private final int PAYER_ID_NODE_MAX_LENGTH = 36;
    private final int PAYER_INN_NODE_MAX_LENGTH = 15;
    private final int PAYER_NAME_NODE_MAX_LENGTH = 355;
    private final int PAYMENT_CONDITION_NODE_MAX_LENGTH = 30;
    private final int PAYMENT_CONDITION_CODE_NODE_MAX_LENGTH = 1;
    private final int PAYMENT_KIND_NODE_MAX_LENGTH = 15;
    private final int PAYMENT_KIND_CODE_NODE_MAX_LENGTH = 1;
    private final int PAYMENT_PRIORITY_NODE_MAX_LENGTH = 2;
    private final int PAYMENT_PURPOSE_NODE_MAX_LENGTH = 210;
    private final int RECEIVER_ACCOUNT_NODE_MAX_LENGTH = 20;
    private final int RECEIVER_BANK_BIC_NODE_MAX_LENGTH = 64;
    private final int RECEIVER_BANK_CORR_ACCOUNT_NODE_MAX_LENGTH = 20;
    private final int RECEIVER_BANK_NAME_NODE_MAX_LENGTH = 770;
    private final int RECEIVER_INN_NODE_MAX_LENGTH = 15;
    private final int RECEIVER_NAME_NODE_MAX_LENGTH = 355;
    private final int RESERV_23_NODE_MAX_LENGTH = 255;
    private final int UIP_NODE_MAX_LENGTH = 50;
    private final int VAT_CALCULATION_RULE_NODE_MAX_LENGTH = 15;

    private String acceptTerm = ACCEPT_TERM_DEFAULT_VALUE;
    private String accountId = ACCOUNT_ID_DEFAULT_VALUE;
    private String bankAcceptDate = BANK_ACCEPT_DATE_DEFAULT_VALUE;
    private String bankMessage = BANK_MESSAGE_DEFAULT_VALUE;
    private String bankMessageAuthor = BANK_MESSAGE_AUTHOR_DEFAULT_VALUE;
    private String docDate = DOC_DATE_DEFAULT_VALUE;
    private String docDispatchDate = DOC_DISPATCH_DATE_DEFAULT_VALUE;
    private String docId = DOC_ID_DEFAULT_VALUE;
    private String docNumber = DOC_NUMBER_DEFAULT_VALUE;
    private String documentSum = DOCUMENT_SUM_DEFAULT_VALUE;
    private String externalId = EXTERNAL_ID_DEFAULT_VALUE;
    private String externalUPGId = EXTERNAL_UPG_ID_DEFAULT_VALUE;
    private String lastModifyDate = LAST_MODIFY_DATE_DEFAULT_VALUE;
    private String messageOnlyForBank = MESSAGE_ONLY_FOR_BANK_DEFAULT_VALUE;
    private String newState = NEW_STATE_DEFAULT_VALUE;
    private String operationDate = OPERATION_DATE_DEFAULT_VALUE;
    private String operationType = OPERATION_TYPE_DEFAULT_VALUE;
    private String orgId = ORG_ID_DEFAULT_VALUE;
    private String orgName = ORG_NAME_DEFAULT_VALUE;
    private String payerAccount = PAYER_ACCOUNT_DEFAULT_VALUE;
    private String payerBankBic = PAYER_BANK_BIC_DEFAULT_VALUE;
    private String payerBankCorrAccount = PAYER_BANK_CORR_ACCOUNT_DEFAULT_VALUE;
    private String payerBankName = PAYER_BANK_NAME_DEFAULT_VALUE;
    private String payerId = PAYER_ID_DEFAULT_VALUE;
    private String payerInn = PAYER_INN_DEFAULT_VALUE;
    private String payerName = PAYER_NAME_DEFAULT_VALUE;
    private String paymentCondition = PAYMENT_CONDITION_DEFAULT_VALUE;
    private String paymentConditionCode = PAYMENT_CONDITION_CODE_DEFAULT_VALUE;
    private String paymentKind = PAYMENT_KIND_DEFAULT_VALUE;
    private String paymentKindCode = PAYMENT_KIND_CODE_DEFAULT_VALUE;
    private String paymentPriority = PAYMENT_PRIORITY_DEFAULT_VALUE;
    private String paymentPurpose = PAYMENT_PURPOSE_DEFAULT_VALUE;
    private String queueDate = QUEUE_DATE_DEFAULT_VALUE;
    private String receiverAccount = RECEIVER_ACCOUNT_DEFAULT_VALUE;
    private String receiverBankBic = RECEIVER_BANK_BIC_DEFAULT_VALUE;
    private String receiverBankCorrAccount = RECEIVER_BANK_CORR_ACCOUNT_DEFAULT_VALUE;
    private String receiverBankName = RECEIVER_BANK_NAME_DEFAULT_VALUE;
    private String receiverInn = RECEIVER_INN_DEFAULT_VALUE;
    private String receiverName = RECEIVER_NAME_DEFAULT_VALUE;
    private String recieveDPayerBank = RECIEVE_D_PAYER_BANK_DEFAULT_VALUE;
    private String reserv23 = RESERV_23_DEFAULT_VALUE;
    private String template = TEMPLATE_DEFAULT_VALUE;
    private String uip = UIP_DEFAULT_VALUE;
    private String vatCalculationRule = VAT_CALCULATION_RULE_DEFAULT_VALUE;
    private String vatRate = VAT_RATE_DEFAULT_VALUE;
    private String vatSum = VAT_SUM_DEFAULT_VALUE;
    private List<PayRequestPartData> partPayments = new ArrayList<>();
    private SignCollection signCollection;

    public String attrRequestId = REQUEST_ID_ATTR_DEFAULT_VALUE;
    public final String attrVersion = VERSION_ATTR_DEFAULT_VALUE;
    public final String attrXmlns = XMLNS_ATTR_DEFAULT_VALUE;

    public final String namespaceUpg = UPG_NAMESPACE_DEFAULT_VALUE;
    public final String namespaceUpgRaif = UPG_RAIF_NAMESPACE_DEFAULT_VALUE;
    public final String namespaceXsi = XSI_NAMESPACE_DEFAULT_VALUE;

    @Override
    public void check() {
        checkStringLength(ACCOUNT_ID_NODE_NAME, getAccountId(), ACCOUNT_ID_NODE_MAX_LENGTH);
        checkStringLength(BANK_MESSAGE_AUTHOR_DEFAULT_VALUE, getBankMessageAuthor(),
                BANK_MESSAGE_AUTHOR_NODE_MAX_LENGTH);
        checkStringLength(DOC_ID_DEFAULT_VALUE, getDocId(), DOC_ID_NODE_MAX_LENGTH);
        checkStringLength(DOC_NUMBER_DEFAULT_VALUE, getDocNumber(), DOC_NUMBER_NODE_MAX_LENGTH);
        checkStringLength(EXTERNAL_ID_DEFAULT_VALUE, getExternalId(), EXTERNAL_ID_NODE_MAX_LENGTH);
        checkStringLength(EXTERNAL_UPG_ID_DEFAULT_VALUE, getExternalUPGId(), EXTERNAL_UPG_ID_NODE_MAX_LENGTH);
        checkStringLength(NEW_STATE_DEFAULT_VALUE, getNewState(), NEW_STATE_ID_NODE_MAX_LENGTH);
        checkStringLength(OPERATION_TYPE_DEFAULT_VALUE, getOperationType(), OPERATION_TYPE_NODE_MAX_LENGTH);
        checkStringLength(ORG_ID_DEFAULT_VALUE, getOrgId(), ORG_ID_NODE_MAX_LENGTH);
        checkStringLength(ORG_NAME_DEFAULT_VALUE, getOrgName(), ORG_NAME_NODE_MAX_LENGTH);
        checkStringLength(PAYER_ACCOUNT_DEFAULT_VALUE, getPayerAccount(), PAYER_ACCOUNT_NODE_MAX_LENGTH);
        checkStringLength(PAYER_BANK_BIC_DEFAULT_VALUE, getPayerBankBic(), PAYER_BANK_BIC_NODE_MAX_LENGTH);
        checkStringLength(PAYER_BANK_CORR_ACCOUNT_DEFAULT_VALUE, getPayerBankCorrAccount(),
                PAYER_BANK_CORR_ACCOUNT_NODE_MAX_LENGTH);
        checkStringLength(PAYER_BANK_NAME_DEFAULT_VALUE, getPayerBankName(), PAYER_BANK_NAME_NODE_MAX_LENGTH);
        checkStringLength(PAYER_ID_DEFAULT_VALUE, getPayerId(), PAYER_ID_NODE_MAX_LENGTH);
        checkStringLength(PAYER_INN_DEFAULT_VALUE, getPayerInn(), PAYER_INN_NODE_MAX_LENGTH);
        checkStringLength(PAYER_NAME_DEFAULT_VALUE, getPayerName(), PAYER_NAME_NODE_MAX_LENGTH);
        checkStringLength(PAYMENT_CONDITION_DEFAULT_VALUE, getPaymentCondition(), PAYMENT_CONDITION_NODE_MAX_LENGTH);
        checkStringLength(PAYMENT_CONDITION_CODE_DEFAULT_VALUE, getPaymentConditionCode(),
                PAYMENT_CONDITION_CODE_NODE_MAX_LENGTH);
        checkStringLength(PAYMENT_KIND_DEFAULT_VALUE, getPaymentKind(), PAYMENT_KIND_NODE_MAX_LENGTH);
        checkStringLength(PAYMENT_KIND_CODE_DEFAULT_VALUE, getPaymentKindCode(), PAYMENT_KIND_CODE_NODE_MAX_LENGTH);
        checkStringLength(PAYMENT_PRIORITY_DEFAULT_VALUE, getPaymentPriority(), PAYMENT_PRIORITY_NODE_MAX_LENGTH);
        checkStringLength(PAYMENT_PURPOSE_DEFAULT_VALUE, getPaymentPurpose(), PAYMENT_PURPOSE_NODE_MAX_LENGTH);
        checkStringLength(RECEIVER_ACCOUNT_DEFAULT_VALUE, getReceiverAccount(), RECEIVER_ACCOUNT_NODE_MAX_LENGTH);
        checkStringLength(RECEIVER_BANK_BIC_DEFAULT_VALUE, getReceiverBankBic(), RECEIVER_BANK_BIC_NODE_MAX_LENGTH);
        checkStringLength(RECEIVER_BANK_CORR_ACCOUNT_DEFAULT_VALUE, getReceiverBankCorrAccount(),
                RECEIVER_BANK_CORR_ACCOUNT_NODE_MAX_LENGTH);
        checkStringLength(RECEIVER_BANK_NAME_DEFAULT_VALUE, getReceiverBankName(), RECEIVER_BANK_NAME_NODE_MAX_LENGTH);
        checkStringLength(RECEIVER_INN_DEFAULT_VALUE, getReceiverInn(), RECEIVER_INN_NODE_MAX_LENGTH);
        checkStringLength(RECEIVER_NAME_DEFAULT_VALUE, getReceiverName(), RECEIVER_NAME_NODE_MAX_LENGTH);
        checkStringLength(RESERV_23_DEFAULT_VALUE, getReserv23(), RESERV_23_NODE_MAX_LENGTH);
        checkStringLength(UIP_DEFAULT_VALUE, getUip(), UIP_NODE_MAX_LENGTH);
        checkStringLength(VAT_CALCULATION_RULE_DEFAULT_VALUE, getVatCalculationRule(),
                VAT_CALCULATION_RULE_NODE_MAX_LENGTH);

        for (PayRequestPartData part: partPayments) {
            part.check();
        }
    }

    public PayRequestDto getDto(String responseId) {
        PayRequestDto dto = new PayRequestDto();

        dto.setAcceptTerm(getAcceptTerm());
        dto.setAccountId(getAccountId());
        dto.setBankAcceptDate(getBankAcceptDate());
        dto.setBankMessage(getBankMessage());
        dto.setBankMessageAuthor(getBankMessageAuthor());
        dto.setDocDate(getDocDate());
        dto.setDocDispatchDate(getDocDispatchDate());
        dto.setDocId(getDocId());
        dto.setDocNumber(getDocNumber());
        dto.setDocumentSum(getDocumentSum());
        dto.setExternalId(getExternalId());
        dto.setExternalUPGId(getExternalUPGId());
        dto.setLastModifyDate(getLastModifyDate());
        dto.setMessageOnlyForBank(getMessageOnlyForBank());
        dto.setNewState(getNewState());
        dto.setOperationDate(getOperationDate());
        dto.setOperationType(getOperationType());
        dto.setOrgId(getOrgId());
        dto.setOrgName(getOrgName());
        dto.setPayerAccount(getPayerAccount());
        dto.setPayerBankBic(getPayerBankBic());
        dto.setPayerBankCorrAccount(getPayerBankCorrAccount());
        dto.setPayerBankName(getPayerBankName());
        dto.setPayerId(getPayerId());
        dto.setPayerInn(getPayerInn());
        dto.setPayerName(getPayerName());
        dto.setPaymentCondition(getPaymentCondition());
        dto.setPaymentConditionCode(getPaymentConditionCode());
        dto.setPaymentKind(getPaymentKind());
        dto.setPaymentKindCode(getPaymentKindCode());
        dto.setPaymentPriority(getPaymentPriority());
        dto.setPaymentPurpose(getPaymentPurpose());
        dto.setQueueDate(getQueueDate());
        dto.setReceiverAccount(getReceiverAccount());
        dto.setReceiverBankBic(getReceiverBankBic());
        dto.setReceiverBankCorrAccount(getReceiverBankCorrAccount());
        dto.setReceiverBankName(getReceiverBankName());
        dto.setReceiverInn(getReceiverInn());
        dto.setReceiverName(getReceiverName());
        dto.setRecieveDPayerBank(getRecieveDPayerBank());
        dto.setReserv23(getReserv23());
        dto.setTemplate(getTemplate());
        dto.setUip(getUip());
        dto.setVatCalculationRule(getVatCalculationRule());
        dto.setVatRate(getVatRate());
        dto.setVatSum(getVatSum());

        List<PayRequestPartDto> partPaymentsDto = dto.getPartPayments();
        for (PayRequestPartData payRequestPart: this.getPartPayments()) {
            partPaymentsDto.add(payRequestPart.getDto());
        }

        if (this.getSignCollection() != null) {
            dto.setSignCollection(this.getSignCollection().getDto());
        }

        dto.setRequestId(dto.getAttrRequestId());
        dto.setResponseId(responseId);
        dto.setRequestName("Pay request");

        return dto;
    }

    public SignCollection getSignCollection() {
        return signCollection;
    }

    public void setSignCollection(SignCollection signCollection) {
        this.signCollection = signCollection;
    }

    public void setAcceptTerm(String acceptTerm) {
        this.acceptTerm = checkNull(acceptTerm, ACCEPT_TERM_DEFAULT_VALUE);
    }

    public void setAccountId(String accountId) {
        this.accountId = checkNull(accountId, ACCOUNT_ID_DEFAULT_VALUE);
    }

    public void setBankAcceptDate(String bankAcceptDate) {
        this.bankAcceptDate = checkNull(bankAcceptDate, BANK_ACCEPT_DATE_DEFAULT_VALUE);
    }

    public void setBankMessage(String bankMessage) {
        this.bankMessage = checkNull(bankMessage, BANK_MESSAGE_DEFAULT_VALUE);
    }

    public void setBankMessageAuthor(String bankMessageAuthor) {
        this.bankMessageAuthor = checkNull(bankMessageAuthor, BANK_MESSAGE_AUTHOR_DEFAULT_VALUE);
    }

    public void setDocDate(String docDate) {
        this.docDate = checkNull(docDate, DOC_DATE_DEFAULT_VALUE);
    }

    public void setDocDispatchDate(String docDispatchDate) {
        this.docDispatchDate = checkNull(docDispatchDate, DOC_DISPATCH_DATE_DEFAULT_VALUE);
    }

    public void setDocId(String docId) {
        this.docId = checkNull(docId, DOC_ID_DEFAULT_VALUE);
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = checkNull(docNumber, DOC_NUMBER_DEFAULT_VALUE);
    }

    public void setDocumentSum(String documentSum) {
        this.documentSum = checkNull(documentSum, DOCUMENT_SUM_DEFAULT_VALUE);
    }

    public void setExternalId(String externalId) {
        this.externalId = checkNull(externalId, EXTERNAL_ID_DEFAULT_VALUE);
    }

    public void setExternalUPGId(String externalUPGId) {
        this.externalUPGId = checkNull(externalUPGId, EXTERNAL_UPG_ID_DEFAULT_VALUE);
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = checkNull(lastModifyDate, LAST_MODIFY_DATE_DEFAULT_VALUE);
    }

    public void setMessageOnlyForBank(String messageOnlyForBank) {
        this.messageOnlyForBank = checkNull(messageOnlyForBank, MESSAGE_ONLY_FOR_BANK_DEFAULT_VALUE);
    }

    public void setNewState(String newState) {
        this.newState = checkNull(newState, NEW_STATE_DEFAULT_VALUE);
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = checkNull(operationDate, OPERATION_DATE_DEFAULT_VALUE);
    }

    public void setOperationType(String operationType) {
        this.operationType = checkNull(operationType, OPERATION_TYPE_DEFAULT_VALUE);
    }

    public void setOrgId(String orgId) {
        this.orgId = checkNull(orgId, ORG_ID_DEFAULT_VALUE);
    }

    public void setOrgName(String orgName) {
        this.orgName = checkNull(orgName, ORG_NAME_DEFAULT_VALUE);
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = checkNull(payerAccount, PAYER_ACCOUNT_DEFAULT_VALUE);
    }

    public void setPayerBankBic(String payerBankBic) {
        this.payerBankBic = checkNull(payerBankBic, PAYER_BANK_BIC_DEFAULT_VALUE);
    }

    public void setPayerBankCorrAccount(String payerBankCorrAccount) {
        this.payerBankCorrAccount = checkNull(payerBankCorrAccount, PAYER_BANK_CORR_ACCOUNT_DEFAULT_VALUE);
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = checkNull(payerBankName, PAYER_BANK_NAME_DEFAULT_VALUE);
    }

    public void setPayerId(String payerId) {
        this.payerId = checkNull(payerId, PAYER_ID_DEFAULT_VALUE);
    }

    public void setPayerInn(String payerInn) {
        this.payerInn = checkNull(payerInn, PAYER_INN_DEFAULT_VALUE);
    }

    public void setPayerName(String payerName) {
        this.payerName = checkNull(payerName, PAYER_NAME_DEFAULT_VALUE);
    }

    public void setPaymentCondition(String paymentCondition) {
        this.paymentCondition = checkNull(paymentCondition, PAYMENT_CONDITION_DEFAULT_VALUE);
    }

    public void setPaymentConditionCode(String paymentConditionCode) {
        this.paymentConditionCode = checkNull(paymentConditionCode, PAYMENT_CONDITION_CODE_DEFAULT_VALUE);
    }

    public void setPaymentKind(String paymentKind) {
        this.paymentKind = checkNull(paymentKind, PAYMENT_KIND_DEFAULT_VALUE);
    }

    public void setPaymentKindCode(String paymentKindCode) {
        this.paymentKindCode = checkNull(paymentKindCode, PAYMENT_KIND_CODE_DEFAULT_VALUE);
    }

    public void setPaymentPriority(String paymentPriority) {
        this.paymentPriority = checkNull(paymentPriority, PAYMENT_PRIORITY_DEFAULT_VALUE);
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = checkNull(paymentPurpose, PAYMENT_PURPOSE_DEFAULT_VALUE);
    }

    public void setQueueDate(String queueDate) {
        this.queueDate = checkNull(queueDate, QUEUE_DATE_DEFAULT_VALUE);
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = checkNull(receiverAccount, RECEIVER_ACCOUNT_DEFAULT_VALUE);
    }

    public void setReceiverBankBic(String receiverBankBic) {
        this.receiverBankBic = checkNull(receiverBankBic, RECEIVER_BANK_BIC_DEFAULT_VALUE);
    }

    public void setReceiverBankCorrAccount(String receiverBankCorrAccount) {
        this.receiverBankCorrAccount = checkNull(receiverBankCorrAccount, RECEIVER_BANK_CORR_ACCOUNT_DEFAULT_VALUE);
    }

    public void setReceiverBankName(String receiverBankName) {
        this.receiverBankName = checkNull(receiverBankName, RECEIVER_BANK_NAME_DEFAULT_VALUE);
    }

    public void setReceiverInn(String receiverInn) {
        this.receiverInn = checkNull(receiverInn, RECEIVER_INN_DEFAULT_VALUE);
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = checkNull(receiverName, RECEIVER_NAME_DEFAULT_VALUE);
    }

    public void setRecieveDPayerBank(String recieveDPayerBank) {
        this.recieveDPayerBank = checkNull(recieveDPayerBank, RECIEVE_D_PAYER_BANK_DEFAULT_VALUE);
    }

    public void setReserv23(String reserv23) {
        this.reserv23 = checkNull(reserv23, RESERV_23_DEFAULT_VALUE);
    }

    public void setTemplate(String template) {
        this.template = checkNull(template, TEMPLATE_DEFAULT_VALUE);
    }

    public void setUip(String uip) {
        this.uip = checkNull(uip, UIP_DEFAULT_VALUE);
    }

    public void setVatCalculationRule(String vatCalculationRule) {
        this.vatCalculationRule = checkNull(vatCalculationRule, VAT_CALCULATION_RULE_DEFAULT_VALUE);
    }

    public void setVatRate(String vatRate) {
        this.vatRate = checkNull(vatRate, VAT_RATE_DEFAULT_VALUE);
    }

    public void setVatSum(String vatSum) {
        this.vatSum = checkNull(vatSum, VAT_SUM_DEFAULT_VALUE);
    }

    public void setPartPayments(List<PayRequestPartData> partPayments) {
        this.partPayments = partPayments;
    }

    public void setAttrRequestId(String attrRequestId) {
        this.attrRequestId = checkNull(attrRequestId, REQUEST_ID_ATTR_DEFAULT_VALUE);
    }

    public String getAcceptTerm() {
        return acceptTerm;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getBankAcceptDate() {
        return bankAcceptDate;
    }

    public String getBankMessage() {
        return bankMessage;
    }

    public String getBankMessageAuthor() {
        return bankMessageAuthor;
    }

    public String getDocDate() {
        return docDate;
    }

    public String getDocDispatchDate() {
        return docDispatchDate;
    }

    public String getDocId() {
        return docId;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public String getDocumentSum() {
        return documentSum;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getExternalUPGId() {
        return externalUPGId;
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public String getMessageOnlyForBank() {
        return messageOnlyForBank;
    }

    public String getNewState() {
        return newState;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public String getPayerBankBic() {
        return payerBankBic;
    }

    public String getPayerBankCorrAccount() {
        return payerBankCorrAccount;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public String getPayerId() {
        return payerId;
    }

    public String getPayerInn() {
        return payerInn;
    }

    public String getPayerName() {
        return payerName;
    }

    public String getPaymentCondition() {
        return paymentCondition;
    }

    public String getPaymentConditionCode() {
        return paymentConditionCode;
    }

    public String getPaymentKind() {
        return paymentKind;
    }

    public String getPaymentKindCode() {
        return paymentKindCode;
    }

    public String getPaymentPriority() {
        return paymentPriority;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public String getQueueDate() {
        return queueDate;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public String getReceiverBankBic() {
        return receiverBankBic;
    }

    public String getReceiverBankCorrAccount() {
        return receiverBankCorrAccount;
    }

    public String getReceiverBankName() {
        return receiverBankName;
    }

    public String getReceiverInn() {
        return receiverInn;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getRecieveDPayerBank() {
        return recieveDPayerBank;
    }

    public String getReserv23() {
        return reserv23;
    }

    public String getTemplate() {
        return template;
    }

    public String getUip() {
        return uip;
    }

    public String getVatCalculationRule() {
        return vatCalculationRule;
    }

    public String getVatRate() {
        return vatRate;
    }

    public String getVatSum() {
        return vatSum;
    }

    public List<PayRequestPartData> getPartPayments() {
        return partPayments;
    }

    public String getAttrRequestId() {
        return attrRequestId;
    }

    public String getAttrVersion() {
        return attrVersion;
    }

    public String getAttrXmlns() {
        return attrXmlns;
    }

    public String getNamespaceUpg() {
        return namespaceUpg;
    }

    public String getNamespaceUpgRaif() {
        return namespaceUpgRaif;
    }

    public String getNamespaceXsi() {
        return namespaceXsi;
    }


}
