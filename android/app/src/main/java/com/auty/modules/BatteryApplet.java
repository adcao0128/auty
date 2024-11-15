package com.auty.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class BatteryApplet extends  Applet{

    boolean isCharging;
    int batteryPercent;
    private NotificationApplet notificationApplet;
    private long lastChargingNotificationTime = 0;
    private static final long NOTIFICATION_COOLDOWN = 6000;

    public BatteryApplet(Context context, NotificationApplet notificationApplet) {
        super("BatteryApp", "config");
        this.notificationApplet = notificationApplet;
        registerBatteryReceiver(context);
    }


    public boolean status_call_plugged_in(){

//        BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
//                isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;
//            }
//        };
        return isCharging;
    }

    public boolean status_call_low_battery(){

//        BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                batteryPercent = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
//            }
//        };
        return batteryPercent < 10 && batteryPercent != -1;
    }

    public void updateBatteryStatus(boolean isCharging, int batteryPercent){
        this.isCharging = isCharging;
        this.batteryPercent = batteryPercent;

        long currentTime = System.currentTimeMillis();

        if(isCharging){
            if (currentTime - lastChargingNotificationTime > NOTIFICATION_COOLDOWN) {
                notificationApplet.sendNotification("Battery", "Charging", "Battery is charging");
                lastChargingNotificationTime = currentTime;
            }
        }
        else if(batteryPercent < 20){
            if (currentTime - lastChargingNotificationTime > NOTIFICATION_COOLDOWN) {
                notificationApplet.sendNotification("Battery", "Low", "Battery is low");
                lastChargingNotificationTime = currentTime;
            }
        }
    }

    private void registerBatteryReceiver(Context context) {
        BatteryStatusReceiver receiver = new BatteryStatusReceiver(this);
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(receiver, filter);
    }

}
