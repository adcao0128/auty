package com.auty.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryStatusReceiver extends BroadcastReceiver {

    private BatteryApplet batteryApplet;

    public BatteryStatusReceiver(BatteryApplet batteryApplet){
        this.batteryApplet = batteryApplet;
    }

    @Override
    public void onReceive(Context context, Intent intent){
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        int batteryPct = (level * 100)/scale;

        batteryApplet.updateBatteryStatus(isCharging, batteryPct);

    }

}
