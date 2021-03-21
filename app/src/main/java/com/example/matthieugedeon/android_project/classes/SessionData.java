package com.example.matthieugedeon.android_project.classes;

import androidx.fragment.app.FragmentManager;

import com.example.matthieugedeon.android_project.models.Wallet;

public final class SessionData {
    private static int userID;
    private static String username;
    private static boolean connected;
    private static FragmentManager mainFM;
    private static Wallet[] wallets;
    private static String rowWallets;

    public static void initialize() {
        SessionData.userID = 0;
        SessionData.username = "";
        SessionData.connected = false;
        SessionData.wallets = new Wallet[]{};
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

    public static void setWallets(Wallet[] wallets){
        SessionData.wallets = wallets;
    }

    public static void setMainFM(FragmentManager mainFM) {
        SessionData.mainFM = mainFM;
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

    public static Wallet[] getWallets() {
        return SessionData.wallets;
    }

    public static String getRowWallets() {
        return SessionData.rowWallets;
    }

    public static FragmentManager getMainFM() {
        return SessionData.mainFM;
    }
}