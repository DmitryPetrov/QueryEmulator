package com.emulator.domain.soap.requests.payrequest.dto;

import com.emulator.domain.soap.requests.DataTransferObject;

public class PayRequestPartDto extends DataTransferObject {

    private String orderDate = "";
    private String orderNumber = "";
    private String partPaymentSum = "";
    private String paymentBalance = "";
    private String strNum = "";

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPartPaymentSum() {
        return partPaymentSum;
    }

    public void setPartPaymentSum(String partPaymentSum) {
        this.partPaymentSum = partPaymentSum;
    }

    public String getPaymentBalance() {
        return paymentBalance;
    }

    public void setPaymentBalance(String paymentBalance) {
        this.paymentBalance = paymentBalance;
    }

    public String getStrNum() {
        return strNum;
    }

    public void setStrNum(String strNum) {
        this.strNum = strNum;
    }
}
