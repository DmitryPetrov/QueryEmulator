package com.emulator.domain.soap.signcollection;

import com.emulator.domain.soap.signcollection.dto.UserWorkspaceDto;

public class UserWorkspace {

    public final String AVP_ACTIVE_NODE_NAME = "AVPActive";
    public final String OS_UPDATABLE_NODE_NAME = "OSUpdatable";
    public final String ADD_INFO_NODE_NAME = "addInfo";
    public final String FAULT_PASS_ATTEMPT_COUNT_NODE_NAME = "faultPassAttemptCount";
    public final String HASH_CODE_NODE_NAME = "hashCode";
    public final String NOT_REMOTE_ACCESS_NODE_NAME = "notRemoteAccess";
    public final String OUTER_KEY_STORAGE_NODE_NAME = "outerKeyStorage";
    public final String PASS_CHANGED_NODE_NAME = "passChanged";
    public final String USER_MAC_NODE_NAME = "userMAC";


    private String AVPActive = "";
    private String OSUpdatable = "";
    private String addInfo = "";
    private String faultPassAttemptCount = "";
    private String hashCode = "";
    private String notRemoteAccess = "";
    private String outerKeyStorage = "";
    private String passChanged = "";
    private String userMAC = "";

    public String getAVPActive() {
        return AVPActive;
    }

    public void setAVPActive(String AVPActive) {
        this.AVPActive = AVPActive;
    }

    public String getOSUpdatable() {
        return OSUpdatable;
    }

    public void setOSUpdatable(String OSUpdatable) {
        this.OSUpdatable = OSUpdatable;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getFaultPassAttemptCount() {
        return faultPassAttemptCount;
    }

    public void setFaultPassAttemptCount(String faultPassAttemptCount) {
        this.faultPassAttemptCount = faultPassAttemptCount;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getNotRemoteAccess() {
        return notRemoteAccess;
    }

    public void setNotRemoteAccess(String notRemoteAccess) {
        this.notRemoteAccess = notRemoteAccess;
    }

    public String getOuterKeyStorage() {
        return outerKeyStorage;
    }

    public void setOuterKeyStorage(String outerKeyStorage) {
        this.outerKeyStorage = outerKeyStorage;
    }

    public String getPassChanged() {
        return passChanged;
    }

    public void setPassChanged(String passChanged) {
        this.passChanged = passChanged;
    }

    public String getUserMAC() {
        return userMAC;
    }

    public void setUserMAC(String userMAC) {
        this.userMAC = userMAC;
    }

    public UserWorkspaceDto getDto() {
        UserWorkspaceDto dto = new UserWorkspaceDto();

        dto.setAVPActive(this.getAVPActive());
        dto.setOSUpdatable(this.getOSUpdatable());
        dto.setAddInfo(this.getAddInfo());
        dto.setFaultPassAttemptCount(this.getFaultPassAttemptCount());
        dto.setHashCode(this.getHashCode());
        dto.setNotRemoteAccess(this.getNotRemoteAccess());
        dto.setOuterKeyStorage(this.getOuterKeyStorage());
        dto.setPassChanged(this.getPassChanged());
        dto.setUserMAC(this.getUserMAC());

        return dto;
    }
}
