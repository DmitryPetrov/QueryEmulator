package com.emulator.domain.soap.requests.statementrequest;

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
            ".w3.org/2001/XMLSchema-instance\" requestId=\"1852ccae-e9b2-48bf-adbd-6027653f194d\" version=\"1\">\r\n" +
            "<upg:Models>\r\n" +
            "<upg:Model>&lt;StatementRequest xmlns=\"http://bssys.com/sbns/integration\"&gt;&#13;\r\n" +
            "&lt;bankMessage/&gt;&#13;\r\n" +
            "&lt;docDate&gt;r&lt;/docDate&gt;&#13;\r\n" +
            "&lt;docId&gt;t&lt;/docId&gt;&#13;\r\n" +
            "&lt;docNumber&gt;y&lt;/docNumber&gt;&#13;\r\n" +
            "&lt;fromDate&gt;p&lt;/fromDate&gt;&#13;\r\n" +
            "&lt;orgId&gt;a&lt;/orgId&gt;&#13;\r\n" +
            "&lt;orgInn&gt;d&lt;/orgInn&gt;&#13;\r\n" +
            "&lt;orgName&gt;s&lt;/orgName&gt;&#13;\r\n" +
            "&lt;toDate&gt;g&lt;/toDate&gt;&#13;\r\n" +
            "&lt;accounts&gt;&#13;\r\n" +
            "&lt;Acc&gt;&#13;\r\n" +
            "&lt;account&gt;h&lt;/account&gt;&#13;\r\n" +
            "&lt;bankBIC&gt;j&lt;/bankBIC&gt;&#13;\r\n" +
            "&lt;bankName&gt;k&lt;/bankName&gt;&#13;\r\n" +
            "&lt;orgName&gt;s&lt;/orgName&gt;&#13;\r\n" +
            "&lt;/Acc&gt;&#13;\r\n" +
            "&lt;/accounts&gt;&#13;\r\n" +
            "&lt;/StatementRequest&gt;&#13;\r\n" +
            "</upg:Model>\r\n" +
            "</upg:Models>\r\n" +
            "</upg:Request>\r\n";

    private StatementRequestData getStatementRequestData() {
        StatementRequestData data = new StatementRequestData();
        data.setAcceptDate("q");
        data.setBankMessage("w");
        data.setBankMessageAuthor("e");
        data.setDocDate("r");
        data.setDocId("t");
        data.setDocNumber("y");
        data.setDocTypeVersion("u");
        data.setExternalId("i");
        data.setExternalUPGId("o");
        data.setFromDate("p");
        data.setLastModifyDate("[");
        data.setMessageOnlyForBank("]");
        data.setOrgId("a");
        data.setOrgName("s");
        data.setOrgInn("d");
        data.setTemplate("f");
        data.setToDate("g");

        DataAccount dataAccount = new DataAccount();
        dataAccount.setAccount("h");
        dataAccount.setBankBIC("j");
        dataAccount.setBankName("k");
        data.getAccounts().add(dataAccount);

        return data;
    }

    @Test
    void build_validData_xmlMessage() throws Exception{
        Config config = new Config();
        DocumentBuilder docBuilder = config.documentBuilder();
        Transformer transformer = config.transformer();
        SignCollectionMessageBuilder singBuilder = Mockito.mock(SignCollectionMessageBuilder.class);

        String result = new MessageBuilder(docBuilder, singBuilder, transformer).build(getStatementRequestData());

        Assertions.assertEquals(XML_TAMPLATE, result);
    }

}