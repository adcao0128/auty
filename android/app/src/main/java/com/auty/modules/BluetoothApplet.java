package com.auty.modules;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.bluetooth.BluetoothManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.bluetooth.BluetoothAdapter;
import android.util.Log;

public class BluetoothApplet extends Applet {

    private NotificationApplet notificationApplet;
    private Context context;
    public boolean isConnected = false;



    public BluetoothApplet(Context context, NotificationApplet notificationApplet) {
        super("BluetoothApp", "config");
        this.context = context;
        this.notificationApplet = notificationApplet;

        registerBluetoothStatusReceiver(context);
    }


    private void registerBluetoothStatusReceiver(Context context) {

        BluetoothStatusReceiver receiver = new BluetoothStatusReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        context.registerReceiver(receiver, filter);
        Log.d("Applet", "Registering bluetooth status receiver");

    }






    public static class BluetoothStatusReceiver extends BroadcastReceiver {

        public final BluetoothApplet bluetoothApplet;
        public BluetoothStatusReceiver(BluetoothApplet bluetoothApplet) {
            this.bluetoothApplet = bluetoothApplet;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
                bluetoothApplet.notificationApplet.sendNotification("Bluetooth", "Connected", "Bluetooth is connected");
            }
            else if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
                bluetoothApplet.notificationApplet.sendNotification("Bluetooth", "Disconnected", "Bluetooth is disconnected");
            }


        }


    }




}
