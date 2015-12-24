package com.donishchenko;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.params.TestNet3Params;

public class TestMain {

    public static void main(String[] args) {
        System.out.println("Start test...\n");

        ECKey key = new ECKey();
        System.out.println("Key created: " + key);

        NetworkParameters params = TestNet3Params.get();
        Address address = key.toAddress(params);
        System.out.println("Address: " + address);

        System.out.println("Create a Wallet");
        Wallet wallet = new Wallet(params);
        // test: create 5 keys and add them to the wallet
        for (int i = 0; i < 5; i++) {
            wallet.importKey(new ECKey());
        }
        ECKey firstKey = wallet.getImportedKeys().get(0);

        // output key
        System.out.println("First key in wallet:\n" + firstKey);

        // and here is the whole wallet
        System.out.println("Complete content of the wallet:\n" + wallet);
    }

}
