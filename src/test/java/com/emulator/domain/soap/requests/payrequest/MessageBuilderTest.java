package com.emulator.domain.soap.requests.payrequest;

import com.emulator.config.Config;
import com.emulator.domain.soap.signcollection.SignCollectionMessageBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;

class MessageBuilderTest {

    private final String XML_TAMPLATE = "<upg:Request xmlns:upg=\"http://bssys.com/upg/request\" " +
            "xmlns:upgRaif=\"http://bssys.com/upg/request/raif\" xmlns:xsi=\"http://www" +
            ".w3.org/2001/XMLSchema-instance\" requestId=\"requestId\" version=\"1\">\r\n" +
            "<upg:Models>\r\n" +
            "<upg:Model>&lt;PayRequest xmlns=\"http://bssys.com/sbns/integration\"&gt;&#13;\r\n" +
            "&lt;bankMessage/&gt;&#13;\r\n" +
            "&lt;acceptTerm&gt;A&lt;/acceptTerm&gt;&#13;\r\n" +
            "&lt;accountId&gt;B&lt;/accountId&gt;&#13;\r\n" +
            "&lt;bankAcceptDate&gt;C&lt;/bankAcceptDate&gt;&#13;\r\n" +
            "&lt;bankMessage&gt;D&lt;/bankMessage&gt;&#13;\r\n" +
            "&lt;bankMessageAuthor&gt;E&lt;/bankMessageAuthor&gt;&#13;\r\n" +
            "&lt;docDate&gt;F&lt;/docDate&gt;&#13;\r\n" +
            "&lt;docDispatchDate&gt;G&lt;/docDispatchDate&gt;&#13;\r\n" +
            "&lt;docId&gt;H&lt;/docId&gt;&#13;\r\n" +
            "&lt;docNumber&gt;I&lt;/docNumber&gt;&#13;\r\n" +
            "&lt;documentSum&gt;J&lt;/documentSum&gt;&#13;\r\n" +
            "&lt;externalId&gt;K&lt;/externalId&gt;&#13;\r\n" +
            "&lt;externalUPGId&gt;L&lt;/externalUPGId&gt;&#13;\r\n" +
            "&lt;lastModifyDate&gt;M&lt;/lastModifyDate&gt;&#13;\r\n" +
            "&lt;messageOnlyForBank&gt;N&lt;/messageOnlyForBank&gt;&#13;\r\n" +
            "&lt;newState&gt;O&lt;/newState&gt;&#13;\r\n" +
            "&lt;operationDate&gt;P&lt;/operationDate&gt;&#13;\r\n" +
            "&lt;operationType&gt;Q&lt;/operationType&gt;&#13;\r\n" +
            "&lt;orgId&gt;R&lt;/orgId&gt;&#13;\r\n" +
            "&lt;orgName&gt;S&lt;/orgName&gt;&#13;\r\n" +
            "&lt;payerAccount&gt;T&lt;/payerAccount&gt;&#13;\r\n" +
            "&lt;payerBankBic&gt;U&lt;/payerBankBic&gt;&#13;\r\n" +
            "&lt;payerBankCorrAccount&gt;V&lt;/payerBankCorrAccount&gt;&#13;\r\n" +
            "&lt;payerBankName&gt;W&lt;/payerBankName&gt;&#13;\r\n" +
            "&lt;payerId&gt;X&lt;/payerId&gt;&#13;\r\n" +
            "&lt;payerInn&gt;Y&lt;/payerInn&gt;&#13;\r\n" +
            "&lt;payerName&gt;Z&lt;/payerName&gt;&#13;\r\n" +
            "&lt;paymentCondition&gt;a&lt;/paymentCondition&gt;&#13;\r\n" +
            "&lt;paymentConditionCode&gt;b&lt;/paymentConditionCode&gt;&#13;\r\n" +
            "&lt;paymentKind&gt;c&lt;/paymentKind&gt;&#13;\r\n" +
            "&lt;paymentKindCode&gt;d&lt;/paymentKindCode&gt;&#13;\r\n" +
            "&lt;paymentPriority&gt;i&lt;/paymentPriority&gt;&#13;\r\n" +
            "&lt;paymentPurpose&gt;f&lt;/paymentPurpose&gt;&#13;\r\n" +
            "&lt;queueDate&gt;g&lt;/queueDate&gt;&#13;\r\n" +
            "&lt;receiverAccount&gt;h&lt;/receiverAccount&gt;&#13;\r\n" +
            "&lt;receiverBankBic&gt;i&lt;/receiverBankBic&gt;&#13;\r\n" +
            "&lt;receiverBankCorrAccount&gt;g&lt;/receiverBankCorrAccount&gt;&#13;\r\n" +
            "&lt;receiverBankName&gt;k&lt;/receiverBankName&gt;&#13;\r\n" +
            "&lt;receiverInn&gt;l&lt;/receiverInn&gt;&#13;\r\n" +
            "&lt;receiverName&gt;m&lt;/receiverName&gt;&#13;\r\n" +
            "&lt;recieveDPayerBank&gt;n&lt;/recieveDPayerBank&gt;&#13;\r\n" +
            "&lt;reserv23&gt;o&lt;/reserv23&gt;&#13;\r\n" +
            "&lt;template&gt;p&lt;/template&gt;&#13;\r\n" +
            "&lt;uip&gt;q&lt;/uip&gt;&#13;\r\n" +
            "&lt;vatCalculationRule&gt;r&lt;/vatCalculationRule&gt;&#13;\r\n" +
            "&lt;vatRate&gt;s&lt;/vatRate&gt;&#13;\r\n" +
            "&lt;vatSum&gt;t&lt;/vatSum&gt;&#13;\r\n" +
            "&lt;PayRequest&gt;&#13;\r\n" +
            "&lt;PayRequestPart&gt;&#13;\r\n" +
            "&lt;orderNumber&gt;v&lt;/orderNumber&gt;&#13;\r\n" +
            "&lt;orderDate&gt;u&lt;/orderDate&gt;&#13;\r\n" +
            "&lt;paymentBalance&gt;x&lt;/paymentBalance&gt;&#13;\r\n" +
            "&lt;partPaymentSum&gt;w&lt;/partPaymentSum&gt;&#13;\r\n" +
            "&lt;strNum&gt;y&lt;/strNum&gt;&#13;\r\n" +
            "&lt;/PayRequestPart&gt;&#13;\r\n" +
            "&lt;/PayRequest&gt;&#13;\r\n" +
            "&lt;/PayRequest&gt;&#13;\r\n" +
            "</upg:Model>\r\n" +
            "</upg:Models>\r\n" +
            "</upg:Request>\r\n";

    private PayRequestData getPayRequestData() {
        PayRequestData data = new PayRequestData();
        data.setAttrRequestId("requestId");
        data.setAcceptTerm("A");
        data.setAccountId("B");
        data.setBankAcceptDate("C");
        data.setBankMessage("D");
        data.setBankMessageAuthor("E");
        data.setDocDate("F");
        data.setDocDispatchDate("G");
        data.setDocId("H");
        data.setDocNumber("I");
        data.setDocumentSum("J");
        data.setExternalId("K");
        data.setExternalUPGId("L");
        data.setLastModifyDate("M");
        data.setMessageOnlyForBank("N");
        data.setNewState("O");
        data.setOperationDate("P");
        data.setOperationType("Q");
        data.setOrgId("R");
        data.setOrgName("S");
        data.setPayerAccount("T");
        data.setPayerBankBic("U");
        data.setPayerBankCorrAccount("V");
        data.setPayerBankName("W");
        data.setPayerId("X");
        data.setPayerInn("Y");
        data.setPayerName("Z");
        data.setPaymentCondition("a");
        data.setPaymentConditionCode("b");
        data.setPaymentKind("c");
        data.setPaymentKindCode("d");
        data.setPaymentPriority("i");
        data.setPaymentPurpose("f");
        data.setQueueDate("g");
        data.setReceiverAccount("h");
        data.setReceiverBankBic("i");
        data.setReceiverBankCorrAccount("g");
        data.setReceiverBankName("k");
        data.setReceiverInn("l");
        data.setReceiverName("m");
        data.setRecieveDPayerBank("n");
        data.setReserv23("o");
        data.setTemplate("p");
        data.setUip("q");
        data.setVatCalculationRule("r");
        data.setVatRate("s");
        data.setVatSum("t");

        PayRequestPartData payRequestPartData = new PayRequestPartData();
        payRequestPartData.setOrderDate("u");
        payRequestPartData.setOrderNumber("v");
        payRequestPartData.setPartPaymentSum("w");
        payRequestPartData.setPaymentBalance("x");
        payRequestPartData.setStrNum("y");

        data.getPartPayments().add(payRequestPartData);

        return data;
    }

    @Test
    void build_validData_xmlMessage() throws Exception{
        Config config = new Config();
        DocumentBuilder docBuilder = config.documentBuilder();
        Transformer transformer = config.transformer();
        SignCollectionMessageBuilder singBuilder = Mockito.mock(SignCollectionMessageBuilder.class);

        String result = new MessageBuilder(docBuilder, singBuilder, transformer).build(getPayRequestData());

        Assertions.assertEquals(XML_TAMPLATE, result);
    }


}