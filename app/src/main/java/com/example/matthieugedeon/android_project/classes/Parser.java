package com.example.matthieugedeon.android_project.classes;

import com.example.matthieugedeon.android_project.models.Wallet;

public class Parser {

    //Returns an array of wallets (coin + address)
    public Wallet[] getWallet(String rawWallet) {
        Wallet singleWallet = new Wallet("", "");
        Wallet[] allWallets = {};

        if (rawWallet == "null") {
            return allWallets;
        }

        String[] strWallet = rawWallet.split("|");
        for (int i = 0; i < strWallet.length; i++) {
            singleWallet.setCoin(strWallet[i].substring(0, 3));
            singleWallet.setAddress(strWallet[i].substring(3));
            allWallets[i] = singleWallet;
        }

        return allWallets;
    }

    //Adds a new wallet to the rawWallet string
    public String walletAppend(String wallets, String newWallet)
    {
        wallets = wallets + "|" + newWallet;
        return wallets;
    }

}
