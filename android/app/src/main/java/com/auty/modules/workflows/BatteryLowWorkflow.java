package com.auty.modules.workflows;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.Nullable;

import com.auty.modules.applets.BatteryApplet;
import com.auty.modules.responses.AbstractResponse;
import com.auty.modules.responses.NotificationResponse;
import com.auty.modules.triggers.BatteryTrigger;

public class BatteryLowWorkflow extends  Workflow{

    private Context context;
    private BatteryTrigger batteryTrigger;
    private NotificationResponse response;
    private BatteryApplet app;

    public BatteryLowWorkflow(Context context, BatteryApplet batteryApplet, AbstractResponse notificationApplet) {
        super("batteryLowWorkflow",batteryApplet, notificationApplet);
        this.context = context;
        this.app = batteryApplet;
        this.response = (NotificationResponse) notificationApplet;

//        this.registerReceiver();
    }

    @Override
    public void registerReceiver() {
        this.batteryTrigger = new BatteryTrigger("batteryLowTrigger", this);
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(this.batteryTrigger, filter);
    }

    @Override
    public void handle(@Nullable Intent intent) {
        if (intent != null) {
            if (this.app.status_call_low_battery(intent)) {
                response.respond("Battery is low");
            }
        } else {
            response.respond("Missing intent in the handler");
        }
    }
}
