package com.emulator.domain.soap.signcollection.dto;

public class UserWorkspaceDto {

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
}
