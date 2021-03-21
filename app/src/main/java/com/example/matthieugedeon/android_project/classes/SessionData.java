package com.example.matthieugedeon.android_project.classes;

import android.app.Activity;

import androidx.fragment.app.FragmentManager;

import com.example.matthieugedeon.android_project.models.Wallet;

import java.util.ArrayList;

public final class SessionData {
    private static int userID;
    private static String username;
    private static boolean connected;
    private static Activity main;
    private static FragmentManager mainFM;
    private static ArrayList<Wallet> wallets;
    private static String rowWallets;
    private static WalletListAdapter wadapter;

    public static void initialize() {
        SessionData.userID = 0;
        SessionData.username = "";
        SessionData.connected = false;
        SessionData.wallets = new ArrayList<Wallet>();
        SessionData.rowWallets = "";
    }

    public static void setUserID(int userID) {
        SessionData.userID = userID;
    }

    public static void setUsername(String username){
        SessionData.username = username;
    }

    public static void setConnected(boolean connected){
        SessionData.connected = connected;
    }

    public static void setRowWallets(String rowWallets){
        SessionData.rowWallets = rowWallets;
    }

    public static void setWallets(ArrayList<Wallet> wallets){
        SessionData.wallets = wallets;
    }

    public static void setMainFM(FragmentManager mainFM) {
        SessionData.mainFM = mainFM;
    }

    public static void setWadapter(WalletListAdapter wadapter) {
        SessionData.wadapter = wadapter;
    }

    public static void setMain(Activity main) {
        SessionData.main = main;
    }

    public static Activity getMain() {
        return SessionData.main;
    }

    public static WalletListAdapter getWadapter() {
        return SessionData.wadapter;
    }

    public static int getUserID() {
        return SessionData.userID;
    }

    public static String getUsername() {
        return SessionData.username;
    }

    public static boolean isConnected() {
        return SessionData.connected;
    }

    public static ArrayList<Wallet> getWallets() {
        return SessionData.wallets;
    }

    public static String getRowWallets() {
        return SessionData.rowWallets;
    }

    public static FragmentManager getMainFM() {
        return SessionData.mainFM;
    }
}