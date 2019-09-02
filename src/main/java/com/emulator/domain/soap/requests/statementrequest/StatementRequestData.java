package com.emulator.domain.soap.requests.statementrequest;

import com.emulator.domain.soap.requests.RequestParameters;
import com.emulator.domain.soap.requests.statementrequest.dto.DataAccountDto;
import com.emulator.domain.soap.requests.statementrequest.dto.StatementRequestDto;
import com.emulator.domain.soap.signcollection.SignCollection;
import com.emulator.exception.RequestParameterLengthException;

import java.util.ArrayList;
import java.util.List;

public class StatementRequestData extends RequestParameters {

    static final String REQUEST_NODE_NAME = "upg:Request";
    static final String MODELS_NODE_NAME = "upg:Models";
    static final String MODEL_NODE_NAME = "upg:Model";
    static final String STATEMENT_REQUEST_NODE_NAME = "StatementRequest";
    static final String ACCEPT_DATE_NODE_NAME = "acceptDate";
    static final String BANK_MESSAGE_NODE_NAME = "bankMessage";
    static final String BANK_MESSAGE_AUTOR_NODE_NAME = "bankMessageAuthor";
    static final String DOC_DATE_NODE_NAME = "docDate";
    static final String DOC_ID_NODE_NAME = "docId";
    static final String DOC_NUMBER_NODE_NAME = "docNumber";
    static final String DOC_TYPE_VERSION_NODE_NAME = "docTypeVersion";
    static final String EXTERNAL_ID_VERSION_NODE_NAME = "externalId";
    static final String EXTERNAL_UPG_ID_NODE_NAME = "externalUPGId";
    static final String FROM_DATE_NODE_NAME = "fromDate";
    static final String LAST_MODIFY_DATE_NODE_NAME = "lastModifyDate";
    static final String MESSAGE_ONLY_FOR_BANK_NODE_NAME = "messageOnlyForBank";
    static final String ORG_ID_NODE_NAME = "orgId";
    static final String ORG_INN_NODE_NAME = "orgInn";
    static final String ORG_NAME_NODE_NAME = "orgName";
    static final String TEMPLATE_NODE_NAME = "template";
    static final String TO_DATE_NODE_NAME = "toDate";
    static final String ACCOUNTS_NODE_NAME = "accounts";
    static final String ACC_NODE_NAME = "Acc";
    static final String REQUEST_ID_ATTR_NAME = "requestId";
    static final String VERSION_ATTR_NAME = "version";
    static final String XMLNS_ATTR_NAME = "xmlns";
    static final String UPG_NAMESPACE_NAME = "xmlns:upg";
    static final String UPG_RAIF_NAMESPACE_NAME = "xmlns:upgRaif";
    static final String XSI_NAMESPACE_NAME = "xmlns:xsi";
    
    private final String ACCEPT_DATE_NODE_DEFAULT_VALUE = "";
    private final String BANK_MESSAGE_NODE_DEFAULT_VALUE = "";
    private final String BANK_MESSAGE_AUTHOR_NODE_DEFAULT_VALUE = "";
    private final String DOC_DATE_NODE_DEFAULT_VALUE = "2018-05-15T17:08:00";
    private final String DOC_ID_NODE_DEFAULT_VALUE = "0b64c0df-1690-4c15-0000-000000000020";
    private final String DOC_NUMBER_NODE_DEFAULT_VALUE = "78";
    private final String DOC_TYPE_VERSION_NODE_DEFAULT_VALUE = "";
    private final String EXTERNAL_ID_NODE_DEFAULT_VALUE = "";
    private final String EXTERNAL_UPG_ID_NODE_DEFAULT_VALUE = "";
    private final String FROM_DATE_NODE_DEFAULT_VALUE = "2018-05-07T00:00:00.715+03:00";
    private final String LAST_MODIFY_DATE_NODE_DEFAULT_VALUE = "";
    private final String MESSAGE_ONLY_FOR_BANK_NODE_DEFAULT_VALUE = "";
    private final String ORG_ID_NODE_DEFAULT_VALUE = "0ce353c5-9a53-497d-ad02-df1fb6c37feb";
    private final String ORG_INN_NODE_DEFAULT_VALUE = "7842170415";
    private final String ORG_NAME_NODE_DEFAULT_VALUE = "АО РЗК";
    private final String TEMPLATE_NODE_DEFAULT_VALUE = "";
    private final String TO_DATE_NODE_DEFAULT_VALUE = "2018-05-14T00:00:00.715+03:00";
    private final String REQUEST_ID_ATTR_DEFAULT_VALUE = "1852ccae-e9b2-48bf-adbd-6027653f194d";
    private final String VERSION_ATTR_DEFAULT_VALUE = "1";
    private final String XMLNS_ATTR_DEFAULT_VALUE = "http://bssys.com/sbns/integration";
    private final String UPG_NAMESPACE_DEFAULT_VALUE = "http://bssys.com/upg/request";
    private final String UPG_RAIF_NAMESPACE_DEFAULT_VALUE = "http://bssys.com/upg/request/raif";
    private final String XSI_NAMESPACE_DEFAULT_VALUE = "http://www.w3.org/2001/XMLSchema-instance";

    private int BANK_MESSAGE_AUTHOR_NODE_MAX_LENGTH = 1024;
    private int DOC_ID_NODE_MAX_LENGTH = 36;
    private int DOC_NUMBER_NODE_MAX_LENGTH = 64;
    private int DOC_TYPE_VERSION_NODE_MAX_LENGTH = 12;
    private int EXTERNAL_ID_NODE_MAX_LENGTH = 64;
    private int EXTERNAL_UPG_ID_NODE_MAX_LENGTH = 64;
    private int ORG_ID_NODE_MAX_LENGTH = 36;
    private int ORG_INN_NODE_MAX_LENGTH = 15;
    private int ORG_NAME_NODE_MAX_LENGTH = 355;
    
    private String acceptDate = ACCEPT_DATE_NODE_DEFAULT_VALUE;
    private String bankMessage = BANK_MESSAGE_NODE_DEFAULT_VALUE;
    private String bankMessageAuthor = BANK_MESSAGE_AUTHOR_NODE_DEFAULT_VALUE;
    private String docDate = DOC_DATE_NODE_DEFAULT_VALUE;
    private String docId = DOC_ID_NODE_DEFAULT_VALUE;
    private String docNumber = DOC_NUMBER_NODE_DEFAULT_VALUE;
    private String docTypeVersion = DOC_TYPE_VERSION_NODE_DEFAULT_VALUE;
    private String externalId = EXTERNAL_ID_NODE_DEFAULT_VALUE;
    private String externalUPGId = EXTERNAL_UPG_ID_NODE_DEFAULT_VALUE;
    private String fromDate = FROM_DATE_NODE_DEFAULT_VALUE;
    private String lastModifyDate = LAST_MODIFY_DATE_NODE_DEFAULT_VALUE;
    private String messageOnlyForBank = MESSAGE_ONLY_FOR_BANK_NODE_DEFAULT_VALUE;
    private String orgId = ORG_ID_NODE_DEFAULT_VALUE;
    private String orgInn = ORG_INN_NODE_DEFAULT_VALUE;
    private String orgName = ORG_NAME_NODE_DEFAULT_VALUE;
    private String template = TEMPLATE_NODE_DEFAULT_VALUE;
    private String toDate = TO_DATE_NODE_DEFAULT_VALUE;
    private List<DataAccount> accounts = new ArrayList<>();
    private SignCollection signCollection;

    public final String attrRequestId = REQUEST_ID_ATTR_DEFAULT_VALUE;
    public final String attrVersion = VERSION_ATTR_DEFAULT_VALUE;
    public final String attrXmlns = XMLNS_ATTR_DEFAULT_VALUE;

    public final String namespaceUpg = UPG_NAMESPACE_DEFAULT_VALUE;
    public final String namespaceUpgRaif = UPG_RAIF_NAMESPACE_DEFAULT_VALUE;
    public final String namespaceXsi = XSI_NAMESPACE_DEFAULT_VALUE;


    @Override
    public void check() throws RequestParameterLengthException {
        checkStringLength(BANK_MESSAGE_AUTOR_NODE_NAME, getBankMessageAuthor(), BANK_MESSAGE_AUTHOR_NODE_MAX_LENGTH);
        checkStringLength(DOC_ID_NODE_NAME, getDocId(), DOC_ID_NODE_MAX_LENGTH);
        checkStringLength(DOC_NUMBER_NODE_NAME, getDocNumber(), DOC_NUMBER_NODE_MAX_LENGTH);
        checkStringLength(DOC_TYPE_VERSION_NODE_NAME, getDocTypeVersion(), DOC_TYPE_VERSION_NODE_MAX_LENGTH);
        checkStringLength(EXTERNAL_ID_VERSION_NODE_NAME, getExternalId(), EXTERNAL_ID_NODE_MAX_LENGTH);
        checkStringLength(EXTERNAL_UPG_ID_NODE_NAME, getExternalUPGId(), EXTERNAL_UPG_ID_NODE_MAX_LENGTH);
        checkStringLength(ORG_ID_NODE_NAME, getOrgId(), ORG_ID_NODE_MAX_LENGTH);
        checkStringLength(ORG_INN_NODE_NAME, getOrgInn(), ORG_INN_NODE_MAX_LENGTH);
        checkStringLength(ORG_NAME_NODE_NAME, getOrgName(), ORG_NAME_NODE_MAX_LENGTH);

        for (DataAccount accountData : getAccounts()) {
            accountData.check();
        }
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = checkNull(acceptDate, ACCEPT_DATE_NODE_DEFAULT_VALUE);
    }

    public String getBankMessage() {
        return bankMessage;
    }

    public void setBankMessage(String bankMessage) {
        this.bankMessage = checkNull(bankMessage, BANK_MESSAGE_NODE_DEFAULT_VALUE);
    }

    public String getBankMessageAuthor() {
        return bankMessageAuthor;
    }

    public void setBankMessageAuthor(String bankMessageAuthor) {
        this.bankMessageAuthor = checkNull(bankMessageAuthor, BANK_MESSAGE_AUTHOR_NODE_DEFAULT_VALUE);
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = checkNull(docDate, DOC_DATE_NODE_DEFAULT_VALUE);
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = checkNull(docId, DOC_ID_NODE_DEFAULT_VALUE);
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = checkNull(docNumber, DOC_NUMBER_NODE_DEFAULT_VALUE);
    }


    public String getDocTypeVersion() {
        return docTypeVersion;
    }

    public void setDocTypeVersion(String docTypeVersion) {
        this.docTypeVersion = checkNull(docTypeVersion, DOC_TYPE_VERSION_NODE_DEFAULT_VALUE);
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = checkNull(externalId, EXTERNAL_ID_NODE_DEFAULT_VALUE);
    }

    public String getExternalUPGId() {
        return externalUPGId;
    }

    public void setExternalUPGId(String externalUPGId) {
        this.externalUPGId = checkNull(externalUPGId, EXTERNAL_UPG_ID_NODE_DEFAULT_VALUE);
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = checkNull(fromDate, FROM_DATE_NODE_DEFAULT_VALUE);
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = checkNull(lastModifyDate, LAST_MODIFY_DATE_NODE_DEFAULT_VALUE);
    }

    public String getMessageOnlyForBank() {
        return messageOnlyForBank;
    }

    public void setMessageOnlyForBank(String messageOnlyForBank) {
        this.messageOnlyForBank = checkNull(lastModifyDate, MESSAGE_ONLY_FOR_BANK_NODE_DEFAULT_VALUE);
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = checkNull(orgId, ORG_ID_NODE_DEFAULT_VALUE);
    }

    public String getOrgInn() {
        return orgInn;
    }

    public void setOrgInn(String orgInn) {
        this.orgInn = checkNull(orgInn, ORG_INN_NODE_DEFAULT_VALUE);
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = checkNull(orgName, ORG_NAME_NODE_DEFAULT_VALUE);
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = checkNull(template, TEMPLATE_NODE_DEFAULT_VALUE);
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = checkNull(toDate, TO_DATE_NODE_DEFAULT_VALUE);
    }

    public List<DataAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<DataAccount> accounts) {
        this.accounts = accounts;
    }

    public SignCollection getSignCollection() {
        if (this.signCollection == null) {
            this.setSignCollection(new SignCollection());
        }
        return signCollection;
    }

    public void setSignCollection(SignCollection signCollection) {
        this.signCollection = signCollection;
    }

    StatementRequestDto getDto(String responseId) {
        StatementRequestDto dto = new StatementRequestDto();

        dto.setAttrRequestId(this.attrRequestId);
        dto.setAttrVersion(this.attrVersion);
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

        List<DataAccountDto> accountsDto = dto.getAccounts();
        for (DataAccount account: this.getAccounts()) {
            accountsDto.add(account.getDto());
        }

        dto.setSignCollection(this.getSignCollection().getDto());

        dto.setRequestId(dto.getAttrRequestId());
        dto.setResponseId(responseId);
        dto.setRequestName("Statement request");

        return dto;
    }

}
