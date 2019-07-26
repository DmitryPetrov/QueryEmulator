package com.emulator.domain.entity;

public class StatementRequestData {

    private String account;
    private String bankBIC;
    private String orgId;
    private String orgInn;
    private String orgName;
    private String docDate;
    private String docId;
    private String docNumber;
    private String fromDate;
    private String toDate;
    private String bankName;

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
        if ((value == null) || (value.equals("")) || (value.equals("(initialState)"))) {
            return defaultValue;
        }
        return value;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = validate(account, "40702810800000005897");
    }

    public String getBankBIC() {
        return bankBIC;
    }

    public void setBankBIC(String bankBIC) {
        this.bankBIC = validate(bankBIC, "044030861");
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = validate(orgId, "0ce353c5-9a53-497d-ad02-df1fb6c37feb");
    }

    public String getOrgInn() {
        return orgInn;
    }

    public void setOrgInn(String orgInn) {
        this.orgInn = validate(orgInn, "7842170415");
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = validate(orgName, "АО \"РЗК\"");
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
        this.docId = validate(docId, "40702810800000005897");
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = validate(docNumber, "78");
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = validate(fromDate, "2018-05-07T00:00:00.715+03:00");
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = validate(toDate, "2018-05-14T00:00:00.715+03:00");
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = validate(bankName, "АО &quot;АБ &quot;РОССИЯ&quot");
    }
}
