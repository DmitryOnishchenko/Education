package com.donishchenko.testapp.service;

import org.bitcoinj.core.ECKey;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service("keyGeneratorService")
public class KeyGeneratorService {

    public void getKey(String master, String chainCode) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(master.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        String hexString = sb.toString();
        System.out.println("Hex format : " + hexString);

        ECKey key = ECKey.fromPrivate(master.getBytes());
    }

    public static void main(String[] args) throws Exception {
        KeyGeneratorService service = new KeyGeneratorService();
        String masterPrivateKey = "b025710dc8338be9bbe9d10d5591d19f658b19d915004667f2ccbc5c40c4f35a";
        String chainCode = "4dde8414-bbfd-4e2c-bc25-019d3243f775";

        service.getKey(masterPrivateKey,chainCode);
    }

}
