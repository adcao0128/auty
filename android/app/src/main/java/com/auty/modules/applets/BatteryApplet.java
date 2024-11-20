package com.auty.modules.applets;


import android.content.Intent;
import android.os.BatteryManager;

import androidx.annotation.NonNull;


public class BatteryApplet extends Applet {


    public BatteryApplet() {
        super("BatteryApp", "config");
    }

    public boolean status_call_plugged_in(@NonNull Intent intent){
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

    }

    public boolean status_call_low_battery(@NonNull Intent intent){
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int batteryPercent = (level * 100)/scale;

        return batteryPercent < 10 && batteryPercent != -1;
    }


}
