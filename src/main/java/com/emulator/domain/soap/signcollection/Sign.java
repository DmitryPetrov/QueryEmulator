package com.emulator.domain.soap.signcollection;

import com.emulator.domain.soap.signcollection.dto.ConfirmSignDto;
import com.emulator.domain.soap.signcollection.dto.SignDto;
import com.emulator.domain.soap.signcollection.dto.UserWorkspaceDto;

import java.util.Objects;

public class Sign {

    public final String CERTIFICATE_GUID_NODE_NAME = "certificateGuid";
    public final String CONTENT_NODE_NAME = "content";
    public final String CONTENT_LARGE_NODE_NAME = "contentLarge";
    public final String DIGEST_SCHEME_NODE_NAME = "digestScheme";
    public final String DIGEST_SCHEME_FORMAT_NODE_NAME = "digestSchemeFormat";
    public final String DIGEST_SCHEME_VERSION_NODE_NAME = "digestSchemeVersion";
    public final String DT_CHECK_NODE_NAME = "dtCheck";
    public final String DT_CREATE_NODE_NAME = "dtCreate";
    public final String ORG_ID_NODE_NAME = "orgId";
    public final String ORG_NAME_NODE_NAME = "orgName";
    public final String POSITION_NODE_NAME = "position";
    public final String SAFE_TOUCH_AUTO_SIGN_NODE_NAME = "safeTouchAutoSign";
    public final String SAFE_TOUCH_DIGEST_SCHEME_NODE_NAME = "safeTouchDigestScheme";
    public final String SAFE_TOUCH_DIGEST_SCHEME_VERSION_NODE_NAME = "safeTouchDigestSchemeVersion";
    public final String SIGN_AUTHORITY_ID_NODE_NAME = "signAuthorityId";
    public final String SIGN_HASH_NODE_NAME = "signHash";
    public final String SIGN_KEY_NODE_NAME = "signKey";
    public final String SIGN_TYPE_NODE_NAME = "signType";
    public final String SIGNER_FULL_NAME_NODE_NAME = "signerFullName";
    public final String USER_IP_NODE_NAME = "userIP";
    public final String USER_MAC_NODE_NAME = "userMAC";
    public final String USER_NAME_NODE_NAME = "userName";
    public final String VALID_NODE_NAME = "valid";
    public final String CONFIRM_SIGN_NODE_NAME = "confirmSign";
    public final String SIGN_NODE_NAME = "Sign";
    public final String USER_WORKSPACE_NODE_NAME = "userWorkspace";
    public final String USER_WORKSPACE_INNER_NODE_NAME = "UserWorkspace";


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
    private ConfirmSign confirmSign;
    private UserWorkspace userWorkspace;


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

    public ConfirmSign getConfirmSign() {
        if (this.confirmSign == null) {
            this.setConfirmSign(new ConfirmSign());
        }
        return confirmSign;
    }

    public void setConfirmSign(ConfirmSign confirmSign) {
        this.confirmSign = confirmSign;
    }

    public UserWorkspace getUserWorkspace() {
        if (this.userWorkspace == null) {
            this.setUserWorkspace(new UserWorkspace());
        }
        return userWorkspace;
    }

    public void setUserWorkspace(UserWorkspace userWorkspace) {
        this.userWorkspace = userWorkspace;
    }

    public SignDto getDto() {
        SignDto dto = new SignDto();

        dto.setCertificateGuid(this.getCertificateGuid());
        dto.setContent(this.getContent());
        dto.setContentLarge(this.getContentLarge());
        dto.setDigestScheme(this.getDigestScheme());
        dto.setDigestSchemeFormat(this.getDigestSchemeFormat());
        dto.setDigestSchemeVersion(this.getDigestSchemeVersion());
        dto.setDtCheck(this.getDtCheck());
        dto.setDtCreate(this.getDtCreate());
        dto.setOrgId(this.getOrgId());
        dto.setOrgName(this.getOrgName());
        dto.setPosition(this.getPosition());
        dto.setSafeTouchAutoSign(this.getSafeTouchAutoSign());
        dto.setSafeTouchDigestScheme(this.getSafeTouchDigestScheme());
        dto.setSafeTouchDigestSchemeVersion(this.getSafeTouchDigestSchemeVersion());
        dto.setSignAuthorityId(this.getSignAuthorityId());
        dto.setSignHash(this.getSignHash());
        dto.setSignKey(this.getSignKey());
        dto.setSignType(this.getSignType());
        dto.setSignerFullName(this.getSignerFullName());
        dto.setUserIP(this.getUserIP());
        dto.setUserMAC(this.getUserMAC());
        dto.setUserName(this.getUserName());
        dto.setValid(this.getValid());

        ConfirmSignDto confirmSignDto = this.getConfirmSign().getDto();
        dto.setConfirmSign(confirmSignDto);

        UserWorkspaceDto userWorkspaceDto = this.getUserWorkspace().getDto();
        dto.setUserWorkspace(userWorkspaceDto);

        return dto;
    }

    @Override
    public String toString() {
        return "Sign{" +
                "certificateGuid='" + certificateGuid + '\'' +
                ", content='" + content + '\'' +
                ", contentLarge='" + contentLarge + '\'' +
                ", digestScheme='" + digestScheme + '\'' +
                ", digestSchemeFormat='" + digestSchemeFormat + '\'' +
                ", digestSchemeVersion='" + digestSchemeVersion + '\'' +
                ", dtCheck='" + dtCheck + '\'' +
                ", dtCreate='" + dtCreate + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", position='" + position + '\'' +
                ", safeTouchAutoSign='" + safeTouchAutoSign + '\'' +
                ", safeTouchDigestScheme='" + safeTouchDigestScheme + '\'' +
                ", safeTouchDigestSchemeVersion='" + safeTouchDigestSchemeVersion + '\'' +
                ", signAuthorityId='" + signAuthorityId + '\'' +
                ", signHash='" + signHash + '\'' +
                ", signKey='" + signKey + '\'' +
                ", signType='" + signType + '\'' +
                ", signerFullName='" + signerFullName + '\'' +
                ", userIP='" + userIP + '\'' +
                ", userMAC='" + userMAC + '\'' +
                ", userName='" + userName + '\'' +
                ", valid='" + valid + '\'' +
                ", confirmSign=" + confirmSign +
                ", userWorkspace=" + userWorkspace +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Sign sign = (Sign) o;
        return Objects.equals(certificateGuid, sign.certificateGuid) &&
                Objects.equals(content, sign.content) &&
                Objects.equals(contentLarge, sign.contentLarge) &&
                Objects.equals(digestScheme, sign.digestScheme) &&
                Objects.equals(digestSchemeFormat, sign.digestSchemeFormat) &&
                Objects.equals(digestSchemeVersion, sign.digestSchemeVersion) &&
                Objects.equals(dtCheck, sign.dtCheck) &&
                Objects.equals(dtCreate, sign.dtCreate) &&
                Objects.equals(orgId, sign.orgId) &&
                Objects.equals(orgName, sign.orgName) &&
                Objects.equals(position, sign.position) &&
                Objects.equals(safeTouchAutoSign, sign.safeTouchAutoSign) &&
                Objects.equals(safeTouchDigestScheme, sign.safeTouchDigestScheme) &&
                Objects.equals(safeTouchDigestSchemeVersion, sign.safeTouchDigestSchemeVersion) &&
                Objects.equals(signAuthorityId, sign.signAuthorityId) &&
                Objects.equals(signHash, sign.signHash) &&
                Objects.equals(signKey, sign.signKey) &&
                Objects.equals(signType, sign.signType) &&
                Objects.equals(signerFullName, sign.signerFullName) &&
                Objects.equals(userIP, sign.userIP) &&
                Objects.equals(userMAC, sign.userMAC) &&
                Objects.equals(userName, sign.userName) &&
                Objects.equals(valid, sign.valid) &&
                Objects.equals(confirmSign, sign.confirmSign) &&
                Objects.equals(userWorkspace, sign.userWorkspace);
    }

    @Override
    public int hashCode() {

        return Objects.hash(certificateGuid, content, contentLarge, digestScheme, digestSchemeFormat,
                digestSchemeVersion, dtCheck, dtCreate, orgId, orgName, position, safeTouchAutoSign, safeTouchDigestScheme, safeTouchDigestSchemeVersion, signAuthorityId, signHash, signKey, signType, signerFullName, userIP, userMAC, userName, valid);
    }
}
