package com.example.matthieugedeon.android_project.classes;

import android.util.Log;

import com.example.matthieugedeon.android_project.models.Wallet;

import java.util.ArrayList;

public class Parser {

    //Returns an array of wallets (coin + address)
    public static ArrayList<Wallet> getWallet(String rawWallet) {
        Wallet singleWallet = new Wallet("", "");
        ArrayList<Wallet> allWallets = new ArrayList<Wallet>();


        Log.i("Wall",rawWallet);

        if (rawWallet.equals("null")) {

            return allWallets;
        }
        else if(!rawWallet.equals("null")){
            String[] strWallet = rawWallet.split("$");
            Log.i("Wall",strWallet[0]);
            for (int i = 0; i < strWallet.length; i++) {
                singleWallet.setCoin(strWallet[i].substring(0, 3));
                singleWallet.setAddress(strWallet[i].substring(3));
                allWallets.add(singleWallet);
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
