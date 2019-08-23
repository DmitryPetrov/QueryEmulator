package com.emulator.domain.soap.requests.getrequeststatus.statement;

import java.util.ArrayList;
import java.util.List;

public class Statement {

    public final String XMLNS_ATTR_NAME = "xmlns";

    public final String ACCEPT_DATE_NODE_NAME = "acceptDate";
    public final String ACCOUNT_NODE_NAME = "account";
    public final String ACCOUNT_ID_NODE_NAME = "accountId";
    public final String ACTUAL_NODE_NAME = "actual";
    public final String AUTHOR_NODE_NAME = "author";
    public final String BANK_BIC_NODE_NAME = "bankBIC";
    public final String BANK_NAME_NODE_NAME = "bankName";
    public final String BANK_NAME_BIC_NODE_NAME = "bankNameBIC";
    public final String CARD_1_SUM_NODE_NAME = "card1Sum";
    public final String CARD_2_SUM_NODE_NAME = "card2Sum";
    public final String CREDIT_RETURN_NODE_NAME = "creditReturn";
    public final String CREDIT_RETURN_NAT_NODE_NAME = "creditReturnNat";
    public final String CURR_CODE_NODE_NAME = "currCode";
    public final String CURR_ISO_CODE_NODE_NAME = "currIsoCode";
    public final String DEBET_RETURN_NODE_NAME = "debetReturn";
    public final String DEBET_RETURN_NAT_NODE_NAME = "debetReturnNat";
    public final String DOC_COMMENT_NODE_NAME = "docComment";
    public final String DOC_DATE_NODE_NAME = "docDate";
    public final String DOC_NUMBER_NODE_NAME = "docNumber";
    public final String DOC_TYPE_VERSION_NODE_NAME = "docTypeVersion";
    public final String EXTERNAL_ID_NODE_NAME = "externalId";
    public final String FROM_DATE_NODE_NAME = "fromDate";
    public final String HASHCODE_NODE_NAME = "hashcode";
    public final String INBOUND_BALANCE_NODE_NAME = "inboundBalance";
    public final String INBOUND_BALANCE_NAT_NODE_NAME = "inboundBalanceNat";
    public final String INDEXED_NODE_NAME = "indexed";
    public final String IS_FINAL_NODE_NAME = "isFinal";
    public final String LAST_OPERATION_DATE_NODE_NAME = "lastOperationDate";
    public final String OLD_DOC_ID_NODE_NAME = "oldDocId";
    public final String ORG_INN_NODE_NAME = "orgInn";
    public final String ORG_NAME_NODE_NAME = "orgName";
    public final String OUTBOUND_BALANCE_NODE_NAME = "outboundBalance";
    public final String OUTBOUND_BALANCE_NAT_NODE_NAME = "outboundBalanceNat";
    public final String PLAN_OUTBOUND_BALANCE_NODE_NAME = "planOutboundBalance";
    public final String PREV_OPERATION_DATE_NODE_NAME = "prevOperationDate";
    public final String RATE_IN_NODE_NAME = "rateIn";
    public final String RATE_IN_FULL_NODE_NAME = "rateInFull";
    public final String RATE_OUT_NODE_NAME = "rateOut";
    public final String RATE_OUT_FULL_NODE_NAME = "rateOutFull";
    public final String REQUEST_ID_NODE_NAME = "requestId";
    public final String SEIZURE_AMOUNT_NODE_NAME = "seizureAmount";
    public final String SEIZURE_WHOLE_AMOUNT_NODE_NAME = "seizureWholeAmount";
    public final String TO_DATE_NODE_NAME = "toDate";
    public final String OPERATIONS_NODE_NAME = "operations";
    public final String OPERATION_NODE_NAME = "Operation";
    public final String SIGN_COLLECTION_NODE_NAME = "signCollection";
    public final String SIGN_COLLECTION_INNER_NODE_NAME = "SignCollection";


    private String attrXmlns = "";

    private String acceptDate= "";
    private String account = "";
    private String accountId = "";
    private String actual = "";
    private String author = "";
    private String bankBIC = "";
    private String bankName = "";
    private String bankNameBIC = "";
    private String card1Sum = "";
    private String card2Sum = "";
    private String creditReturn = "";
    private String creditReturnNat = "";
    private String currCode = "";
    private String currIsoCode = "";
    private String debetReturn = "";
    private String debetReturnNat = "";
    private String docComment = "";
    private String docDate = "";
    private String docNumber = "";
    private String docTypeVersion = "";
    private String externalId = "";
    private String fromDate = "";
    private String hashcode = "";
    private String inboundBalance = "";
    private String inboundBalanceNat = "";
    private String indexed = "";
    private String isFinal = "";
    private String lastOperationDate = "";
    private String oldDocId = "";
    private String orgInn = "";
    private String orgName = "";
    private String outboundBalance = "";
    private String outboundBalanceNat = "";
    private String planOutboundBalance = "";
    private String prevOperationDate = "";
    private String rateIn = "";
    private String rateInFull = "";
    private String rateOut = "";
    private String rateOutFull = "";
    private String requestId = "";
    private String seizureAmount = "";
    private String seizureWholeAmount = "";
    private String toDate = "";
    private List<Operation> operations = new ArrayList<>();
    private SignCollection signCollection = new SignCollection();

    public String getAttrXmlns() {
        return attrXmlns;
    }

    public void setAttrXmlns(String attrXmlns) {
        this.attrXmlns = attrXmlns;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBankBIC() {
        return bankBIC;
    }

    public void setBankBIC(String bankBIC) {
        this.bankBIC = bankBIC;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNameBIC() {
        return bankNameBIC;
    }

    public void setBankNameBIC(String bankNameBIC) {
        this.bankNameBIC = bankNameBIC;
    }

    public String getCard1Sum() {
        return card1Sum;
    }

    public void setCard1Sum(String card1Sum) {
        this.card1Sum = card1Sum;
    }

    public String getCard2Sum() {
        return card2Sum;
    }

    public void setCard2Sum(String card2Sum) {
        this.card2Sum = card2Sum;
    }

    public String getCreditReturn() {
        return creditReturn;
    }

    public void setCreditReturn(String creditReturn) {
        this.creditReturn = creditReturn;
    }

    public String getCreditReturnNat() {
        return creditReturnNat;
    }

    public void setCreditReturnNat(String creditReturnNat) {
        this.creditReturnNat = creditReturnNat;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public String getCurrIsoCode() {
        return currIsoCode;
    }

    public void setCurrIsoCode(String currIsoCode) {
        this.currIsoCode = currIsoCode;
    }

    public String getDebetReturn() {
        return debetReturn;
    }

    public void setDebetReturn(String debetReturn) {
        this.debetReturn = debetReturn;
    }

    public String getDebetReturnNat() {
        return debetReturnNat;
    }

    public void setDebetReturnNat(String debetReturnNat) {
        this.debetReturnNat = debetReturnNat;
    }

    public String getDocComment() {
        return docComment;
    }

    public void setDocComment(String docComment) {
        this.docComment = docComment;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public String getInboundBalance() {
        return inboundBalance;
    }

    public void setInboundBalance(String inboundBalance) {
        this.inboundBalance = inboundBalance;
    }

    public String getInboundBalanceNat() {
        return inboundBalanceNat;
    }

    public void setInboundBalanceNat(String inboundBalanceNat) {
        this.inboundBalanceNat = inboundBalanceNat;
    }

    public String getIndexed() {
        return indexed;
    }

    public void setIndexed(String indexed) {
        this.indexed = indexed;
    }

    public String getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(String isFinal) {
        this.isFinal = isFinal;
    }

    public String getLastOperationDate() {
        return lastOperationDate;
    }

    public void setLastOperationDate(String lastOperationDate) {
        this.lastOperationDate = lastOperationDate;
    }

    public String getOldDocId() {
        return oldDocId;
    }

    public void setOldDocId(String oldDocId) {
        this.oldDocId = oldDocId;
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

    public String getOutboundBalance() {
        return outboundBalance;
    }

    public void setOutboundBalance(String outboundBalance) {
        this.outboundBalance = outboundBalance;
    }

    public String getOutboundBalanceNat() {
        return outboundBalanceNat;
    }

    public void setOutboundBalanceNat(String outboundBalanceNat) {
        this.outboundBalanceNat = outboundBalanceNat;
    }

    public String getPlanOutboundBalance() {
        return planOutboundBalance;
    }

    public void setPlanOutboundBalance(String planOutboundBalance) {
        this.planOutboundBalance = planOutboundBalance;
    }

    public String getPrevOperationDate() {
        return prevOperationDate;
    }

    public void setPrevOperationDate(String prevOperationDate) {
        this.prevOperationDate = prevOperationDate;
    }

    public String getRateIn() {
        return rateIn;
    }

    public void setRateIn(String rateIn) {
        this.rateIn = rateIn;
    }

    public String getRateInFull() {
        return rateInFull;
    }

    public void setRateInFull(String rateInFull) {
        this.rateInFull = rateInFull;
    }

    public String getRateOut() {
        return rateOut;
    }

    public void setRateOut(String rateOut) {
        this.rateOut = rateOut;
    }

    public String getRateOutFull() {
        return rateOutFull;
    }

    public void setRateOutFull(String rateOutFull) {
        this.rateOutFull = rateOutFull;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSeizureAmount() {
        return seizureAmount;
    }

    public void setSeizureAmount(String seizureAmount) {
        this.seizureAmount = seizureAmount;
    }

    public String getSeizureWholeAmount() {
        return seizureWholeAmount;
    }

    public void setSeizureWholeAmount(String seizureWholeAmount) {
        this.seizureWholeAmount = seizureWholeAmount;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public boolean add(Operation operation) {
        return operations.add(operation);
    }

    public SignCollection getSignCollection() {
        return signCollection;
    }
}
