package com.emulator.domain.soap.requests.statementrequest;

import ru.rutoken.jrt11.Config;

import java.security.*;
import java.util.ArrayList;
import java.util.List;

public class SingCreator {

    private String head = "[Запрос на получение информации о движении денежных средств]";
    private List<String> content = new ArrayList<String>();
    private List<String> account = new ArrayList<String>();
    private int lineSeparator = 0x0A;

    {
        content.add("Номер документа=");
        content.add("Дата документа=");
        content.add("Дата выписки=");
        content.add("Наименование организации автора документа=");
        content.add("ИНН организации автора документа=");

        account.add("[Счет #");
        account.add("]");
        account.add("Счет=");
        account.add("БИК банка, в котором обслуживается счет=");
        account.add("Наименование банка=");
    }

    public void create() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException,
            SignatureException {
        StringBuilder builder = new StringBuilder();
        builder.append(head + lineSeparator);

        for (String s : content) {
            builder.append(s + "content" + lineSeparator);
        }
        for (String s : account) {
            builder.append(s + "content" + lineSeparator);
        }
        for (String s : account) {
            builder.append(s + "content" + lineSeparator);
        }

        String tokenId = "0086092711";
        String tokenPassword = "12345678";

        String document = builder.toString();

        byte[] digitalSignature;
        try {
            Config config2 = new Config("C:\\Windows\\System32\\rtPKCS11ECP.dll", "0086092711", "11111111");
            digitalSignature = sign(document, config2);
        } catch (Exception e) {
            System.out.println("exception, try new password:\n" + e);
            Config config = new Config("C:\\Windows\\System32\\rtPKCS11ECP.dll", "0086092711", "12345678");
            digitalSignature = sign(document, config);
        }



        System.out.println("digitalSignature:" + new String(digitalSignature));
    }

    public byte[] sign(String document, Config config) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException,
            InvalidAlgorithmParameterException {

        Provider provider = new ru.rutoken.jrt11.JRT11Provider(config);
        Security.addProvider(provider);

        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("rtGOST3410", provider);
        keyPairGenerator.initialize(new ru.rutoken.security.spec.ParamAlias("rtRSA"));
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        Signature signature = Signature.getInstance("rtGOST3411withrtGOST3410", provider);
        signature.initSign(keyPair.getPrivate());
        signature.update(document.getBytes());
        byte[] digitalSignature = signature.sign();

        Signature signature2 = Signature.getInstance("rtGOST3411withrtGOST3410", provider);
        signature2.initVerify(keyPair.getPublic());
        signature2.update(document.getBytes());
        boolean verified = signature2.verify(digitalSignature);

        System.out.println("verified = " + verified);

        return digitalSignature;

    }

}
