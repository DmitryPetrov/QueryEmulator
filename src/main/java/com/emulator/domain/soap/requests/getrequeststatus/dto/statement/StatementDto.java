package com.emulator.domain.soap.requests.getrequeststatus.dto.statement;

import com.emulator.domain.soap.signcollection.dto.SignCollectionDto;

import java.util.ArrayList;
import java.util.List;

public class StatementDto {

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
    private List<OperationDto> operations = new ArrayList<>();
    private SignCollectionDto signCollection;

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

    public List<OperationDto> getOperations() {
        return operations;
    }

    public SignCollectionDto getSignCollection() {
        if (this.signCollection == null) {
            this.setSignCollection(new SignCollectionDto());
        }
        return signCollection;
    }

    public void setSignCollection(SignCollectionDto signCollection) {
        this.signCollection = signCollection;
    }
}
