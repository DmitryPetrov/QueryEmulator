package com.emulator.domain.soap.statementrequest;

import com.emulator.domain.entity.RequestParameters;
import com.emulator.domain.frontend.response.statementrequest.DataAccountDto;
import com.emulator.domain.frontend.response.statementrequest.StatementRequestDataDto;
import com.emulator.exception.RequestParameterLengthException;

import java.util.ArrayList;
import java.util.List;

public class StatementRequestData extends RequestParameters {

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
    private List<DataAccount> accounts = new ArrayList<>();
    private SignCollection signCollection;

    public final String requestAttrRequestId = "1852ccae-e9b2-48bf-adbd-6027653f194d";
    public final String requestAttrVersion = "1";
    public final String requestNameSpaceUpgValue = "http://bssys.com/upg/request";
    public final String requestNameSpaceUpgRaifValue = "http://bssys.com/upg/request/raif";
    public final String requestNameSpaceXsiValue = "http://www.w3.org/2001/XMLSchema-instance";

    public final String statementRequestAttrXmlns = "http://bssys.com/sbns/integration";

    @Override
    public void check() throws RequestParameterLengthException {
        checkStringLength("bankMessageAuthor", this.bankMessageAuthor, 1024);
        checkStringLength("docId", this.docId, 36);
        checkStringLength("docNumber", this.docNumber, 64);
        checkStringLength("docTypeVersion", this.docTypeVersion, 12);
        checkStringLength("externalId", this.externalId, 64);
        checkStringLength("externalUPGId", this.externalUPGId, 64);
        checkStringLength("orgId", this.orgId, 36);
        checkStringLength("orgInn", this.orgInn, 15);
        checkStringLength("orgName", this.orgName, 355);

        for (DataAccount accountData : this.accounts) {
            accountData.check();
        }
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = checkNull(acceptDate, ACCEPT_DATE_DEFAULT_VALUE);
    }

    public String getBankMessage() {
        return bankMessage;
    }

    public void setBankMessage(String bankMessage) {
        this.bankMessage = checkNull(bankMessage, BANK_MESSAGE_DEFAULT_VALUE);
    }

    public String getBankMessageAuthor() {
        return bankMessageAuthor;
    }

    public void setBankMessageAuthor(String bankMessageAuthor) {
        this.bankMessageAuthor = checkNull(bankMessageAuthor, BANK_MESSAGE_AUTHOR_DEFAULT_VALUE);
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = checkNull(docDate, DOC_DATE_DEFAULT_VALUE);
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = checkNull(docId, DOC_ID_DEFAULT_VALUE);
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = checkNull(docNumber, DOC_NUMBER_DEFAULT_VALUE);
    }


    public String getDocTypeVersion() {
        return docTypeVersion;
    }

    public void setDocTypeVersion(String docTypeVersion) {
        this.docTypeVersion = checkNull(docTypeVersion, DOC_TYPE_VERSION_DEFAULT_VALUE);
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = checkNull(externalId, EXTERNAL_ID_DEFAULT_VALUE);
    }

    public String getExternalUPGId() {
        return externalUPGId;
    }

    public void setExternalUPGId(String externalUPGId) {
        this.externalUPGId = checkNull(externalUPGId, EXTERNAL_UPG_ID_DEFAULT_VALUE);
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = checkNull(fromDate, FROM_DATE_DEFAULT_VALUE);
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = checkNull(lastModifyDate, LAST_MODIFY_DATE_DEFAULT_VALUE);
    }

    public String getMessageOnlyForBank() {
        return messageOnlyForBank;
    }

    public void setMessageOnlyForBank(String messageOnlyForBank) {
        this.messageOnlyForBank = checkNull(lastModifyDate, MESSAGE_ONLY_FOR_BANK_DEFAULT_VALUE);
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = checkNull(orgId, ORG_ID_DEFAULT_VALUE);
    }

    public String getOrgInn() {
        return orgInn;
    }

    public void setOrgInn(String orgInn) {
        this.orgInn = checkNull(orgInn, ORG_INN_DEFAULT_VALUE);
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = checkNull(orgName, ORG_NAME_DEFAULT_VALUE);
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = checkNull(template, TEMPLATE_DEFAULT_VALUE);
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = checkNull(toDate, TO_DATE_DEFAULT_VALUE);
    }

    public List<DataAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<DataAccount> accounts) {
        this.accounts = accounts;
    }

    public SignCollection getSignCollection() {
        if (this.signCollection == null) {
            this.signCollection = new SignCollection();
        }
        return signCollection;
    }

    public void setSignCollection(SignCollection signCollection) {
        this.signCollection = signCollection;
    }

    public StatementRequestDataDto getDto() {
        StatementRequestDataDto dto = new StatementRequestDataDto();

        dto.setAttrRequestId(this.requestAttrRequestId);
        dto.setAttrVersion(this.requestAttrVersion);
        dto.setAcceptDate(this.getAcceptDate());
        dto.setBankMessage(this.getBankMessage());
        dto.setBankMessageAuthor(this.getBankMessageAuthor());
        dto.setDocDate(this.getDocDate());
        dto.setDocId(this.getDocId());
        dto.setDocNumber(this.getDocNumber());
        dto.setDocTypeVersion(this.getDocTypeVersion());
        dto.setExternalId(this.getExternalId());
        dto.setExternalUPGId(this.getExternalUPGId());
        dto.setFromDate(this.getFromDate());
        dto.setLastModifyDate(this.getLastModifyDate());
        dto.setMessageOnlyForBank(this.getMessageOnlyForBank());
        dto.setOrgId(this.getOrgId());
        dto.setOrgInn(this.getOrgInn());
        dto.setOrgName(this.getOrgName());
        dto.setTemplate(this.getTemplate());
        dto.setToDate(this.getToDate());

        List<DataAccountDto> accountsDto = new ArrayList<>(getAccounts().size());
        for (DataAccount account: getAccounts()) {
            accountsDto.add(account.getDto());
        }
        dto.setAccounts(accountsDto);

        dto.setSignCollection(this.getSignCollection().getDto());

        return dto;
    }

}
