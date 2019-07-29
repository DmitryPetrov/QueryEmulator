package com.emulator.domain.soap.statementRequest;

import com.emulator.domain.soap.exception.RequestParameterLengthException;

public class StatementRequestData {

    private final String ACCEPT_DATE_DEFAULT_VALUE = "";
    private final String BANK_MESSAGE_DEFAULT_VALUE = "";
    private final String BANK_MESSAGE_AUTHOR_DEFAULT_VALUE = "";
    private final String DOC_DATE_DEFAULT_VALUE = "2018-05-15T17:08:00";
    private final String DOC_ID_DEFAULT_VALUE = "40702810800000005897";
    private final String DOC_NUMBER_DEFAULT_VALUE = "78";
    private final String DOC_TYPE_VERSION_DEFAULT_VALUE = "";
    private final String EXTERNAL_ID_DEFAULT_VALUE = "";
    private final String EXTERNAL_UPG_ID_DEFAULT_VALUE = "";
    private final String FROM_DATE_DEFAULT_VALUE = "2018-05-07T00:00:00.715+03:00";
    private final String LAST_MODIFY_DATE_DEFAULT_VALUE = "";
    private final String MESSAGE_ONLY_FOR_BANK_DEFAULT_VALUE = "";
    private final String ORG_ID_DEFAULT_VALUE = "0ce353c5-9a53-497d-ad02-df1fb6c37feb";
    private final String ORG_INN_DEFAULT_VALUE = "7842170415";
    private final String ORG_NAME_DEFAULT_VALUE = "ПАО МРСК Северного Кавказа";
    private final String TEMPLATE_DEFAULT_VALUE = "";
    private final String TO_DATE_DEFAULT_VALUE = "2018-05-14T00:00:00.715+03:00";
    private final String ACCOUNT_DEFAULT_VALUE = "40702810800000005897";
    private final String BANK_BIC_DEFAULT_VALUE = "044030861";
    private final String BANK_NAME_DEFAULT_VALUE = "АО &quot;АБ &quot;РОССИЯ&quot";
    private final String ACCOUNT_ORG_NAME_DEFAULT_VALUE = "ПАО МРСК Северного Кавказа";
    private final String SING_COLLECTION_BANK_MESSAGE_DEFAULT_VALUE = "";
    private final String DIGEST_NAME_DEFAULT_VALUE = "";
    private final String SIGN_DEFAULT_VALUE = "";

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

    private String validate(String value, String defaultValue) {
        if ((value == null) || (value.equals("(initialState)"))) {
            return defaultValue;
        }
        return value;
    }

    private void validateStringLength(String paramName, String string, int maxLength) throws RequestParameterLengthException {
        if (string.length() > maxLength) {
            RequestParameterLengthException exception = new RequestParameterLengthException("String is too long");
            exception.setMaxLength(maxLength);
            exception.setParameterName(paramName);
            throw exception;
        }
    }

    public void check() throws RequestParameterLengthException {
        validateStringLength("bankMessageAuthor", this.bankMessageAuthor, 1024);
        validateStringLength("docId", this.docId, 36);
        validateStringLength("docNumber", this.docNumber, 64);
        validateStringLength("docTypeVersion", this.docTypeVersion, 12);
        validateStringLength("externalId", this.externalId, 64);
        validateStringLength("externalUPGId", this.externalUPGId, 64);
        validateStringLength("orgId", this.orgId, 36);
        validateStringLength("orgInn", this.orgInn, 15);
        validateStringLength("orgName", this.orgName, 355);
        validateStringLength("account", this.account, 20);
        validateStringLength("bankBIC", this.bankBIC, 64);
        validateStringLength("bankName", this.bankName, 500);
        validateStringLength("accountOrgName", this.accountOrgName, 355);
        validateStringLength("digestName", this.digestName, 255);
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = validate(acceptDate, ACCEPT_DATE_DEFAULT_VALUE);
    }

    public String getBankMessage() {
        return bankMessage;
    }

    public void setBankMessage(String bankMessage) {
        this.bankMessage = validate(bankMessage, BANK_MESSAGE_DEFAULT_VALUE);
    }

    public String getBankMessageAuthor() {
        return bankMessageAuthor;
    }

    public void setBankMessageAuthor(String bankMessageAuthor) {
        this.bankMessageAuthor = validate(bankMessageAuthor, BANK_MESSAGE_AUTHOR_DEFAULT_VALUE);
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = validate(docDate, DOC_DATE_DEFAULT_VALUE);
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = validate(docId, DOC_ID_DEFAULT_VALUE);
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = validate(docNumber, DOC_NUMBER_DEFAULT_VALUE);
    }


    public String getDocTypeVersion() {
        return docTypeVersion;
    }

    public void setDocTypeVersion(String docTypeVersion) {
        this.docTypeVersion = validate(docTypeVersion, DOC_TYPE_VERSION_DEFAULT_VALUE);
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = validate(externalId, EXTERNAL_ID_DEFAULT_VALUE);
    }

    public String getExternalUPGId() {
        return externalUPGId;
    }

    public void setExternalUPGId(String externalUPGId) {
        this.externalUPGId = validate(externalUPGId, EXTERNAL_UPG_ID_DEFAULT_VALUE);
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = validate(fromDate, FROM_DATE_DEFAULT_VALUE);
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = validate(lastModifyDate, LAST_MODIFY_DATE_DEFAULT_VALUE);
    }

    public String getMessageOnlyForBank() {
        return messageOnlyForBank;
    }

    public void setMessageOnlyForBank(String messageOnlyForBank) {
        this.messageOnlyForBank = validate(lastModifyDate, MESSAGE_ONLY_FOR_BANK_DEFAULT_VALUE);
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = validate(orgId, ORG_ID_DEFAULT_VALUE);
    }

    public String getOrgInn() {
        return orgInn;
    }

    public void setOrgInn(String orgInn) {
        this.orgInn = validate(orgInn, ORG_INN_DEFAULT_VALUE);
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = validate(orgName, ORG_NAME_DEFAULT_VALUE);
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = validate(template, TEMPLATE_DEFAULT_VALUE);
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = validate(toDate, TO_DATE_DEFAULT_VALUE);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = validate(account, ACCOUNT_DEFAULT_VALUE);
    }

    public String getBankBIC() {
        return bankBIC;
    }

    public void setBankBIC(String bankBIC) {
        this.bankBIC = validate(bankBIC, BANK_BIC_DEFAULT_VALUE);
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = validate(bankName, BANK_NAME_DEFAULT_VALUE);
    }

    public String getAccountOrgName() {
        return accountOrgName;
    }

    public void setAccountOrgName(String accountOrgName) {
        this.accountOrgName = validate(accountOrgName, ACCOUNT_ORG_NAME_DEFAULT_VALUE);
    }

    public String getSignCollectionBankMessage() {
        return signCollectionBankMessage;
    }

    public void setSignCollectionBankMessage(String signCollectionBankMessage) {
        this.signCollectionBankMessage = validate(signCollectionBankMessage, SING_COLLECTION_BANK_MESSAGE_DEFAULT_VALUE);
    }

    public String getDigestName() {
        return digestName;
    }

    public void setDigestName(String digestName) {
        this.digestName = validate(digestName, DIGEST_NAME_DEFAULT_VALUE);
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = validate(sign, SIGN_DEFAULT_VALUE);
    }
}
