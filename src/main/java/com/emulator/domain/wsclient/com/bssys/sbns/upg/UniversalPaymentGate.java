
package com.emulator.domain.wsclient.com.bssys.sbns.upg;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "UniversalPaymentGate", targetNamespace = "http://upg.sbns.bssys.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface UniversalPaymentGate {


    /**
     * 
     * @param userLogin
     * @return
     *     returns java.util.List<byte[]>
     */
    @WebMethod
    @WebResult(targetNamespace = "http://upg.sbns.bssys.com/")
    @RequestWrapper(localName = "preLogin", targetNamespace = "http://upg.sbns.bssys.com/", className = "com.bssys.sbns.upg.PreLogin")
    @ResponseWrapper(localName = "preLoginResponse", targetNamespace = "http://upg.sbns.bssys.com/", className = "com.bssys.sbns.upg.PreLoginResponse")
    public List<byte[]> preLogin(
        @WebParam(name = "userLogin", targetNamespace = "http://upg.sbns.bssys.com/")
        String userLogin);

    /**
     * 
     * @param requests
     * @param sessionId
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "http://upg.sbns.bssys.com/")
    @RequestWrapper(localName = "getRequestStatus", targetNamespace = "http://upg.sbns.bssys.com/", className = "com.bssys.sbns.upg.GetRequestStatus")
    @ResponseWrapper(localName = "getRequestStatusResponse", targetNamespace = "http://upg.sbns.bssys.com/", className = "com.bssys.sbns.upg.GetRequestStatusResponse")
    public List<String> getRequestStatus(
        @WebParam(name = "requests", targetNamespace = "http://upg.sbns.bssys.com/")
        List<String> requests,
        @WebParam(name = "sessionId", targetNamespace = "http://upg.sbns.bssys.com/")
        String sessionId);

    /**
     * 
     * @param userLogin
     * @param preloginId
     * @param clientAuthData
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "http://upg.sbns.bssys.com/")
    @RequestWrapper(localName = "login", targetNamespace = "http://upg.sbns.bssys.com/", className = "com.bssys.sbns.upg.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://upg.sbns.bssys.com/", className = "com.bssys.sbns.upg.LoginResponse")
    public String login(
        @WebParam(name = "userLogin", targetNamespace = "http://upg.sbns.bssys.com/")
        String userLogin,
        @WebParam(name = "preloginId", targetNamespace = "http://upg.sbns.bssys.com/")
        String preloginId,
        @WebParam(name = "clientAuthData", targetNamespace = "http://upg.sbns.bssys.com/")
        List<byte[]> clientAuthData);

    /**
     * 
     * @param receipts
     * @param sessionId
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "http://upg.sbns.bssys.com/")
    @RequestWrapper(localName = "sendDocumentReceipts", targetNamespace = "http://upg.sbns.bssys.com/", className = "com.bssys.sbns.upg.SendDocumentReceipts")
    @ResponseWrapper(localName = "sendDocumentReceiptsResponse", targetNamespace = "http://upg.sbns.bssys.com/", className = "com.bssys.sbns.upg.SendDocumentReceiptsResponse")
    public List<String> sendDocumentReceipts(
        @WebParam(name = "receipts", targetNamespace = "http://upg.sbns.bssys.com/")
        List<String> receipts,
        @WebParam(name = "sessionId", targetNamespace = "http://upg.sbns.bssys.com/")
        String sessionId);

    /**
     * 
     * @param requests
     * @param sessionId
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "http://upg.sbns.bssys.com/")
    @RequestWrapper(localName = "sendRequests", targetNamespace = "http://upg.sbns.bssys.com/", className = "com.bssys.sbns.upg.SendRequests")
    @ResponseWrapper(localName = "sendRequestsResponse", targetNamespace = "http://upg.sbns.bssys.com/", className = "com.bssys.sbns.upg.SendRequestsResponse")
    public List<String> sendRequests(
        @WebParam(name = "requests", targetNamespace = "http://upg.sbns.bssys.com/")
        List<String> requests,
        @WebParam(name = "sessionId", targetNamespace = "http://upg.sbns.bssys.com/")
        String sessionId);

}
