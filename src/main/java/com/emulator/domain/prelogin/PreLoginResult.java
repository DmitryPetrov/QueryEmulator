package com.emulator.domain.prelogin;

import java.util.Base64;

public class PreLoginResult {

    private byte[] salt;
    private byte[] bytesFromServer;
    private byte[] legacySalt;
    private byte[] preLoginId;
    private byte[] userHash;

    public byte[] getSalt() {
        return salt;
    }

    public String getSaltString() {
        return toString(salt);
    }

    public void setSalt(byte[] salt) {
        this.salt = Base64.getEncoder().encode(salt);
    }

    public byte[] getBytesFromServer() {
        return bytesFromServer;
    }

    public String getBytesFromServerString() {
        return toString(bytesFromServer);
    }

    public void setBytesFromServer(byte[] bytesFromServer) {
        this.bytesFromServer = Base64.getEncoder().encode(bytesFromServer);
    }

    public byte[] getLegacySalt() {
        return legacySalt;
    }

    public String getLegacySaltString() {
        return toString(legacySalt);
    }

    public void setLegacySalt(byte[] legacySalt) {
        this.legacySalt = Base64.getEncoder().encode(legacySalt);
    }

    public String getPreLoginIdString() {
        return toString(preLoginId);
    }

    public byte[] getPreLoginId() {
        return preLoginId;
    }

    public void setPreLoginId(byte[] preLoginId) {
        this.preLoginId = preLoginId;
    }

    public byte[] getUserHash() {
        return userHash;
    }

    public String getUserHashString() {
        return toString(userHash);
    }

    public void setUserHash(byte[] userHash) {
        this.userHash = userHash;
    }

    private String toString(byte[] bytes) {
        return new String(bytes);
    }

    @Override
    public String toString() {
        return "PreLoginResult [salt=" + getSaltString() + ", bytesFromServer=" + getBytesFromServerString()
                + ", legacySalt=" + getLegacySaltString() + ", preLoginId=" + getPreLoginId() + ", userHash="
                + getUserHashString() + "]";
    }

}
