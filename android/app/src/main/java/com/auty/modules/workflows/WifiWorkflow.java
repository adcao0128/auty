package com.auty.modules.workflows;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.annotation.Nullable;

import com.auty.modules.applets.WifiApplet;
import com.auty.modules.responses.AbstractResponse;
import com.auty.modules.responses.NotificationResponse;
import com.auty.modules.triggers.WifiTrigger;

public class WifiWorkflow extends Workflow {

    private WifiTrigger wifiTrigger;
    private NotificationResponse response;
    private WifiApplet app;

    private Context context;

    public WifiWorkflow(Context context, WifiApplet wifiApplet, AbstractResponse notificationResponse) {
        super("wifiWorkflow", wifiApplet, notificationResponse);
        this.context = context;
        this.response = (NotificationResponse) notificationResponse;
        this.app = wifiApplet;

//        this.registerReceiver();
    }

    @Override
    public void registerReceiver() {
        this.wifiTrigger = new WifiTrigger(this);
        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        this.context.registerReceiver(this.wifiTrigger, filter);
    }

    @Override
    public void handle(@Nullable Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Network currentNetwork = connectivityManager.getActiveNetwork();
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(currentNetwork);

            if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                String wifiName = this.app.getWifiSSID();
//                boolean isConnected = true;
                response.respond("You are connected to Wi-Fi: " + wifiName);
            } else {
//                boolean isConnected = false;
                response.respond("You are not connected to Wi-Fi");
            }
        }
    }

}
