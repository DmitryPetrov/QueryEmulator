package com.emulator.domain.soap.requests.payrequest.dto;

import com.emulator.domain.soap.requests.DataTransferObject;
import com.emulator.domain.soap.signcollection.dto.SignCollectionDto;

import java.util.ArrayList;
import java.util.List;

public class PayRequestDto extends DataTransferObject {

    private String attrRequestId = "";

    private String acceptTerm = "";
    private String accountId = "";
    private String bankAcceptDate = "";
    private String bankMessage = "";
    private String bankMessageAuthor = "";
    private String docDate = "";
    private String docDispatchDate = "";
    private String docId = "";
    private String docNumber = "";
    private String documentSum = "";
    private String externalId = "";
    private String externalUPGId = "";
    private String lastModifyDate = "";
    private String messageOnlyForBank = "";
    private String newState = "";
    private String operationDate = "";
    private String operationType = "";
    private String orgId = "";
    private String orgName = "";
    private String payerAccount = "";
    private String payerBankBic = "";
    private String payerBankCorrAccount = "";
    private String payerBankName = "";
    private String payerId = "";
    private String payerInn = "";
    private String payerName = "";
    private String paymentCondition = "";
    private String paymentConditionCode = "";
    private String paymentKind = "";
    private String paymentKindCode = "";
    private String paymentPriority = "";
    private String paymentPurpose = "";
    private String queueDate = "";
    private String receiverAccount = "";
    private String receiverBankBic = "";
    private String receiverBankCorrAccount = "";
    private String receiverBankName = "";
    private String receiverInn = "";
    private String receiverName = "";
    private String recieveDPayerBank = "";
    private String reserv23 = "";
    private String template = "";
    private String uip = "";
    private String vatCalculationRule = "";
    private String vatRate = "";
    private String vatSum = "";
    private List<PayRequestPartDto> partPayments = new ArrayList<>();
    private SignCollectionDto signCollection;

    public SignCollectionDto getSignCollection() {
        if (this.signCollection == null) {
            this.setSignCollection(new SignCollectionDto());
        }
        return signCollection;
    }

    public void setSignCollection(SignCollectionDto signCollection) {
        this.signCollection = signCollection;
    }

    public String getAttrRequestId() {
        return attrRequestId;
    }

    public void setAttrRequestId(String attrRequestId) {
        this.attrRequestId = attrRequestId;
    }

    public String getAcceptTerm() {
        return acceptTerm;
    }

    public void setAcceptTerm(String acceptTerm) {
        this.acceptTerm = acceptTerm;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBankAcceptDate() {
        return bankAcceptDate;
    }

    public void setBankAcceptDate(String bankAcceptDate) {
        this.bankAcceptDate = bankAcceptDate;
    }

    public String getBankMessage() {
        return bankMessage;
    }

    public void setBankMessage(String bankMessage) {
        this.bankMessage = bankMessage;
    }

    public String getBankMessageAuthor() {
        return bankMessageAuthor;
    }

    public void setBankMessageAuthor(String bankMessageAuthor) {
        this.bankMessageAuthor = bankMessageAuthor;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getDocDispatchDate() {
        return docDispatchDate;
    }

    public void setDocDispatchDate(String docDispatchDate) {
        this.docDispatchDate = docDispatchDate;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocumentSum() {
        return documentSum;
    }

    public void setDocumentSum(String documentSum) {
        this.documentSum = documentSum;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getExternalUPGId() {
        return externalUPGId;
    }

    public void setExternalUPGId(String externalUPGId) {
        this.externalUPGId = externalUPGId;
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getMessageOnlyForBank() {
        return messageOnlyForBank;
    }

    public void setMessageOnlyForBank(String messageOnlyForBank) {
        this.messageOnlyForBank = messageOnlyForBank;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getPayerBankBic() {
        return payerBankBic;
    }

    public void setPayerBankBic(String payerBankBic) {
        this.payerBankBic = payerBankBic;
    }

    public String getPayerBankCorrAccount() {
        return payerBankCorrAccount;
    }

    public void setPayerBankCorrAccount(String payerBankCorrAccount) {
        this.payerBankCorrAccount = payerBankCorrAccount;
    }

    public String getPayerBankName() {
        return payerBankName;
    }

    public void setPayerBankName(String payerBankName) {
        this.payerBankName = payerBankName;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayerInn() {
        return payerInn;
    }

    public void setPayerInn(String payerInn) {
        this.payerInn = payerInn;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPaymentCondition() {
        return paymentCondition;
    }

    public void setPaymentCondition(String paymentCondition) {
        this.paymentCondition = paymentCondition;
    }

    public String getPaymentConditionCode() {
        return paymentConditionCode;
    }

    public void setPaymentConditionCode(String paymentConditionCode) {
        this.paymentConditionCode = paymentConditionCode;
    }

    public String getPaymentKind() {
        return paymentKind;
    }

    public void setPaymentKind(String paymentKind) {
        this.paymentKind = paymentKind;
    }

    public String getPaymentKindCode() {
        return paymentKindCode;
    }

    public void setPaymentKindCode(String paymentKindCode) {
        this.paymentKindCode = paymentKindCode;
    }

    public String getPaymentPriority() {
        return paymentPriority;
    }

    public void setPaymentPriority(String paymentPriority) {
        this.paymentPriority = paymentPriority;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public String getQueueDate() {
        return queueDate;
    }

    public void setQueueDate(String queueDate) {
        this.queueDate = queueDate;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public String getReceiverBankBic() {
        return receiverBankBic;
    }

    public void setReceiverBankBic(String receiverBankBic) {
        this.receiverBankBic = receiverBankBic;
    }

    public String getReceiverBankCorrAccount() {
        return receiverBankCorrAccount;
    }

    public void setReceiverBankCorrAccount(String receiverBankCorrAccount) {
        this.receiverBankCorrAccount = receiverBankCorrAccount;
    }

    public String getReceiverBankName() {
        return receiverBankName;
    }

    public void setReceiverBankName(String receiverBankName) {
        this.receiverBankName = receiverBankName;
    }

    public String getReceiverInn() {
        return receiverInn;
    }

    public void setReceiverInn(String receiverInn) {
        this.receiverInn = receiverInn;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getRecieveDPayerBank() {
        return recieveDPayerBank;
    }

    public void setRecieveDPayerBank(String recieveDPayerBank) {
        this.recieveDPayerBank = recieveDPayerBank;
    }

    public String getReserv23() {
        return reserv23;
    }

    public void setReserv23(String reserv23) {
        this.reserv23 = reserv23;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getUip() {
        return uip;
    }

    public void setUip(String uip) {
        this.uip = uip;
    }

    public String getVatCalculationRule() {
        return vatCalculationRule;
    }

    public void setVatCalculationRule(String vatCalculationRule) {
        this.vatCalculationRule = vatCalculationRule;
    }

    public String getVatRate() {
        return vatRate;
    }

    public void setVatRate(String vatRate) {
        this.vatRate = vatRate;
    }

    public String getVatSum() {
        return vatSum;
    }

    public void setVatSum(String vatSum) {
        this.vatSum = vatSum;
    }

    public List<PayRequestPartDto> getPartPayments() {
        return partPayments;
    }

}
