package com.example.matthieugedeon.android_project.classes;

import com.example.matthieugedeon.android_project.models.Wallet;

public final class SessionData {
    private static int userID;
    private static String username;
    private static boolean connected;
    private static Wallet[] wallets;

    public static void initialize() {
        SessionData.userID = 0;
        SessionData.username = "";
        SessionData.connected = false;
        SessionData.wallets = new Wallet[]{};
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
}