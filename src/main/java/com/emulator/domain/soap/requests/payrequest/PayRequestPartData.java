package com.emulator.domain.soap.requests.payrequest;

import com.emulator.domain.soap.requests.RequestParameters;
import com.emulator.domain.soap.requests.payrequest.dto.PayRequestPartDto;

public class PayRequestPartData extends RequestParameters {

    static final String PAY_REQUEST_PART_NODE_NAME = "PayRequestPart";
    static final String ORDER_DATE_NODE_NAME = "orderDate";
    static final String ORDER_NUMBER_NODE_NAME = "orderNumber";
    static final String PART_PAYMENT_SUM_NODE_NAME = "partPaymentSum";
    static final String PAYMENT_BALANCE_NODE_NAME = "paymentBalance";
    static final String STR_NUM_NODE_NAME = "strNum";

    private final String ORDER_DATE_DEFAULT_VALUE = "";
    private final String ORDER_NUMBER_DEFAULT_VALUE = "";
    private final String PART_PAYMENT_SUM_DEFAULT_VALUE = "";
    private final String PAYMENT_BALANCE_DEFAULT_VALUE = "";
    private final String STR_NUM_DEFAULT_VALUE = "";

    private final int ORDER_NUMBER_NODE_MAX_LENGTH = 6;

    private String orderDate = ORDER_DATE_DEFAULT_VALUE;
    private String orderNumber = ORDER_NUMBER_DEFAULT_VALUE;
    private String partPaymentSum = PART_PAYMENT_SUM_DEFAULT_VALUE;
    private String paymentBalance = PAYMENT_BALANCE_DEFAULT_VALUE;
    private String strNum = STR_NUM_DEFAULT_VALUE;

    @Override
    public void check() {
        checkStringLength(ORDER_NUMBER_NODE_NAME, getOrderNumber(), ORDER_NUMBER_NODE_MAX_LENGTH);
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = checkNull(orderDate, ORDER_DATE_DEFAULT_VALUE);
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = checkNull(orderNumber, ORDER_NUMBER_DEFAULT_VALUE);
    }

    public String getPartPaymentSum() {
        return partPaymentSum;
    }

    public void setPartPaymentSum(String partPaymentSum) {
        this.partPaymentSum = checkNull(partPaymentSum, PART_PAYMENT_SUM_DEFAULT_VALUE);
    }

    public String getPaymentBalance() {
        return paymentBalance;
    }

    public void setPaymentBalance(String paymentBalance) {
        this.paymentBalance = checkNull(paymentBalance, PAYMENT_BALANCE_DEFAULT_VALUE);
    }

    public String getStrNum() {
        return strNum;
    }

    public void setStrNum(String strNum) {
        this.strNum = checkNull(strNum, STR_NUM_DEFAULT_VALUE);
    }

    PayRequestPartDto getDto() {
        PayRequestPartDto dto = new PayRequestPartDto();
        dto.setOrderDate(getOrderDate());
        dto.setOrderNumber(getOrderNumber());
        dto.setPartPaymentSum(getPartPaymentSum());
        dto.setPaymentBalance(getPaymentBalance());
        dto.setStrNum(getStrNum());
        return dto;
    }

    @Override
    public String toString() {
        return "PayRequestPartData{" +
                "orderDate='" + orderDate + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", partPaymentSum='" + partPaymentSum + '\'' +
                ", paymentBalance='" + paymentBalance + '\'' +
                ", strNum='" + strNum + '\'' +
                '}';
    }
}
