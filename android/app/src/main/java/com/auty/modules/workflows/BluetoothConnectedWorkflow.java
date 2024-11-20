package com.auty.modules.workflows;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.annotation.Nullable;

import com.auty.modules.applets.Applet;
import com.auty.modules.applets.BluetoothApplet;
import com.auty.modules.responses.AbstractResponse;
import com.auty.modules.responses.NotificationResponse;
import com.auty.modules.triggers.BluetoothTrigger;

public class BluetoothConnectedWorkflow extends Workflow {

    private BluetoothTrigger bluetoothTrigger;
    private NotificationResponse response;
    private BluetoothApplet app;

    private Context context;


    public BluetoothConnectedWorkflow(Context context, Applet applet, AbstractResponse response) {
        super("bluetoothConnectedWorkflow",applet, response);
        this.context = context;
        this.response = (NotificationResponse) response;
        this.app = (BluetoothApplet) applet;

//        this.registerReceiver();
    }

    @Override
    public void registerReceiver(){
        this.bluetoothTrigger = new BluetoothTrigger(this);
//        BluetoothApplet.BluetoothStatusReceiver receiver = new BluetoothApplet.BluetoothStatusReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.context.registerReceiver(this.bluetoothTrigger, filter);
        Log.d("Applet", "Registering bluetooth status receiver");
    }

    @Override
    public void handle(@Nullable Intent intent) {
        if (intent != null) {
            if (this.app.isConnected(intent)) {
                this.response.respond("Bluetooth device is connected");
            } else {
                this.response.respond("Bluetooth device is disconnected");
            }
        }
    }
}
