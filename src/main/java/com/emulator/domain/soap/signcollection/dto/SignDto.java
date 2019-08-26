package com.emulator.domain.soap.signcollection.dto;

public class SignDto {

    private String certificateGuid = "";
    private String content = "";
    private String contentLarge = "";
    private String digestScheme = "";
    private String digestSchemeFormat = "";
    private String digestSchemeVersion = "";
    private String dtCheck = "";
    private String dtCreate = "";
    private String orgId = "";
    private String orgName = "";
    private String position = "";
    private String safeTouchAutoSign = "";
    private String safeTouchDigestScheme = "";
    private String safeTouchDigestSchemeVersion = "";
    private String signAuthorityId = "";
    private String signHash = "";
    private String signKey = "";
    private String signType = "";
    private String signerFullName = "";
    private String userIP = "";
    private String userMAC = "";
    private String userName = "";
    private String valid = "";
    private SignDto confirmSign;
    private UserWorkspaceDto userWorkspace;

    public String getCertificateGuid() {
        return certificateGuid;
    }

    public void setCertificateGuid(String certificateGuid) {
        this.certificateGuid = certificateGuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentLarge() {
        return contentLarge;
    }

    public void setContentLarge(String contentLarge) {
        this.contentLarge = contentLarge;
    }

    public String getDigestScheme() {
        return digestScheme;
    }

    public void setDigestScheme(String digestScheme) {
        this.digestScheme = digestScheme;
    }

    public String getDigestSchemeFormat() {
        return digestSchemeFormat;
    }

    public void setDigestSchemeFormat(String digestSchemeFormat) {
        this.digestSchemeFormat = digestSchemeFormat;
    }

    public String getDigestSchemeVersion() {
        return digestSchemeVersion;
    }

    public void setDigestSchemeVersion(String digestSchemeVersion) {
        this.digestSchemeVersion = digestSchemeVersion;
    }

    public String getDtCheck() {
        return dtCheck;
    }

    public void setDtCheck(String dtCheck) {
        this.dtCheck = dtCheck;
    }

    public String getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(String dtCreate) {
        this.dtCreate = dtCreate;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSafeTouchAutoSign() {
        return safeTouchAutoSign;
    }

    public void setSafeTouchAutoSign(String safeTouchAutoSign) {
        this.safeTouchAutoSign = safeTouchAutoSign;
    }

    public String getSafeTouchDigestScheme() {
        return safeTouchDigestScheme;
    }

    public void setSafeTouchDigestScheme(String safeTouchDigestScheme) {
        this.safeTouchDigestScheme = safeTouchDigestScheme;
    }

    public String getSafeTouchDigestSchemeVersion() {
        return safeTouchDigestSchemeVersion;
    }

    public void setSafeTouchDigestSchemeVersion(String safeTouchDigestSchemeVersion) {
        this.safeTouchDigestSchemeVersion = safeTouchDigestSchemeVersion;
    }

    public String getSignAuthorityId() {
        return signAuthorityId;
    }

    public void setSignAuthorityId(String signAuthorityId) {
        this.signAuthorityId = signAuthorityId;
    }

    public String getSignHash() {
        return signHash;
    }

    public void setSignHash(String signHash) {
        this.signHash = signHash;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignerFullName() {
        return signerFullName;
    }

    public void setSignerFullName(String signerFullName) {
        this.signerFullName = signerFullName;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getUserMAC() {
        return userMAC;
    }

    public void setUserMAC(String userMAC) {
        this.userMAC = userMAC;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public SignDto getConfirmSign() {
        if (this.confirmSign == null) {
            this.setConfirmSign(new SignDto());
        }
        return confirmSign;
    }

    public void setConfirmSign(SignDto confirmSign) {
        this.confirmSign = confirmSign;
    }

    public UserWorkspaceDto getUserWorkspace() {
        if (this.userWorkspace == null) {
            this.setUserWorkspace(new UserWorkspaceDto());
        }
        return userWorkspace;
    }

    public void setUserWorkspace(UserWorkspaceDto userWorkspace) {
        this.userWorkspace = userWorkspace;
    }
}
