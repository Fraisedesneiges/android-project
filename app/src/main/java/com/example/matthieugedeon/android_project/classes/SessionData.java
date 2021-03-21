package com.example.matthieugedeon.android_project.classes;

public final class SessionData {
    private static int userID;
    private static String username;
    private static boolean connected;

    public static void initialize() {
        SessionData.userID = 0;
        SessionData.username = "";
        SessionData.connected = false;
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