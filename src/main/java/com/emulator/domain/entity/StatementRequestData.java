package com.emulator.domain.entity;

import com.emulator.domain.soap.exception.RequestParameterLengthException;

public class StatementRequestData {

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
    private String account = "";
    private String bankBIC = "";
    private String bankName = "";
    private String accountOrgName = "";
    private String signCollectionBankMessage = "";
    private String digestName = "";
    private String sign = "";

    public final String requestAttrRequestId = "1852ccae-e9b2-48bf-adbd-6027653f194d";
    public final String requestAttrVersion = "1";
    public final String requestNameSpaceUpg = "xmlns:upg";
    public final String requestNameSpaceUpgValue = "http://bssys.com/upg/request";
    public final String requestNameSpaceUpgRaif = "xmlns:upgRaif";
    public final String requestNameSpaceUpgRaifValue = "http://bssys.com/upg/request/raif";
    public final String requestNameSpaceXsi = "xmlns:xsi";
    public final String requestNameSpaceXsiValue = "http://www.w3.org/2001/XMLSchema-instance";

    public final String statementRequestAttrXmlns = "http://bssys.com/sbns/integration";

    private RequestParameterLengthException exception = null;

    private String validate(String value, String defaultValue) {
        if ((value == null) || (value.equals("(initialState)"))) {
            return defaultValue;
        }
        return value;
    }

    private void validateStringLength(String paramName, String string, int maxLength) {
        if (string.length() > maxLength) {
            exception = new RequestParameterLengthException("String is too long");
            exception.setMaxLength(maxLength);
            exception.setParameterName(paramName);
        }
    }

    public void check() throws RequestParameterLengthException {
        if(exception != null) {
            throw exception;
        }
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = validate(acceptDate, "");
    }

    public String getBankMessage() {
        return bankMessage;
    }

    public void setBankMessage(String bankMessage) {
        this.bankMessage = validate(bankMessage, "");
    }

    public String getBankMessageAuthor() {
        return bankMessageAuthor;
    }

    public void setBankMessageAuthor(String bankMessageAuthor) {
        validateStringLength("bankMessageAuthor", bankMessageAuthor, 1024);
        this.bankMessageAuthor = validate(bankMessageAuthor, "");
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = validate(docDate, "2018-05-15T17:08:00");
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        validateStringLength("docId", docId, 36);
        this.docId = validate(docId, "40702810800000005897");
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        validateStringLength("docNumber", docNumber, 64);
        this.docNumber = validate(docNumber, "78");
    }


    public String getDocTypeVersion() {
        return docTypeVersion;
    }

    public void setDocTypeVersion(String docTypeVersion) {
        validateStringLength("docTypeVersion", docTypeVersion, 12);
        this.docTypeVersion = validate(docTypeVersion, "");
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        validateStringLength("externalId", externalId, 64);
        this.externalId = validate(externalId, "");
    }

    public String getExternalUPGId() {
        return externalUPGId;
    }

    public void setExternalUPGId(String externalUPGId) {
        validateStringLength("externalUPGId", externalUPGId, 64);
        this.externalUPGId = validate(externalUPGId, "");
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = validate(fromDate, "2018-05-07T00:00:00.715+03:00");
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = validate(lastModifyDate, "");
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
        validateStringLength("orgId", orgId, 36);
        this.orgId = validate(orgId, "0ce353c5-9a53-497d-ad02-df1fb6c37feb");
    }

    public String getOrgInn() {
        return orgInn;
    }

    public void setOrgInn(String orgInn) {
        validateStringLength("orgInn", orgInn, 15);
        this.orgInn = validate(orgInn, "7842170415");
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        validateStringLength("orgName", orgName, 355);
        this.orgName = validate(orgName, "АО \"РЗК\"");
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = validate(template, "");
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = validate(toDate, "2018-05-14T00:00:00.715+03:00");
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        validateStringLength("account", account, 20);
        this.account = validate(account, "40702810800000005897");
    }

    public String getBankBIC() {
        return bankBIC;
    }

    public void setBankBIC(String bankBIC) {
        validateStringLength("bankBIC", bankBIC, 64);
        this.bankBIC = validate(bankBIC, "044030861");
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        validateStringLength("bankName", bankName, 500);
        this.bankName = validate(bankName, "АО &quot;АБ &quot;РОССИЯ&quot");
    }

    public String getAccountOrgName() {
        return accountOrgName;
    }

    public void setAccountOrgName(String accountOrgName) {
        validateStringLength("accountOrgName", accountOrgName, 355);
        this.accountOrgName = validate(accountOrgName, "");
    }

    public String getSignCollectionBankMessage() {
        return signCollectionBankMessage;
    }

    public void setSignCollectionBankMessage(String signCollectionBankMessage) {
        this.signCollectionBankMessage = validate(signCollectionBankMessage, "");
    }

    public String getDigestName() {
        return digestName;
    }

    public void setDigestName(String digestName) {
        validateStringLength("digestName", digestName, 255);
        this.digestName = validate(digestName, "");
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = validate(sign, "");
    }
}
