package com.emulator.domain.prelogin;

import java.util.Arrays;
import java.util.Base64;

public class PreLoginResult {

    private byte[] salt;
    private byte[] bytesFromServer;
    private byte[] legacySalt;
    private String preLoginId;
    private byte[] userHash;

    public byte[] getSalt() {
        return salt;
    }

    public String getSaltString() {
        return salt.toString();
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getBytesFromServer() {
        return bytesFromServer;
    }

    public String getBytesFromServerString() {
        return bytesFromServer.toString();
    }

    public void setBytesFromServer(byte[] bytesFromServer) {
        this.bytesFromServer = bytesFromServer;
    }

    public byte[] getLegacySalt() {
        return legacySalt;
    }

    public void setLegacySalt(byte[] legacySalt) {
        this.legacySalt = legacySalt;
    }

    public String getPreLoginId() {
        return preLoginId;
    }

    public void setPreLoginId(byte[] preLoginIdBase64) {
        byte[] decodedBytes = Base64.getMimeDecoder().decode(Arrays.toString(preLoginIdBase64));
        String decodedString = new String(decodedBytes);
        this.preLoginId = decodedString;
    }

    public byte[] getUserHash() {
        return userHash;
    }

    public void setUserHash(byte[] userHashBase64) {
        byte[] decodedBytes = Base64.getMimeDecoder().decode(Arrays.toString(userHashBase64));
        this.userHash = decodedBytes;
    }

    @Override
    public String toString() {
        return "PreLoginResult [salt=" + Arrays.toString(salt) + ", bytesFromServer=" + Arrays.toString(bytesFromServer)
                + ", legacySalt=" + Arrays.toString(legacySalt) + ", preLoginId=" + preLoginId + ", userHash="
                + Arrays.toString(userHash) + "]";
    }

}
