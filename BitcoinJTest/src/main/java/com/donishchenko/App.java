package com.donishchenko;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.utils.BriefLogFormatter;

/**
 * Hello world!
 *
 */
public class App {
    private static Address forwardingAddress;
    private static WalletAppKit kit;

    public static void main(String[] args) throws AddressFormatException {
        BriefLogFormatter.init();

        NetworkParameters params = TestNet3Params.get();
    }

}
