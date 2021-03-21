package com.example.matthieugedeon.android_project.models;

public class Wallet {
    private String coin;
    private String address;

    public Wallet(String coin, String address){
        this.coin = coin;
        this.address = address;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
