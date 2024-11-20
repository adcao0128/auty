package com.auty.modules.applets;

import android.content.Context;

import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;

public class WifiApplet extends Applet {

    private WifiManager wifiManager;

    public WifiApplet(Context context) {
        super("WifiApp", "config");

        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public String getWifiSSID() {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo != null ? wifiInfo.getSSID() : "Unknown SSID";
    }


}
