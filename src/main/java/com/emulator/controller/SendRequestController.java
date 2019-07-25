package com.emulator.controller;


import com.emulator.domain.entity.AppUser;
import com.emulator.domain.entity.SendRequestRequest1Data;
import com.emulator.domain.frontend.SOAPConnectionStatus;
import com.emulator.domain.soap.SOAPClient;
import com.emulator.domain.soap.exception.SOAPServerSendRequestRequest1Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class SendRequestController {

    @Autowired
    private SOAPClient soapClient;

    @RequestMapping(value = "/sendRequest/request1", method = RequestMethod.POST)
    @ResponseBody
    public SOAPConnectionStatus requests1(HttpSession httpSession,
                                          @RequestParam(value = "account", required = false) String account,
                                          @RequestParam(value = "bankBIC", required = false) String bankBIC,
                                          @RequestParam(value = "orgId", required = false) String orgId,
                                          @RequestParam(value = "orgInn", required = false) String orgInn,
                                          @RequestParam(value = "orgName", required = false) String orgName) {
        AppUser user = (AppUser) httpSession.getAttribute("user");
        try {
            SendRequestRequest1Data data = createSendRequestData(account, bankBIC, orgId, orgInn, orgName);
            soapClient.sendRequestRequest1(data);
            return requests1Succeeded(user.getSessionId());
        } catch (SOAPServerSendRequestRequest1Exception e) {
            e.printStackTrace();
            return requests1Failed(e);
        }
    }

    private SOAPConnectionStatus requests1Succeeded(String message) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("OK");
        result.setMessage("SendRequest request1 to SOAP server is success. message=" + message);
        return result;
    }

    private SOAPConnectionStatus requests1Failed(SOAPServerSendRequestRequest1Exception exception) {
        SOAPConnectionStatus result = new SOAPConnectionStatus();
        result.setStatus("SendRequest request1 ERROR");
        result.setMessage("SendRequest request1 to SOAP server is fail.");
        result.setSoapMessages("<SoapMessages>" + exception.getSoapMessages() + "</SoapMessages>");
        return result;
    }

    private SendRequestRequest1Data createSendRequestData(String account, String bankBIC, String orgId, String
            orgInn, String orgName) {
        SendRequestRequest1Data result = new SendRequestRequest1Data();
        result.setAccount(account);
        result.setBankBIC(bankBIC);
        result.setOrgId(orgId);
        result.setOrgInn(orgInn);
        result.setOrgName(orgName);
        return result;
    }
}
