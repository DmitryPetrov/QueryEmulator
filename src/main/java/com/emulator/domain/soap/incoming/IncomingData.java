package com.emulator.domain.soap.incoming;

import com.emulator.domain.entity.RequestParameters;
import com.emulator.exception.RequestParameterLengthException;

import java.util.ArrayList;
import java.util.List;

public class IncomingData extends RequestParameters {

    public final static String REQUEST_NODE_NAME = "upg:Request";
    public final static String INCOMING_NODE_NAME = "upg:Incoming";
    public final static String DOC_TYPES_NODE_NAME = "upg:docTypes";
    public final static String DOC_TYPE_NODE_NAME = "upg:docType";
    public final static String REQUEST_ID_ATTR_NAME = "requestId";
    public final static String VERSION_ATTR_NAME = "version";
    public final static String SENDER_ATTR_NAME = "sender";
    public final static String RECEIVER_ATTR_NAME = "receiver";
    public final static String STATE_REQUEST_ATTR_NAME = "stateRequest";
    public final static String INCOMING_ID_ATTR_NAME = "incomingId";
    public final static String TIMESTAMP_ATTR_NAME = "timestamp";
    public final static String UPG_NAMESPACE_NAME = "xmlns:upg";
    public final static String XSI_NAMESPACE_NAME = "xmlns:xsi";


    private final static int DOC_TYPE_MAX_LENGTH = 100;
    private final static int INCOMING_ID_ATTR_MAX_LENGTH = 100;


    private final static String DOC_TYPE_DEFAULT_VALUE = "R020";
    private final static String VERSION_ATTR_DEFAULT_VALUE = "1";
    private final static String SENDER_ATTR_DEFAULT_VALUE = "1";
    private final static String RECEIVER_ATTR_DEFAULT_VALUE = "1";
    private final static String STATE_REQUEST_ATTR_DEFAULT_VALUE = "true";
    private final static String INCOMING_ID_ATTR_DEFAULT_VALUE = "6d773fb4-0608-493e-acc3-000000000016";
    private final static String TIMESTAMP_ATTR_DEFAULT_VALUE = "2018-05-23T16:53:00";
    private final static String UPG_NAMESPACE_DEFAULT_VALUE = "http://bssys.com/upg/request";
    private final static String XSI_NAMESPACE_DEFAULT_VALUE = "http://www.w3.org/2001/XMLSchema-instance";


    private String attrRequestId = "";
    private String attrVersion = VERSION_ATTR_DEFAULT_VALUE;
    private String attrSender = SENDER_ATTR_DEFAULT_VALUE;
    private String attrReceiver = RECEIVER_ATTR_DEFAULT_VALUE;
    private String namespaceUpg = UPG_NAMESPACE_DEFAULT_VALUE;
    private String namespaceXsi = XSI_NAMESPACE_DEFAULT_VALUE;

    private String attrStateRequest = STATE_REQUEST_ATTR_DEFAULT_VALUE;
    private String attrIncomingId = INCOMING_ID_ATTR_DEFAULT_VALUE;
    private String attrTimestamp = TIMESTAMP_ATTR_DEFAULT_VALUE;
    private List<String> docTypes;

    {
        docTypes = new ArrayList<>();
        docTypes.add(DOC_TYPE_DEFAULT_VALUE);
    }

    @Override
    public void check() throws RequestParameterLengthException {
        checkStringLength(INCOMING_ID_ATTR_NAME, this.attrIncomingId, INCOMING_ID_ATTR_MAX_LENGTH);

        for (String docType: docTypes) {
            checkStringLength(DOC_TYPE_NODE_NAME, docType, DOC_TYPE_MAX_LENGTH);
        }
    }
}
