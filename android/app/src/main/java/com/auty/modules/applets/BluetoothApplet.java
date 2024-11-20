package com.auty.modules.applets;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class BluetoothApplet extends Applet {

    private Context context;
    private static final int BLUETOOTH_PERMISSION_REQUEST_CODE = 1;

    public BluetoothApplet(Context context) {
        super("BluetoothApp", "config");

        this.context = context;

        if (!hasBluetoothPermissions()) {
            requestBluetoothPermissions();
        }
    }

    public boolean isConnected(Intent intent){
        String action = intent.getAction();

        if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
            return true;
        }
        else if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
            return false;
        }
        return false;
    }

    private boolean hasBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return ContextCompat.checkSelfPermission(this.context, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.context, android.Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(this.context, android.Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 12 and higher
            ActivityCompat.requestPermissions((Activity) this.context, new String[]{
                    android.Manifest.permission.BLUETOOTH_CONNECT,
                    android.Manifest.permission.BLUETOOTH_SCAN
            }, BLUETOOTH_PERMISSION_REQUEST_CODE);
        } else {
            // Below Android 12
            ActivityCompat.requestPermissions((Activity) this.context, new String[]{
                    android.Manifest.permission.BLUETOOTH,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, BLUETOOTH_PERMISSION_REQUEST_CODE);
        }
    }

}



