package com.emulator.domain.soap.requests.getrequeststatus.stateresponse;

import com.emulator.domain.soap.requests.getrequeststatus.dto.StateResponseDto;

public class StateResponse {

    public final String XMLNS_ATTR_NAME = "xmlns";

    public final String BANK_MESSAGE_NODE_NAME = "bankMessage";
    public final String BANK_MESSAGE_AUTHOR_NODE_NAME = "bankMessageAuthor";
    public final String CREATE_TIME_NODE_NAME = "createTime";
    public final String DOC_ID_NODE_NAME = "docId";
    public final String DOC_TYPE_NODE_NAME = "docType";
    public final String EXT_ID_NODE_NAME = "extId";
    public final String MESSAGE_ONLY_FOR_BANK_NODE_NAME = "messageOnlyForBank";
    public final String OPERATION_DATE_NODE_NAME = "operationDate";
    public final String STATE_NODE_NAME = "state";

    private String attrXmlns = "";

    private String bankMessage = "";
    private String bankMessageAuthor = "";
    private String createTime = "";
    private String docId = "";
    private String docType = "";
    private String extId = "";
    private String messageOnlyForBank = "";
    private String operationDate = "";
    private String state = "";

//    private AcceptPayRequest acceptPayRequest;
//    private AppReferenceReq appReferenceReq;
//    private BGAdditional bgAdditional;
//    private CashFunds cashFunds;
//    private ClDP clDP;
//    private ConfDocCertificate138I confDocCertificate138I;
//    private ContractReissue contractReissue;
//    private CurrBuy currBuy;
//    private CurrConv currConv;
//    private CurrDealCertificate138I currDealCertificate138I;
//    private CurrDealCertificate181I currDealCertificate181I;
//    private CurrSell currSell;
//    private DealContract181I dealContract181I;
//    private DealPassCon138I dealPassCon138I;
//    private DealPassCred138I dealPassCred138I;
//    private DealCred181I dealPassCred181I;
//    private DepositGrant depositGrant;
//    private DepositLongGrand depositLongGrand;
//    private DeregDP deregDP;
//    private DetachmentPayRoll detachmentPayRoll;
//    private FinalPayment finalPayment;
//    private FoundersAdditionalInfo foundersAdditionalInfo;
//    private IssueCards issueCards;
//    private LCAdditional lcAdditional;
//    private LCRubAdditional lcRubAdditional;
//    private LetterInBank letterInBank;
//    private MandatorySaleBox mandatorySaleBox;
//    private MbaReport mbaReport;
//    private MT103 mt103;
//    private MT202 mt202;
//    private NsoOpen nsoOpen;
//    private OnlineCurrencyConversion onlineCurrencyConversion;
//    private PayDocCur payDocCur;
//    private PayDocRu payDocRu;
//    private PayRequest payRequest;
//    private PayRoll payRoll;
//    private R050AdditionalFields r050;
//    private RegChanDP regChanDP;
//    private RegChanDPCC112 regChanDPCC112;
//    private UntypDocModel untypDocModel;

    public String getAttrXmlns() {
        return attrXmlns;
    }

    public void setAttrXmlns(String attrXmlns) {
        this.attrXmlns = attrXmlns;
    }

    public String getBankMessage() {
        return bankMessage;
    }

    public void setBankMessage(String bankMessage) {
        this.bankMessage = bankMessage;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public StateResponseDto getDto() {
        StateResponseDto dto = new StateResponseDto();
        dto.setAttrXmlns(this.getAttrXmlns());
        dto.setBankMessage(this.getBankMessage());
        dto.setDocId(this.getDocId());
        dto.setDocType(this.getDocType());
        dto.setExtId(this.getExtId());
        dto.setState(this.getState());

        return dto;
    }
}
