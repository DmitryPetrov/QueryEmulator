package com.emulator.domain.frontend.response.statementrequest;

import com.emulator.domain.frontend.response.DataTransferObject;

import java.util.ArrayList;
import java.util.List;

public class StatementRequestDto extends DataTransferObject {

    private String attrRequestId = "";
    private String attrVersion = "";

    private String acceptDate = "";
    private String bankMessage = "";
    private String bankMessageAuthor = "";
    private String docDate = "";
    private String docId = "";
    private String docNumber = "";
    private String docTypeVersion = "";
    private String externalId = "";
    private String externalUPGId = "";
    private String fromDate = "";
    private String lastModifyDate = "";
    private String messageOnlyForBank = "";
    private String orgId = "";
    private String orgInn = "";
    private String orgName = "";
    private String template = "";
    private String toDate = "";
    private List<DataAccountDto> accounts = new ArrayList<>();
    private SignCollectionDto signCollection;


    public String getAttrRequestId() {
        return attrRequestId;
    }

    public void setAttrRequestId(String attrRequestId) {
        this.attrRequestId = attrRequestId;
    }

    public String getAttrVersion() {
        return attrVersion;
    }

    public void setAttrVersion(String attrVersion) {
        this.attrVersion = attrVersion;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
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

    public String getDocTypeVersion() {
        return docTypeVersion;
    }

    public void setDocTypeVersion(String docTypeVersion) {
        this.docTypeVersion = docTypeVersion;
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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgInn() {
        return orgInn;
    }

    public void setOrgInn(String orgInn) {
        this.orgInn = orgInn;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<DataAccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<DataAccountDto> accounts) {
        this.accounts = accounts;
    }

    public SignCollectionDto getSignCollection() {
        return signCollection;
    }

    public void setSignCollection(SignCollectionDto signCollection) {
        this.signCollection = signCollection;
    }
}
