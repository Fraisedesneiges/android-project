package com.example.matthieugedeon.android_project.classes;

import android.util.Log;

import com.example.matthieugedeon.android_project.models.Wallet;

public class Parser {

    //Returns an array of wallets (coin + address)
    public static Wallet[] getWallet(String rawWallet) {
        Wallet singleWallet = new Wallet("", "");
        Wallet[] allWallets = new Wallet[]{};

        if (rawWallet.equals("null")) {
            return allWallets;
        }
        else if(!rawWallet.equals("null")){
            String[] strWallet = rawWallet.split("$");
            for (int i = 0; i < strWallet.length-1; i++) {
                singleWallet.setCoin(strWallet[i].substring(0, 3));
                singleWallet.setAddress(strWallet[i].substring(3));
                allWallets[i] = singleWallet;
            }
        }


        return allWallets;
    }

    //Adds a new wallet to the rawWallet string
    public String walletAppend(String wallets, String newWallet)
    {
        if(wallets.equals("null")){
            wallets = newWallet;
        }
        else{
            wallets = wallets + "$" + newWallet;
        }

        return wallets;
    }

}
