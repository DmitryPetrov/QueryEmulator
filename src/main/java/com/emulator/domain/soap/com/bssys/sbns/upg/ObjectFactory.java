
package com.emulator.domain.soap.com.bssys.sbns.upg;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bssys.sbns.upg package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SendDocumentReceiptsResponse_QNAME = new QName("http://upg.sbns.bssys.com/", "sendDocumentReceiptsResponse");
    private final static QName _Login_QNAME = new QName("http://upg.sbns.bssys.com/", "login");
    private final static QName _SendDocumentReceipts_QNAME = new QName("http://upg.sbns.bssys.com/", "sendDocumentReceipts");
    private final static QName _SendRequestsResponse_QNAME = new QName("http://upg.sbns.bssys.com/", "sendRequestsResponse");
    private final static QName _GetRequestStatusResponse_QNAME = new QName("http://upg.sbns.bssys.com/", "getRequestStatusResponse");
    private final static QName _PreLogin_QNAME = new QName("http://upg.sbns.bssys.com/", "preLogin");
    private final static QName _LoginResponse_QNAME = new QName("http://upg.sbns.bssys.com/", "loginResponse");
    private final static QName _GetRequestStatus_QNAME = new QName("http://upg.sbns.bssys.com/", "getRequestStatus");
    private final static QName _PreLoginResponse_QNAME = new QName("http://upg.sbns.bssys.com/", "preLoginResponse");
    private final static QName _SendRequests_QNAME = new QName("http://upg.sbns.bssys.com/", "sendRequests");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bssys.sbns.upg
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PreLogin }
     * 
     */
    public PreLogin createPreLogin() {
        return new PreLogin();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link GetRequestStatus }
     * 
     */
    public GetRequestStatus createGetRequestStatus() {
        return new GetRequestStatus();
    }

    /**
     * Create an instance of {@link PreLoginResponse }
     * 
     */
    public PreLoginResponse createPreLoginResponse() {
        return new PreLoginResponse();
    }

    /**
     * Create an instance of {@link SendRequests }
     * 
     */
    public SendRequests createSendRequests() {
        return new SendRequests();
    }

    /**
     * Create an instance of {@link SendDocumentReceiptsResponse }
     * 
     */
    public SendDocumentReceiptsResponse createSendDocumentReceiptsResponse() {
        return new SendDocumentReceiptsResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link SendDocumentReceipts }
     * 
     */
    public SendDocumentReceipts createSendDocumentReceipts() {
        return new SendDocumentReceipts();
    }

    /**
     * Create an instance of {@link SendRequestsResponse }
     * 
     */
    public SendRequestsResponse createSendRequestsResponse() {
        return new SendRequestsResponse();
    }

    /**
     * Create an instance of {@link GetRequestStatusResponse }
     * 
     */
    public GetRequestStatusResponse createGetRequestStatusResponse() {
        return new GetRequestStatusResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendDocumentReceiptsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://upg.sbns.bssys.com/", name = "sendDocumentReceiptsResponse")
    public JAXBElement<SendDocumentReceiptsResponse> createSendDocumentReceiptsResponse(SendDocumentReceiptsResponse value) {
        return new JAXBElement<SendDocumentReceiptsResponse>(_SendDocumentReceiptsResponse_QNAME, SendDocumentReceiptsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://upg.sbns.bssys.com/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendDocumentReceipts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://upg.sbns.bssys.com/", name = "sendDocumentReceipts")
    public JAXBElement<SendDocumentReceipts> createSendDocumentReceipts(SendDocumentReceipts value) {
        return new JAXBElement<SendDocumentReceipts>(_SendDocumentReceipts_QNAME, SendDocumentReceipts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendRequestsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://upg.sbns.bssys.com/", name = "sendRequestsResponse")
    public JAXBElement<SendRequestsResponse> createSendRequestsResponse(SendRequestsResponse value) {
        return new JAXBElement<SendRequestsResponse>(_SendRequestsResponse_QNAME, SendRequestsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRequestStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://upg.sbns.bssys.com/", name = "getRequestStatusResponse")
    public JAXBElement<GetRequestStatusResponse> createGetRequestStatusResponse(GetRequestStatusResponse value) {
        return new JAXBElement<GetRequestStatusResponse>(_GetRequestStatusResponse_QNAME, GetRequestStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PreLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://upg.sbns.bssys.com/", name = "preLogin")
    public JAXBElement<PreLogin> createPreLogin(PreLogin value) {
        return new JAXBElement<PreLogin>(_PreLogin_QNAME, PreLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://upg.sbns.bssys.com/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRequestStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://upg.sbns.bssys.com/", name = "getRequestStatus")
    public JAXBElement<GetRequestStatus> createGetRequestStatus(GetRequestStatus value) {
        return new JAXBElement<GetRequestStatus>(_GetRequestStatus_QNAME, GetRequestStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PreLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://upg.sbns.bssys.com/", name = "preLoginResponse")
    public JAXBElement<PreLoginResponse> createPreLoginResponse(PreLoginResponse value) {
        return new JAXBElement<PreLoginResponse>(_PreLoginResponse_QNAME, PreLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendRequests }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://upg.sbns.bssys.com/", name = "sendRequests")
    public JAXBElement<SendRequests> createSendRequests(SendRequests value) {
        return new JAXBElement<SendRequests>(_SendRequests_QNAME, SendRequests.class, null, value);
    }

}
