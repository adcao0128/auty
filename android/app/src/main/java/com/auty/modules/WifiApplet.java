package com.auty.modules;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class WifiApplet extends Applet {
    private boolean isConnected = false;
    private NotificationApplet notificationApplet;
    private WifiManager wifiManager;
    private Context context;
    private String wifiName;

    public WifiApplet(Context context, NotificationApplet notificationApplet) {
        super("WifiApp", "config");
        this.context = context;
        this.notificationApplet = notificationApplet;
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        registerWifiReceiver(context);

        updateWifiStatus();
    }

    private void registerWifiReceiver(Context context) {
        WifiStatusReceiver receiver = new WifiStatusReceiver(this);
        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        context.registerReceiver(receiver, filter);
    }

    public void updateWifiStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Network currentNetwork = connectivityManager.getActiveNetwork();
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(currentNetwork);

            if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                wifiName = getWifiSSID();
                isConnected = true;
                notificationApplet.sendNotification("Wifi", "Connected", "You are connected to Wi-Fi: " + wifiName);
            } else {

                isConnected = false;
                notificationApplet.sendNotification("Wifi", "Disconnected", "You are not connected to Wi-Fi");
            }
        }
    }


    private String getWifiSSID() {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo != null ? wifiInfo.getSSID() : "Unknown SSID";
    }


    public static class WifiStatusReceiver extends BroadcastReceiver {
        private WifiApplet wifiApplet;

        public WifiStatusReceiver(WifiApplet wifiApplet) {
            this.wifiApplet = wifiApplet;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            wifiApplet.updateWifiStatus();
        }
    }
}
