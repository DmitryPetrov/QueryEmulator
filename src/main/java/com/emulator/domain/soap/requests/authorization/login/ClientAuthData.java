package com.emulator.domain.soap.requests.authorization.login;

import java.util.Base64;

public class ClientAuthData {

    private byte[] passwordHash;
    private byte[] extPasswordData;

    public byte[] getPasswordHash() {
        return Base64.getDecoder().decode(passwordHash);
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = toByteArray(passwordHash);
    }

    public byte[] getExtPasswordData() {
        return Base64.getDecoder().decode(extPasswordData);
    }

    public void setExtPasswordData(String extPasswordData) {
        this.extPasswordData = toByteArray(extPasswordData);
    }


    private byte[] toByteArray(String str) {
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for(int i = 0 ; i < byteArray.length ; ++i) {
            byteArray[i] = (byte) charArray[i];
        }

        return byteArray;
    }

    @Override
    public String toString() {
        return "ClientAuthData[" +
                "passwordHash=" + new String(passwordHash) +
                ", extPasswordData=" + new String(extPasswordData) +
                "]";
    }
}
